package com.test.zjj.tb_cbk.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.test.zjj.httplib.HttpHelper;
import com.test.zjj.tb_cbk.R;
import com.test.zjj.tb_cbk.beans.TabInfo;
import com.test.zjj.tb_cbk.ui.fragment.ContentFragment;
import com.test.zjj.tb_cbk.utils.DBHelper;

import java.util.List;


public class HomeActivity extends FragmentActivity {
    private TabLayout mTabs;
    private ViewPager mVp;
    private List<TabInfo> tabInfos;
    private List<TabInfo> tabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState!= null)
        {   //启动销毁activity保存下来的fragment
            String FRAGMENTS_TAG = "android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);


//        Bundle bundle = getIntent().getBundleExtra("tabs");
//        if (bundle != null) {
//            tabInfos = bundle.getParcelableArrayList("tabs");
//        } else {
//            //标题
//            onCreateTabs();
//        }
        initTabs();
        initView();
    }

    private void initTabs() {
        DBHelper helper = new DBHelper(this,"chabaike");
        tabInfos = DBHelper.onCreatTabs(helper.getReadableDatabase());
    }

    private void initView() {
        mTabs = (TabLayout) findViewById(R.id.home_tab);
        mVp = (ViewPager) findViewById(R.id.home_vp);
        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mVp.setAdapter(new ContentAdapter(getSupportFragmentManager()));
        mTabs.setupWithViewPager(mVp);
    }

    public class ContentAdapter extends FragmentStatePagerAdapter {
        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("testi", "LineNum:90-->ContentAdapter-->getItem: fragment:" + position);
            //在需要的时候再创建Fragment
            ContentFragment cf = new ContentFragment();
            Bundle bundle = new Bundle();
            bundle.putString("channelID", tabInfos.get(position).getChannelId());
            cf.setArguments(bundle);
            return cf;
        }

        @Override
        public int getCount() {
            return tabInfos == null ? 0 : tabInfos.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabInfos==null?"没有标题":tabInfos.get(position).getName();
        }
    }

    @Override
    protected void onDestroy() {
        HttpHelper.stopWork();
        super.onDestroy();
    }
}
