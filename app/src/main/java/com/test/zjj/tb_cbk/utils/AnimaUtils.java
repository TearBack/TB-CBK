package com.test.zjj.tb_cbk.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

/**
 * Created by Administrator on 2016/6/24.
 */
public class AnimaUtils {
    public static void setLoadingAnima(View view){
        RotateAnimation animation = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(2000);
        animation.setRepeatCount(5);
        view.startAnimation(animation);
    }
}
