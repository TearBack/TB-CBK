package com.test.zjj.tb_cbk.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.test.zjj.tb_cbk.R;
import com.test.zjj.tb_cbk.application.ConstantKey;
import com.test.zjj.tb_cbk.utils.Pref_Utils;

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        final Intent intent = new Intent();
//        StringRequest request = new StringRequest(ConstantKey.CLASS_URL, "GET", new Request.CallBack() {
//            @Override
//            public void onResoponse(Object o) {
//                String json = (String) o;
//                try {
//                    String jsonArr = JSON_Utils.getChannelJsonArr(json);
//                    if (jsonArr!=null){
//                        List<TabInfo> tabList = JSON_Utils.parseJson2List(TabInfo.class, jsonArr);
//                        for (int i = 0; i < 35; i++) {
//                            tabList.remove(9);//只留下九条
//                        }
//                        Log.i("testi", "LineNum:38-->LoadingActivity-->onResoponse: tabs:" + tabList.toString());
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelableArrayList("tabs", (ArrayList<? extends Parcelable>) tabList);
//                        //bundle.putParcelable("tabs", (Parcelable) tabList);
//                        intent.putExtra("tabs", bundle);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });
//
//        HttpHelper.addRequest(request);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //如果是第一次打开程序，那么跳转到欢迎界面，否则进度主界面
                if (Pref_Utils.getBoolean(LoadingActivity.this, ConstantKey.PRE_KEY_FIRST_OPEN, true)) {
                    intent.setClass(LoadingActivity.this, WelcomeActivity.class);
                }else {
                    intent.setClass(LoadingActivity.this,HomeActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },2*1000);
    }

    @Override
    protected void onDestroy() {
        Log.i("testi", "LineNum:74-->LoadingActivity-->onDestroy: :" + "");
        super.onDestroy();
    }
}
