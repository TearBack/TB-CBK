package com.test.zjj.tb_cbk.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.test.zjj.tb_cbk.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.settings_collect).setOnClickListener(this);
        findViewById(R.id.settings_history).setOnClickListener(this);
        findViewById(R.id.settings_version).setOnClickListener(this);
        findViewById(R.id.settings_suggest).setOnClickListener(this);
        intent = new Intent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_collect:
                intent.setClass(SettingsActivity.this,CollectionActivity.class);
                break;
            case R.id.settings_history:
                intent.setClass(SettingsActivity.this,CollectionActivity.class);
                intent.putExtra("isHistory",true);
                break;
            case R.id.settings_version:
                intent.setClass(SettingsActivity.this,VersionActivity.class);
                break;
            case R.id.settings_suggest:
                intent.setClass(SettingsActivity.this,SuggestActivity.class);
                break;
        }
        startActivity(intent);
    }
}
