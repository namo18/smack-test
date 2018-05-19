package com.mycompany.myfirstapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by ghost on 2018-3-16.
 */

public class MyAdapter extends CursorAdapter{
    protected TextView a ;
    protected TextView b;

    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, Cursor c){
        super(context,c);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return layoutInflater.inflate(R.layout.item ,parent,false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        final TextView ta = view.findViewById(R.id.tv_a);
        Button btnAccept = view.findViewById(R.id.btn_accept);
        Button btnReject_ = view.findViewById(R.id.btn_reject);
        ta.setText(cursor.getString(1));

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Click Accept id="+ta.getText().toString());
            }
        });
        btnReject_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Click Reject id="+ta.getText().toString());
            }
        });
    }
}
