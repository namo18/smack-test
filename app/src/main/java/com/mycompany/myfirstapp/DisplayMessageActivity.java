package com.mycompany.myfirstapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;


public class DisplayMessageActivity extends AppCompatActivity {

    private int cnt = 0;
    private SQLiteDatabase db;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);

        Log.e("=========", message);

        MyDbHelper dbHelper = MyDbHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name","123");
        contentValues.put("pass","456");
        db.insert(MyDbHelper.TABLE_NAME,null,contentValues);
        Button btn_query = findViewById(R.id.btn_query_db);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.query(MyDbHelper.TABLE_NAME, new String[]{"_id","name", "pass"}, null, null, null, null, null);

                while(c.moveToNext())
                {
                    System.out.println("line:"+c.getString(1) +"  Password:"+c.getString(2));
                }
                c.close();
            }
        });

        Cursor c = db.query(MyDbHelper.TABLE_NAME, new String[]{"_id","name", "pass"}, null, null, null, null, null);
        myAdapter = new MyAdapter(this,c);
        System.out.println("Cursor count: "+c.getCount());
        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(myAdapter);
    }
    public void ClearItem(View view)
    {
        Cursor c = db.query(MyDbHelper.TABLE_NAME, new String[]{"_id","name", "pass"}, null, null, null, null, null);
        myAdapter.changeCursor(c);
    }
}
