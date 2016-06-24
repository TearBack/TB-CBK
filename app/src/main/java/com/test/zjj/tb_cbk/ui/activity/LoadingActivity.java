package com.test.zjj.tb_cbk.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;

import com.test.zjj.httplib.HttpHelper;
import com.test.zjj.httplib.Request;
import com.test.zjj.httplib.StringRequest;
import com.test.zjj.tb_cbk.R;
import com.test.zjj.tb_cbk.application.ConstantKey;
import com.test.zjj.tb_cbk.beans.TabInfo;
import com.test.zjj.tb_cbk.utils.Constant;
import com.test.zjj.tb_cbk.utils.JSON_Utils;
import com.test.zjj.tb_cbk.utils.Pref_Utils;

import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final Intent intent = new Intent(LoadingActivity.this,HomeActivity.class);
        StringRequest request = new StringRequest(ConstantKey.CLASS_URL, "GET", new Request.CallBack() {
            @Override
            public void onResoponse(Object o) {
                String json = (String) o;
                try {
                    String jsonArr = JSON_Utils.getChannelJsonArr(json);
                    if (jsonArr!=null){
                        List<TabInfo> tabList = JSON_Utils.parseJson2List(TabInfo.class, jsonArr);
                        Log.i("test", "-->onResoponse: tab:" + tabList.get(0).getName());
                        for (int i = 0; i < 35; i++) {
                            tabList.remove(9);//只留下九条
                        }
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("tabs", (ArrayList<? extends Parcelable>) tabList);
                        //bundle.putParcelable("tabs", (Parcelable) tabList);
                        intent.putExtra("tabs", bundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        HttpHelper.addRequest(request);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //如果是第一次打开程序，那么跳转到欢迎界面，否则进度主界面
                if (Pref_Utils.getBoolean(LoadingActivity.this, Constant.PRE_KEY_FIRST_OPEN, true)) {
                    intent.setClass(LoadingActivity.this, WelcomeActivity.class);
                }else {
                    intent.setClass(LoadingActivity.this,HomeActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },3*1000);
    }

    @Override
    protected void onDestroy() {
        Log.i("atest", "-->onDestroy: :" + "");
//        HttpHelper.stopWork();
        super.onDestroy();
    }
}
