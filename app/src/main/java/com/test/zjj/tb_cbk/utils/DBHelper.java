package com.test.zjj.tb_cbk.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.test.zjj.tb_cbk.beans.Info;
import com.test.zjj.tb_cbk.beans.TabInfo;

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

    public DBHelper(Context context,String name){
        super(context, name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE [history] (" +
                "  [url] TEXT NOT NULL, " +
                "  [title] VARCHAR(50), " +
                "  [desc] TEXT, " +
                "  [content] text, " +
                "  [channel] VARCHAR(10), " +
                "  [time] VARCHAR(30), " +
                "  [source] VARCHAR(10), " +
                "  [addtime] INTEGER NOT NULL, "+
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor query(SQLiteDatabase db,String table,String[] columns,String selection,String[] selectArgs){
        return db.query(table,columns,selection,selectArgs,null,null,null);
    }

    public static boolean checkedCollection(SQLiteDatabase db,String mUrl){
        Cursor cursor = db.query("collection", new String[]{"url"}, "url=?", new String[]{mUrl}, null, null, null);
        int couunt = cursor.getCount();
        Log.i("atest", "-->checkedCollection: countColl:" + couunt);
        if (couunt==1){
            return true;
        }
        return false;
    }

    public static boolean checkedHistory(SQLiteDatabase db,String mUrl){
        Cursor cursor = db.query("history",new String[]{"url"},"url=?",new String[]{mUrl},null,null,null);
        int count = cursor.getCount();
        if (count==1){
            return true;
        }
        return false;
    }

    public static boolean saveCollection(SQLiteDatabase db,Info info){
        ContentValues values = new ContentValues();
        values.put("url",info.getLink());
        values.put("title",info.getTitle());
        values.put("desc",info.getDesc());
        values.put("content",info.getHtml());
        values.put("channel",info.getChannelName());
        values.put("time",info.getPubDate());
        values.put("source",info.getSource());
        values.put("savetime", System.currentTimeMillis());
        long result = db.insert("collection",null,values);
        return result==-1?false:true;
    }

    public static boolean addHistory(SQLiteDatabase db,Info info){
        ContentValues values = new ContentValues();
        values.put("url",info.getLink());
        values.put("title",info.getTitle());
        values.put("desc",info.getDesc());
        values.put("content",info.getHtml());
        values.put("channel",info.getChannelName());
        values.put("time",info.getPubDate());
        values.put("source",info.getSource());
        values.put("addtime",System.currentTimeMillis());
        long result = db.insert("history",null,values);
        return result==-1?false:true;
    }

    public static void updateTabs(SQLiteDatabase db,List<TabInfo> tabInfos){
        if (tabInfos==null) return;
        for (int i = 0; i < tabInfos.size(); i++) {
            ContentValues values = new ContentValues();
            values.put("id",tabInfos.get(i).getChannelId());
            values.put("name",tabInfos.get(i).getName());
            db.insert("tabs",null,values);
        }
        db.close();

    }

}
