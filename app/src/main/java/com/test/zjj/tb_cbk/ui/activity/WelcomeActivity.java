package com.test.zjj.tb_cbk.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.test.zjj.tb_cbk.R;
import com.test.zjj.tb_cbk.utils.Constant;
import com.test.zjj.tb_cbk.utils.Pref_Utils;


public class WelcomeActivity extends Activity {

    private int[] res = new int[]{R.drawable.slide1,R.drawable.slide2,R.drawable.slide3};
    private ImageView[] imageViews = new ImageView[res.length];
    private LinearLayout linearLayout;
    private ViewPager viewPager;
    private int pre_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.welcome_vp);
        linearLayout = (LinearLayout) findViewById(R.id.welcome_ll);

        LinearLayout.LayoutParams params_point = new LinearLayout.LayoutParams(20, 20);
        params_point.leftMargin=10;
        LinearLayout.LayoutParams params_img = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView imageView = null;
        View view = null;
        for (int i = 0; i < res.length; i++) {
            imageView = new ImageView(this);
            imageView.setImageResource(res[i]);
            //没有设置scale属性，图片不填充
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(params_img);
            if (i==res.length-1){
                //给最后一张图片监听进入主页面
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Pref_Utils.putBoolean(WelcomeActivity.this, Constant.PRE_KEY_FIRST_OPEN, false);

                        Intent intent = new Intent(WelcomeActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
            imageViews[i] = imageView;

            view = new View(this);
            if (i==0) {
                view.setBackgroundResource(R.drawable.point_now);
            }else {
                view.setBackgroundResource(R.drawable.ponit);
            }
            view.setLayoutParams(params_point);
            linearLayout.addView(view);
        }

        //设置适配器
        viewPager.setAdapter(new WelcomeViewPagerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                linearLayout.getChildAt(position).setBackgroundResource(R.drawable.point_now);
                linearLayout.getChildAt(pre_index).setBackgroundResource(R.drawable.ponit);
                pre_index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class WelcomeViewPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return imageViews.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews[position]);
            return imageViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews[position]);
        }

    }
}
