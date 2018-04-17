/*
 * This is the source code of Telegram for Android v. 1.3.2.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013.
 */

package com.mycompany.myfirstapp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class DispatchQueue extends Thread {
    private final Object handlerSyncObject = new Object();
    public Handler handler;

    public DispatchQueue(final String threadName) {
        System.out.println("DispatchQueue 初始化 "+threadName);
        setName(threadName);
        start();
        System.out.println("DispatchQueue 初始化完毕");
    }

    private void sendMessage(Message msg, int delay) {
        if (handler == null) {
            try {
                synchronized (handlerSyncObject) {
                    handlerSyncObject.wait();
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        if (handler != null) {
            if (delay <= 0) {
                handler.sendMessage(msg);
            } else {
                handler.sendMessageDelayed(msg, delay);
            }
        }
    }

    public void postRunnable(Runnable runnable) {
        postRunnable(runnable, 0);
    }

    public void postRunnable(Runnable runnable, int delay) {
        System.out.println("DispatchQueue 执行"+getName()+" postRunnable");
        if (handler == null) {
            try {
                System.out.println("DispatchQueue "+getName()+" handler = null");
                synchronized (handlerSyncObject) {
                    System.out.println("DispatchQueue "+getName()+" handlerSyncObject.wait");
                    handlerSyncObject.wait();
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        if (handler != null) {
            if (delay <= 0) {
                handler.post(runnable);
            } else {
                handler.postDelayed(runnable, delay);
            }
        }
    }

    public void run() {
        System.out.println("DispatchQueue "+getName()+"执行 run()");
        Looper.prepare();
        handler = new Handler();
        synchronized (handlerSyncObject) {
            handlerSyncObject.notify();
        }
        Looper.loop();
    }
}
