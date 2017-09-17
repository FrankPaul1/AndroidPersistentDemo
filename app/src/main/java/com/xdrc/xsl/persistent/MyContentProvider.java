package com.xdrc.xsl.persistent;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by yoyo on 2017/9/17.
 */

public class MyContentProvider extends ContentProvider {
    private MyDBHelper dbHelper = null;
    private static final String AUTHORITY = "com.xdrc.xsl.persistent.MyContentProvider";

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int PERSONS = 1;

    static {
        URI_MATCHER.addURI(AUTHORITY, MyDBHelper.TABLE, PERSONS);
    }

    public MyContentProvider(Context ctx) {
        this.dbHelper = new MyDBHelper(ctx);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = null;

        db.execSQL("SELECT * FORM " + MyDBHelper.TABLE);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
