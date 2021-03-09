package com.hxuanyu.tools.maogai.Activity;

import android.graphics.Color;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.hxuanyu.tools.maogai.R;

import spa.lyh.cn.statusbarlightmode.ImmersionConfiguration;
import spa.lyh.cn.statusbarlightmode.ImmersionMode;

public class AboutActivity extends BaseActivity {

    private ImmersionMode immersionMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBar();
        setContentView(R.layout.activity_about);
        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.about_toolbar);
        mToolbarTb.setTitle("关于");
        mToolbarTb.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbarTb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setBar() {
        //初始化对应的配置
        ImmersionConfiguration configuration = new ImmersionConfiguration
                .Builder(this)
                .enableImmersionMode(ImmersionConfiguration.ENABLE)
                .setColor(R.color.bar_color)
                .build();
        //完成ImmersionMode的配置初始化
        ImmersionMode.getInstance().init(configuration);
        immersionMode = ImmersionMode.getInstance();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        immersionMode.execImmersionMode(this);
    }
}
