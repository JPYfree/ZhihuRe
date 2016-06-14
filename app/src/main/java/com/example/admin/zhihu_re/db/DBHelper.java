package com.example.admin.zhihu_re.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/10.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "daily_news.db";
    public static final String TABLE_NAME = "daily_news_fav";
    public static final int DB_VERSION = 1;
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NEWS_ID = "news_id";
    public static final String COLUMN_NEWS_TITLE = "news_title";
    public static final String COLUMN_NEWS_IMAGE = "news_image";

    public static final String DATABASE_CREATE = "create table " + TABLE_NAME
            + "( " + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NEWS_ID + " integer unique, "
            + COLUMN_NEWS_TITLE + " text, "
            + COLUMN_NEWS_IMAGE + " text );";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
}
