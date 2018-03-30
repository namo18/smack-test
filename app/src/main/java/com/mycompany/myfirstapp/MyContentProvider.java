package com.mycompany.myfirstapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.security.Principal;

public class MyContentProvider extends ContentProvider {
    private static final int TEST_TABLE = 1;
    private static final int TEST_ID = 2;
    public static final String AUTHORITY = "com.mycompany.provider";
    private final UriMatcher uriMatcher;
    private MyDbHelper dbHelper;

    public MyContentProvider() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "test", TEST_TABLE);
        uriMatcher.addURI(AUTHORITY, "test/#", TEST_ID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        if(dbHelper==null)
        {
            dbHelper=MyDbHelper.getInstance(getContext());
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long rowId = db.insert("test",null,values);
        if(rowId>0){
            Uri noteUri = ContentUris.withAppendedId(Uri.parse("content://"+AUTHORITY +"/test"),rowId);
            if(getContext()!=null)
            {
                getContext().getContentResolver().notifyChange(noteUri,null);
            }
            return noteUri;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = MyDbHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("test",projection,selection,selectionArgs,null,null,sortOrder);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
