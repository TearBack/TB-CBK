package com.test.zjj.tb_cbk.ui.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.test.zjj.tb_cbk.R;
import com.test.zjj.tb_cbk.adapter.CollectListAdapter;
import com.test.zjj.tb_cbk.beans.Collection;
import com.test.zjj.tb_cbk.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private List<Collection> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        Boolean isHistory = getIntent().getBooleanExtra("isHistory", false);
        ListView listView = (ListView) findViewById(R.id.collect_lv);
        TextView tv_tile = (TextView) findViewById(R.id.collect_tv_title);
        DBHelper helper = new DBHelper(this, "chabaike");
        SQLiteDatabase rdb = helper.getReadableDatabase();

        if (!isHistory) {
            Cursor cursor = rdb.query("collection", null, null, null, null, null, null);
            long count = cursor.getCount();
            Log.i("atest", "-->onCreate: countCollectionAc:" + count);
            if (count != 0) {
                list = getCollectionList(cursor);
            }
        }else {
            Cursor cursor = rdb.query("history",null,null,null,null,null,null);
            long count = cursor.getCount();
            Log.i("atest", "-->onCreate: countHisotry:" + count);
            if (count!=0){
                list = getHistoryList(cursor);
            }
            tv_tile.setText("浏览历史");
        }
        rdb.close();
        CollectListAdapter adapter = new CollectListAdapter(list, this);
        listView.setAdapter(adapter);
    }

    private List<Collection> getHistoryList(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        List<Collection> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            long saveTime = cursor.getLong(cursor.getColumnIndex("addtime"));
            Collection collection = new Collection(title, saveTime);
            list.add(collection);
        }
        return list;
    }

    private List<Collection> getCollectionList(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        List<Collection> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            long saveTime = cursor.getLong(cursor.getColumnIndex("savetime"));
            Collection collection = new Collection(title, saveTime);
            list.add(collection);
        }
        return list;
    }
}
