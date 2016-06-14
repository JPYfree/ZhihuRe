package com.example.admin.zhihu_re.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.zhihu_re.entity.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/10.
 */
public class DailyNewsDB {

    private SQLiteDatabase database;
    private static DailyNewsDB dailyNewsDB;

    private DailyNewsDB(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public synchronized static DailyNewsDB getInstance(Context context) {
        if (dailyNewsDB == null) {
            dailyNewsDB = new DailyNewsDB(context);
        }
        return dailyNewsDB;
    }

    public void saveFavourite(News news) {
        if (news != null) {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_NEWS_ID, news.getId());
            values.put(DBHelper.COLUMN_NEWS_TITLE, news.getTitle());
            values.put(DBHelper.COLUMN_NEWS_IMAGE, news.getImage());
            database.insert(DBHelper.TABLE_NAME, null, values);
        }
    }

    public List<News> loadFavourite() {
        List<News> favouriteList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setId(cursor.getInt(1));
                news.setTitle(cursor.getString(2));
                news.setImage(cursor.getString(3));
                favouriteList.add(news);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return favouriteList;
    }

    public void deleteFavourite(News news) {
        if (news != null) {
            database.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_NEWS_ID + " = ?",
                    new String[] {news.getId() + ""});
        }
    }

    public boolean isFavourite(News news) {
        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, DBHelper.COLUMN_NEWS_ID + " = ?",
                new String[] {news.getId() + ""}, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }else {
            return false;
        }
    }

    public synchronized void closeDB() {
        if (dailyNewsDB != null) {
            database.close();
        }
    }
}
