package com.xdrc.xsl.persistent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yoyo on 2017/9/17.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String TABLE = "person";
    public static final String DB_NAME = "test.db";
    public static final int version = 3;

//    private String createSql = "CREATE TABLE IF NOT EXISTS " + TABLE +
//            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//            "username VARCHAR," +
//            "age INTEGER)";
    private String createSql = "CREATE TABLE IF NOT EXISTS " + TABLE +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username VARCHAR," +
            "age INTEGER," +
            "status INTEGER DEFAULT 1)";

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("ALTER TABLE " + TABLE + " ADD COLUMN status INTEGER");
    }
}
