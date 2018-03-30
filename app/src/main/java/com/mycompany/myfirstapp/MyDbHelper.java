package com.mycompany.myfirstapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ghost on 2018-3-15.
 */

public class MyDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "test.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "test";

    private static MyDbHelper instance;

    private MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MyDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MyDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_sql = "create table if not exists " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name text, pass text)";
        db.execSQL(table_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
