package com.mycompany.myfirstapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mycompany.myfirstapp.trustStore.MemorizingTrustManager;

import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearch;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jivesoftware.smackx.xdata.Form;
import org.jxmpp.jid.BareJid;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Localpart;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;


public class MyActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    public static int count = 0;
    public final Object synObject = new Object();
    public static DispatchQueue q1 = new DispatchQueue("Q1");
    public static DispatchQueue t1 = new DispatchQueue("T1");
    public static XMPPTCPConnection xmppConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn = (Button) findViewById(R.id.button2);

        int[] buttons = new int[]{R.id.btn_add_roster, R.id.btn_check_state, R.id.btn_connect,
                R.id.btn_login, R.id.btn_select, R.id.btn_select_roster, R.id.btn_delete_roster,
                R.id.btn_subscribe, R.id.btn_logout, R.id.btn_unsubscribe, R.id.btn_search,
                R.id.btn_account_attrib,R.id.btn_create_account,R.id.btn_subcribed};

        for (int i : buttons) {
            Button btngroup = findViewById(i);
            btngroup.setOnClickListener(this);
        }

        ListView lv = findViewById(R.id.listView1);
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse(MyContentProvider.AUTHORITY);
        Cursor c = contentResolver.query(uri, new String[]{"name"}, null, null, "_id desc");

        MyAdapter adapter = new MyAdapter(this, c);
        lv.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("button", "button click");

                for (int i = 0; i < 5; i++) {
                    final int t = i;
                    q1.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            for (int j = 0; j < 10; j++) {
                                try {
                                    System.out.println("This Q1#" + Integer.toString(t) + "  id=" + j);
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    t1.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            for (int j = 0; j < 10; j++) {
                                try {
                                    System.out.println("This T1#" + Integer.toString(t) + "  id=" + j);
                                    Thread.sleep(5);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }

//               Runnable runnable = new Runnable() {
//                   @Override
//                   public void run() {
//                       for(int i =0;i<20;i++)
//                           System.out.println(Thread.currentThread().getName()+Integer.toString(i));
//                   }
//               };
//                runnable.run();
            }
        });

        // ListView listView = (ListView)findViewById(R.id.listView1);

//        ContentResolver provider = getContentResolver();
//        Uri uri = Uri.parse("CONTENT://"+MyContentProvider.AUTHORITY);
//        SQLiteDatabase db = MyDbHelper.getInstance(this).getReadableDatabase();
//
//
//        Cursor cursor = provider.query(uri,null,null,null,"_id desc");
//        //cursor = db.query("test",new String[]{"_id","name","pass"},null,null,null,null,"_id desc");
//        TextView t = (TextView) findViewById(R.id.edit_message);
//        int count = cursor.getCount();
//        t.setText(String.format("%d",count));
//        MyAdapter adapter = new MyAdapter(this,cursor);
//
//        listView.setAdapter(adapter);
        permission();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_connect:
                q1.postRunnable(new Runnable() {
                    public void run() {
                        try {
                            MemorizingTrustManager mtm = new MemorizingTrustManager(getApplicationContext());
                            SSLContext sslcontext = SSLContext.getInstance("TLS");
                            sslcontext.init(null, new X509TrustManager[] { mtm }, new SecureRandom());

                            assert sslcontext != null;

                            XMPPTCPConnectionConfiguration.Builder mBuilder = XMPPTCPConnectionConfiguration.builder();
                            mBuilder.setHostAddress(InetAddress.getByName("192.168.2.19"));
                            mBuilder.setPort(5222);
                            mBuilder.setXmppDomain("workchat");
                            mBuilder.setResource("android");
                            mBuilder.setCompressionEnabled(true);
                            mBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
//                            mBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.required)
//                                    .setCustomSSLContext(sslcontext)
//                                    .setHostnameVerifier(mtm.wrapHostnameVerifier(new StrictHostnameVerifier()));

                            XMPPTCPConnectionConfiguration mConfig = mBuilder.build();

                            xmppConnection = new XMPPTCPConnection(mConfig);
                            xmppConnection.connect();


                        } catch (XMPPException | SmackException | IOException |
                                InterruptedException|NoSuchAlgorithmException|KeyManagementException e)

                        {
                            e.printStackTrace();
                        }
                    }
                });
                break;

            case R.id.btn_subcribed:
                try{
                    TextView tv = findViewById(R.id.edit_friend);
                    String friend = tv.getText().toString();

                    Presence presenceRes = new Presence(Presence.Type.subscribed);
                    Jid jid = JidCreate.bareFrom(JidCreate.from(friend, xmppConnection.getServiceName(), "android"));
                    presenceRes.setTo(jid);
                    xmppConnection.sendStanza(presenceRes);
                }catch (Exception err){
                    err.printStackTrace();
                }

                break;
            case R.id.btn_login:
                try {
                    TextView textView = findViewById(R.id.edit_message);
                    TextView password = findViewById(R.id.edit_password);
                    Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);

                    xmppConnection.login(textView.getText().toString(), password.getText().toString());
                    System.out.println("sengmessage click");
                    System.out.print(xmppConnection.isConnected());

                    if (xmppConnection != null && xmppConnection.isConnected() && xmppConnection.isAuthenticated()) {

                        //条件过滤器
                        StanzaFilter filter = new StanzaTypeFilter(Presence.class);
                        //packet监听器
                        StanzaListener listener = new StanzaListener() {

                            @Override
                            public void processStanza(Stanza packet) {
                                String name, password, response, acceptAdd, alertName, alertSubName;
                                System.out.println("PresenceService-" + packet.toXML());
                                if (packet instanceof Presence) {
                                    Presence presence = (Presence) packet;
                                    Jid from = presence.getFrom();//发送方
                                    Jid to = presence.getTo();//接收方
                                    if (presence.getType().equals(Presence.Type.subscribe)) {
                                        System.out.println("收到添加请求！From:" + from.toString() + "To:" + to.toString());
                                    } else if (presence.getType().equals(
                                            Presence.Type.subscribed)) {
                                        System.out.println( "恭喜，对方同意添加好友！From:" + from.toString() + "To:" + to.toString());
                                    } else if (presence.getType().equals(
                                            Presence.Type.unsubscribe)) {
                                        //发送广播传递response字符串
                                        System.out.println( "抱歉，对方拒绝添加好友，将你从好友列表移除！From:" + from.toString() + "To:" + to.toString());
                                    } else if (presence.getType().equals(
                                            Presence.Type.unsubscribed)) {
                                    } else if (presence.getType().equals(
                                            Presence.Type.unavailable)) {
                                        System.out.println("好友下线！");
                                    } else {
                                        System.out.println("好友上线！");
                                    }
                                }
                            }
                        };
                        //添加监听
                        xmppConnection.addAsyncStanzaListener(listener, filter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btn_select:

                try {
                    UserSearchManager usm = new UserSearchManager(xmppConnection);
                    Form searchForm = usm.getSearchForm(JidCreate.domainBareFrom("search." + xmppConnection.getServiceName()));
                    Form answerForm = searchForm.createAnswerForm();
                    UserSearch userSearch = new UserSearch();

                    answerForm.setAnswer("Username", true);
                    answerForm.setAnswer("search", "86");


                    ReportedData data = userSearch.sendSearchForm(xmppConnection, answerForm, JidCreate.domainBareFrom("search." + xmppConnection.getServiceName()));
                    for (ReportedData.Row row : data.getRows()) {
                        System.out.println(row.getValues("Username"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_check_state:
                if (xmppConnection != null) {
                    System.out.println(xmppConnection.isConnected());
                    System.out.println(xmppConnection.isAuthenticated());
                }
                break;
            case R.id.btn_add_roster:
                try {

                    TextView tv = findViewById(R.id.edit_friend);
                    Roster roster = Roster.getInstanceFor(xmppConnection);
                    BareJid jid = JidCreate.bareFrom(JidCreate.from(tv.getText().toString(), xmppConnection.getServiceName(), "android"));
                    roster.createEntry(jid, "test", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_select_roster:
                System.out.println("click:好友列表");
                try {

                    TextView tv = findViewById(R.id.edit_friend);
                    Roster roster = Roster.getInstanceFor(xmppConnection);
                    Collection<RosterEntry> entries = roster.getEntries();
                    Jid jid = JidCreate.bareFrom(JidCreate.from(tv.getText().toString(), xmppConnection.getServiceName(), "android"));
                    for (RosterEntry entry : entries) {
                        System.out.println(entry.getJid().toString());
                        System.out.println("isSubscriptionPending:" + entry.isSubscriptionPending());
                        System.out.println("isApproved:" + entry.isApproved());
                        System.out.println("canSeeHisPresence:" + entry.canSeeHisPresence());
                        System.out.println("canSeeMyPresence:" + entry.canSeeMyPresence());
                        if (entry.canSeeHisPresence() && !entry.canSeeMyPresence()) {
                            Presence presenceRes = new Presence(Presence.Type.subscribed);
                            presenceRes.setTo(entry.getJid());
                            xmppConnection.sendStanza(presenceRes);
                        }
                    }
                } catch (Exception err) {
                    err.printStackTrace();
                }
                break;
            case R.id.btn_delete_roster:
                try {

                    TextView tv = findViewById(R.id.edit_friend);
                    Roster roster = Roster.getInstanceFor(xmppConnection);
                    Jid jid = JidCreate.bareFrom(JidCreate.from(tv.getText().toString(), xmppConnection.getServiceName(), "android"));
                    RosterEntry entry = roster.getEntry(JidCreate.bareFrom(jid));
                    roster.removeEntry(entry);
                } catch (Exception err) {
                    err.printStackTrace();
                }
                break;
            case R.id.btn_subscribe:
                try {
                    TextView tv = findViewById(R.id.edit_friend);
                    Presence subscribe = new Presence(Presence.Type.subscribe);
                    Jid jid = JidCreate.bareFrom(JidCreate.from(tv.getText().toString(), xmppConnection.getServiceName(), "android"));
                    subscribe.setTo(jid);
                    xmppConnection.sendStanza(subscribe);
                } catch (Exception err) {
                    err.printStackTrace();
                }
                break;
            case R.id.btn_logout:
                try {
                    xmppConnection.disconnect();
                    System.out.println("disconnect");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_unsubscribe:
                try {

                    TextView tv = findViewById(R.id.edit_friend);
                    Presence subscribe = new Presence(Presence.Type.unsubscribed);
                    Jid jid = JidCreate.bareFrom(JidCreate.from(tv.getText().toString(), xmppConnection.getServiceName(), "android"));
                    subscribe.setTo(jid);
                    xmppConnection.sendStanza(subscribe);
                } catch (Exception err) {
                    err.printStackTrace();
                }
                break;
            case R.id.btn_search:
                Intent intent = new Intent(this, DisplayMessageActivity.class);
                EditText editText = (EditText) findViewById(R.id.edit_message);
                String message = editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
                break;
            case R.id.btn_create_account:
                try {
                    AccountManager accountManager = AccountManager.getInstance(xmppConnection);
                    Map<String, String> attributes = new HashMap<String, String>();
                    attributes.put("test", "test");
                    accountManager.createAccount(Localpart.from("username"), "user password", attributes);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_account_attrib:
                try {
                    AccountManager accountManager = AccountManager.getInstance(xmppConnection);

                    System.out.println("Account attribute:"+accountManager.getAccountAttribute("username"));
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void friendQuest(final Jid friend) {
        try {
            Roster roster = Roster.getInstanceFor(xmppConnection);
            RosterEntry entry = roster.getEntry(JidCreate.bareFrom(friend));
            if (entry != null) {
                System.out.println(friend.toString() + " can See His Presence :" + entry.canSeeHisPresence());
//                Presence presenceRes = new Presence(Presence.Type.subscribed);
//                presenceRes.setTo(friend);
//                xmppConnection.sendStanza(presenceRes);
            } else {

                ContentResolver contentResolver = getContentResolver();
                ContentValues values = new ContentValues();
                values.put("name", friend.toString());
                Uri uri = Uri.parse("content://" + MyContentProvider.AUTHORITY + "/test");
                contentResolver.insert(uri, values);

//                Presence presenceRes = new Presence(Presence.Type.subscribed);
//                presenceRes.setTo(friend);
//                xmppConnection.sendStanza(presenceRes);
            }
//            Presence presenceRes = new Presence(Presence.Type.subscribed);
//            presenceRes.setTo(friend);
//            xmppConnection.sendStanza(presenceRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
//        builder.setTitle("添加好友请求" ) ;
//        builder.setMessage("用户"+friend.toString()+"请求添加你为好友" ) ;
//        builder.setPositiveButton("同意",new DialogInterface.OnClickListener() {
//            //同意按钮监听事件，发送同意Presence包及添加对方为好友的申请
//            @Override
//            public void onClick(DialogInterface dialog, int arg1) {
//                try {
//                    Presence presenceRes = new Presence(Presence.Type.subscribed);
//                    presenceRes.setTo(friend);
//                    xmppConnection.sendStanza(presenceRes);
//                }catch (Exception err)
//                {
//                    err.printStackTrace();
//                }
//            }
//        });
//        builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
//            //拒绝按钮监听事件，发送拒绝Presence包
//            @Override
//            public void onClick(DialogInterface dialog, int arg1) {
//                try {
//                    Presence presenceRes = new Presence(Presence.Type.unsubscribe);
//                    presenceRes.setTo(friend);
//                    xmppConnection.sendStanza(presenceRes);
//                }catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        });
//        builder.show();
    }

    public void permission() {
        PermissionHelper permissionHelper = new PermissionHelper(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS, Manifest.permission.SYSTEM_ALERT_WINDOW}, 100);
        permissionHelper.request(new PermissionHelper.PermissionCallback() {
            @Override// 全都授权
            public void onPermissionGranted() {
                // addRingtoonsToMediaStore();
                Log.d("workchat", "onPermissionGranted() called");
            }

            @Override// 某个授权
            public void onIndividualPermissionGranted(String[] grantedPermission) {
                Log.d("workchat", "onIndividualPermissionGranted() called with: " +
                        "grantedPermission = [" + TextUtils.join(",", grantedPermission) + "]");
            }

            @Override// 全部拒绝
            public void onPermissionDenied() {
                Log.d("workchat", "onPermissionDenied() called");
            }

            @Override// 用户选择了"不再询问"后，点击"拒绝按钮"，执行此方法
            public void onPermissionDeniedBySystem() {
                Log.d("workchat", "onPermissionDeniedBySystem() called");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
