package com.test.zjj.tb_cbk.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.test.zjj.tb_cbk.beans.Info;
import com.test.zjj.tb_cbk.beans.TabInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class DBHelper extends SQLiteOpenHelper {
    private DBHelper helper;
    private static final int VERSION = 1;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name) {
        super(context, name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("testi", "LineNum:32-->DBHelper-->onCreate: :" + "");
        db.execSQL("CREATE TABLE [history] (" +
                "  [url] TEXT NOT NULL, " +
                "  [title] VARCHAR(50), " +
                "  [desc] TEXT, " +
                "  [content] text, " +
                "  [channel] VARCHAR(10), " +
                "  [time] VARCHAR(30), " +
                "  [source] VARCHAR(10), " +
                "  [addtime] INTEGER NOT NULL, " +
                "  CONSTRAINT [] PRIMARY KEY ([url]));");

        db.execSQL("CREATE TABLE [collection] (" +
                "  [url] TEXT NOT NULL, " +
                "  [title] VARCHAR(50), " +
                "  [desc] TEXT, " +
                "  [content] text, " +
                "  [channel] VARCHAR(10), " +
                "  [time] VARCHAR(30), " +
                "  [source] VARCHAR(10), " +
                "  [savetime] INTEGER NOT NULL, " +
                "  CONSTRAINT [] PRIMARY KEY ([url]));");

        db.execSQL("CREATE TABLE [tabs] (" +
                "  [id] VARCHAR(50), " +
                "  [name] VARCHAR(10), " +
                "  CONSTRAINT [] PRIMARY KEY ([id]));");
        String[] ids = new String[]{"5572a108b3cdc86cf39001cd", "5572a108b3cdc86cf39001ce", "5572a108b3cdc86cf39001cf",
                "5572a108b3cdc86cf39001d0", "5572a108b3cdc86cf39001d1", "5572a108b3cdc86cf39001d2",
                "5572a108b3cdc86cf39001d3", "5572a108b3cdc86cf39001d4", "5572a108b3cdc86cf39001d5"};
        String[] names = new String[]{"国内焦点", "国际焦点", "军事焦点",
                "财经焦点", "互联网焦点", "房产焦点",
                "汽车焦点", "体育焦点", "娱乐焦点"};
        for (int i = 0; i < ids.length; i++) {
            ContentValues values = new ContentValues();
            values.put("id", ids[i]);
            values.put("name", names[i]);
            db.insert("tabs", null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static boolean checkedCollection(SQLiteDatabase db, String mUrl) {
        Cursor cursor = db.query("collection", new String[]{"url"}, "url=?", new String[]{mUrl}, null, null, null);
        int couunt = cursor.getCount();
        Log.i("atest", "-->checkedCollection: countColl:" + couunt);
        if (couunt == 1) {
            return true;
        }
        return false;
    }

    public static boolean checkedHistory(SQLiteDatabase db, String mUrl) {
        Cursor cursor = db.query("history", new String[]{"url"}, "url=?", new String[]{mUrl}, null, null, null);
        int count = cursor.getCount();
        if (count == 1) {
            return true;
        }
        return false;
    }

    public static boolean saveCollection(SQLiteDatabase db, Info info) {
        ContentValues values = new ContentValues();
        values.put("url", info.getLink());
        values.put("title", info.getTitle());
        values.put("desc", info.getDesc());
        values.put("content", info.getHtml());
        values.put("channel", info.getChannelName());
        values.put("time", info.getPubDate());
        values.put("source", info.getSource());
        values.put("savetime", System.currentTimeMillis());
        long result = db.insert("collection", null, values);
        return result == -1 ? false : true;
    }

    public static boolean addHistory(SQLiteDatabase db, Info info) {
        ContentValues values = new ContentValues();
        values.put("url", info.getLink());
        values.put("title", info.getTitle());
        values.put("desc", info.getDesc());
        values.put("content", info.getHtml());
        values.put("channel", info.getChannelName());
        values.put("time", info.getPubDate());
        values.put("source", info.getSource());
        values.put("addtime", System.currentTimeMillis());
        long result = db.insert("history", null, values);
        return result == -1 ? false : true;
    }

    public static void updateTabs(SQLiteDatabase db, List<TabInfo> tabInfos) {
        if (db == null || tabInfos == null || tabInfos.size() == 0) return;
        for (int i = 0; i < tabInfos.size(); i++) {
            ContentValues values = new ContentValues();
            values.put("id", tabInfos.get(i).getChannelId());
            values.put("name", tabInfos.get(i).getName());
            db.insert("tabs", null, values);
        }
        db.close();
    }

    public static List<TabInfo> onCreatTabs(SQLiteDatabase rdb){
        List<TabInfo> tabInfos = new ArrayList<>();
        Cursor cursor = rdb.query("tabs",null,null,null,null,null,null);
        TabInfo tabInfo = null;
        while (cursor.moveToNext()){
            tabInfo = new TabInfo();
            tabInfo.setChannelId(cursor.getString(cursor.getColumnIndex("id")));
            tabInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
            tabInfos.add(tabInfo);
        }
        rdb.close();
        return tabInfos;
    }
}
