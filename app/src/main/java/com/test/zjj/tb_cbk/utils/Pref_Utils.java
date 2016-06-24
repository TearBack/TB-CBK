package com.test.zjj.tb_cbk.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/6/20.
 */
public class Pref_Utils {

    public static void putBoolean(Context context,String name,boolean isFirstOpen){
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(name, isFirstOpen);
        editor.commit();
    }

    public static boolean getBoolean(Context context,String name,boolean mDefault){
        SharedPreferences sp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        boolean isFirstOpen = sp.getBoolean(name,mDefault);
        return isFirstOpen;
    }
}
