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

        ListAdapter listAdapter;
        CursorAdapter cursorAdapter;

        ListView lv = (ListView) findViewById(R.id.listView);

        MyDbHelper dbHelper = MyDbHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();

        Button btn_query = findViewById(R.id.btn_query_db);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.query(MyDbHelper.TABLE_NAME, new String[]{"_id","name", "pass"}, null, null, null, null, null);

                while(c.moveToNext())
                {
                    System.out.println("line:"+c.getString(1));
                }
                c.close();
            }
        });

        Cursor c = db.query(MyDbHelper.TABLE_NAME, new String[]{"_id","name", "pass"}, null, null, null, null, null);

        while(c.moveToNext())
        {
            System.out.println("line:"+c.getString(1));
        }
        lv.setAdapter(new CursorAdapter(this,c) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
                View view=inflater.inflate(R.layout.item ,parent,false);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView ta = view.findViewById(R.id.tv_a);
                ta.setText(cursor.getString(1));
            }
        });
        c.close();
    }

    public void addItem(View view)
    {

        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        Random random = new Random();
        values.put("name", random.nextInt());
        values.put("pass","password");
        Uri uri =Uri.parse("content://" + MyContentProvider.AUTHORITY + "/test");
        contentResolver.insert(uri,values);
    }
}
