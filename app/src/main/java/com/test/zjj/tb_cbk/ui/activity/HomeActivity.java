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
import com.test.zjj.httplib.Request;
import com.test.zjj.httplib.StringRequest;
import com.test.zjj.tb_cbk.R;
import com.test.zjj.tb_cbk.application.ConstantKey;
import com.test.zjj.tb_cbk.beans.TabInfo;
import com.test.zjj.tb_cbk.ui.fragment.ContentFragment;
import com.test.zjj.tb_cbk.utils.JSON_Utils;

import java.util.List;


public class HomeActivity extends FragmentActivity {
    private TabLayout mTabs;
    private ViewPager mVp;
    private List<TabInfo> tabInfos;
    private List<TabInfo> tabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState!= null)
        {
            String FRAGMENTS_TAG = "android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Bundle bundle = getIntent().getBundleExtra("tabs");
        if (bundle != null) {
            tabInfos = bundle.getParcelableArrayList("tabs");
        } else {
            //标题
            onCreateTabs();
        }

        initView();
    }

    private void onCreateTabs() {
        StringRequest request = new StringRequest(ConstantKey.CLASS_URL, "GET", new Request.CallBack() {
            @Override
            public void onResoponse(Object o) {
                String json = (String) o;
                String jsonArr = null;
                try {
                    jsonArr = JSON_Utils.getChannelJsonArr(json);
                    if (jsonArr != null) {
                        tabList = JSON_Utils.parseJson2List(TabInfo.class, jsonArr);
                        Log.i("test", "-->onResoponse: tab:" + tabList.get(0).getName());
                        for (int i = 0; i < 35; i++) {
                            tabList.remove(9);//只留下九条
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        HttpHelper.addRequest(request);
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
            Log.i("test", "-->getItem: position:" + position);
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
            return tabInfos.get(position).getName();
        }
    }

    @Override
    protected void onDestroy() {
        HttpHelper.stopWork();
        super.onDestroy();
    }
}
