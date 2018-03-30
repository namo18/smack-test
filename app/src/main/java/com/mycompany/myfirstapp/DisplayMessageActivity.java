package com.mycompany.myfirstapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;


public class DisplayMessageActivity extends AppCompatActivity {

    private int cnt = 0;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);

        Log.e("=========", message);

        ListView lv = (ListView) findViewById(R.id.listView);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse(MyContentProvider.AUTHORITY);
        Cursor c = contentResolver.query(uri,new String[]{"name","pass"},null,null,"_id desc");

        MyAdapter adapter = new MyAdapter(this,c);
        lv.setAdapter(adapter);
    }

    public void addItem(View view)
    {
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put("name","123");
        values.put("pass","password");
        Uri uri =Uri.parse("content://" + MyContentProvider.AUTHORITY + "/test");
        contentResolver.insert(uri,values);
    }
}
