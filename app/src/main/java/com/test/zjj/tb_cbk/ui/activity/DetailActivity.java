package com.test.zjj.tb_cbk.ui.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.test.zjj.tb_cbk.R;
import com.test.zjj.tb_cbk.beans.Info;
import com.test.zjj.tb_cbk.utils.DBHelper;

public class DetailActivity extends AppCompatActivity {

    private TextView tv_title, tv_channel, tv_time, tv_source, tv_desc, tv_html;
    private Info mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();

        mInfo = getIntent().getExtras().getParcelable("info");
        if (mInfo != null) {
            tv_title.setText(mInfo.getTitle());
            tv_channel.setText(mInfo.getChannelName());
            tv_time.setText(mInfo.getPubDate());
            tv_source.setText(mInfo.getSource());
            tv_desc.setText(mInfo.getDesc());
            String html = mInfo.getHtml();
            Log.i("atest", "-->onCreate: html:" + html);
            if (html != null) {
                tv_html.setText(Html.fromHtml(html));
            }
            DBHelper helper = new DBHelper(this, "chabaike");
            SQLiteDatabase wdb = helper.getWritableDatabase();
            SQLiteDatabase rdb = helper.getReadableDatabase();
            boolean isAdd = DBHelper.checkedHistory(wdb, mInfo.getLink());
            if (isAdd) {
                Log.i("atest", "-->onCreate: :" + "历史已经添加过了!");
            } else {
                boolean isSu = DBHelper.addHistory(wdb, mInfo);
                if (isSu) {
                    Log.i("atest", "-->onCreate: :" + "历史添加成功！");
                } else {
                    Log.i("atest", "-->onCreate: :" + "历史添加失败！");
                }
            }
        }


    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        toolbar.setTitle("详细页面");
        toolbar.setTitleTextColor(Color.BLUE);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        tv_title = (TextView) findViewById(R.id.detail_tv_title);
        tv_channel = (TextView) findViewById(R.id.detail_tv_channel);
        tv_time = (TextView) findViewById(R.id.detail_tv_time);
        tv_source = (TextView) findViewById(R.id.detail_tv_source);
        tv_desc = (TextView) findViewById(R.id.detail_tv_desc);
        tv_html = (TextView) findViewById(R.id.detail_tv_html);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.toolbar_more:
                Intent intent = new Intent(DetailActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.toolbar_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "MyShare");
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "分享到"));
                break;
            case R.id.toolbar_save:
                //保存书签
                DBHelper helper = new DBHelper(DetailActivity.this, "chabaike");
                SQLiteDatabase db = helper.getReadableDatabase();
                SQLiteDatabase dbw = helper.getWritableDatabase();
                boolean isCollected = DBHelper.checkedCollection(db, mInfo.getLink());
                if (!isCollected) {
                    if (DBHelper.saveCollection(dbw, mInfo)) {
                        Toast.makeText(DetailActivity.this, "收藏成功!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivity.this, "收藏失败！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailActivity.this, "该文章已经收藏！", Toast.LENGTH_SHORT).show();
                }
                db.close();
                dbw.close();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
