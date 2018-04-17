package com.mycompany.myfirstapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    //构造函数。每当数据库的数据发生改变时，适配器将调用requery()重新查询以显示最新的数据。
    public MyAdapter(Context context, Cursor c, boolean autoRequery)
    {
        super(context,c,autoRequery);
        // TODO Auto-generated constructor stub

    }
    @Override
    public void bindView(View view, Context context, Cursor cursor){
        setChildView(view,cursor);
    }

    public void setChildView(View view, Cursor cursor)
    {
        TextView a = (TextView)view.findViewById(R.id.tv_a);
//        TextView b = (TextView)view.findViewById(R.id.tv_b);
        if(cursor.moveToNext() && cursor.getCount()>0){
            a.setText(cursor.getString(cursor.getColumnIndex("name")));
//            b.setText(cursor.getString(cursor.getColumnIndex("pass")));
        }
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup){
        //View view = LayoutInflater.from(context).inflate(R.layout.item,null);
        View view = layoutInflater.inflate(R.layout.item,null);
        setChildView(view,cursor);
        return view;
    }
}
