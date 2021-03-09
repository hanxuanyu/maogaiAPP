package com.hxuanyu.tools.maogai.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.LayoutRes;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hxuanyu.tools.maogai.R;
import com.hxuanyu.tools.maogai.adapter.CardFragmentPagerAdapter;
import com.hxuanyu.tools.maogai.events.ShadowTransformer;
import com.hxuanyu.tools.maogai.utils.ActivityCollector;
import com.hxuanyu.tools.maogai.utils.MyToast;
import com.xiaomi.market.sdk.UpdateResponse;
import com.xiaomi.market.sdk.UpdateStatus;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;
import com.xiaomi.market.sdk.XiaomiUpdateListener;

import spa.lyh.cn.statusbarlightmode.ImmersionConfiguration;
import spa.lyh.cn.statusbarlightmode.ImmersionMode;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private Button LButton,NButton;
    private ViewPager mViewPager;
    private ImmersionMode immersionMode;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private TextView currentQuestion;
    private Toolbar toolbar;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private int currentCptIndex = 0;
    private final SeekBar seekBar = null;
    private final String[] chapters = new String[]{"第一章", "第二章", "第三章", "第四章", "第五章", "第六章", "第七章","第八章", "第九章", "第十章", "第十一章", "第十二章", "第十三章", "第十四章","判断题"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBar();//设置状态栏样式
        setContentView(R.layout.activity_main);
        checkUpdate();
        init();
        setSupportActionBar(toolbar);
    }

    private void checkUpdate() {
        XiaomiUpdateAgent.setUpdateAutoPopup(true);
        XiaomiUpdateAgent.setUpdateListener(new XiaomiUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.STATUS_UPDATE:
                        MyToast.show(MainActivity.this,"有更新");
                        // 有更新， UpdateResponse为本次更新的详细信息
                        // 其中包含更新信息，下载地址，MD5校验信息等，可自行处理下载安装
                        // 如果希望 SDK继续接管下载安装事宜，可调用
                        //  XiaomiUpdateAgent.arrange()
                        break;
                    case UpdateStatus.STATUS_NO_UPDATE:
                        MyToast.show(MainActivity.this,"无更新");
                        // 无更新， UpdateResponse为null
                        break;
                    case UpdateStatus.STATUS_NO_WIFI:
                        // 设置了只在WiFi下更新，且WiFi不可用时， UpdateResponse为null
                        break;
                    case UpdateStatus.STATUS_NO_NET:
                        // 没有网络， UpdateResponse为null
                        MyToast.show(MainActivity.this,"没有网络");
                        break;
                    case UpdateStatus.STATUS_FAILED:
                        //MyToast.show(MainActivity.this,"请稍后再试");
                        // 检查更新与服务器通讯失败，可稍后再试， UpdateResponse为null
                        break;
                    case UpdateStatus.STATUS_LOCAL_APP_FAILED:
                        //MyToast.show(MainActivity.this,"本地检查失败");
                        // 检查更新获取本地安装应用信息失败， UpdateResponse为null
                        break;
                    default:
                        break;
                }
            }
        });
        XiaomiUpdateAgent.update(this,true);
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        currentQuestion = findViewById(R.id.current_question);
        LButton = (Button) findViewById(R.id.switch_chapter);
        NButton = (Button) findViewById(R.id.reset_question);
        LButton.setOnClickListener(this);
        NButton.setOnClickListener(this);
        toolbar = findViewById(R.id.toolbar);
        mFragmentCardAdapter = new CardFragmentPagerAdapter(MainActivity.this,getSupportFragmentManager(),
                dpToPixels(2, this),chapters[currentCptIndex]+".json");
        mViewPager.setAdapter(mFragmentCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);
        mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        changeChapter(chapters[getChapter()]);

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentQuestion.setText("第"+(position+1)+"题,共"+mViewPager.getAdapter().getCount()+"题");
                saveProgress();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(getProgress());

        mFragmentCardShadowTransformer.enableScaling(true);
        currentQuestion.setText("第"+(mViewPager.getCurrentItem()+1)+"题,共"+mViewPager.getAdapter().getCount()+"题");
    }

    private void changeChapter(String desChapter){
        mViewPager.setCurrentItem(0,false);
        CardFragmentPagerAdapter temeAdapter = new CardFragmentPagerAdapter(MainActivity.this,getSupportFragmentManager(),
                dpToPixels(2, this),desChapter+".json");
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager,temeAdapter);
        mFragmentCardShadowTransformer.enableScaling(true);
        mViewPager.setAdapter(temeAdapter);
        //LButton.setText("当前"+desChapter);
        mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        currentQuestion.setText("第"+(mViewPager.getCurrentItem()+1)+"题,共"+mViewPager.getAdapter().getCount()+"题");
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reset_question:
                showCustomizeDialog();
                break;
            case R.id.switch_chapter:
            {
                //final String[] chapters = new String[]{"第八章", "第九章", "第十章", "第十一章", "第十二章", "第十三章", "第十四章"};
                alert = null;
                builder = new AlertDialog.Builder(MainActivity.this);
                alert = builder.setIcon(R.mipmap.message)
                        .setTitle("选择你想复习的章节")
                        .setItems(chapters, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                changeChapter(chapters[which]);
                                currentCptIndex=which;
                                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                                editor.putInt("currentCpt", currentCptIndex);
                                editor.commit();//提交修改
                                mViewPager.setCurrentItem(0);
                                MyToast.show(getApplicationContext(),"你选择了" + chapters[which]);
                            }
                        }).create();
                alert.show();
            }
                break;
        }
    }


    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }



    private void saveProgress(){
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putInt("currentItem", mViewPager.getCurrentItem());
        editor.putInt("currentCpt", currentCptIndex);
        editor.commit();//提交修改
    }

    private int getProgress(){
        SharedPreferences preferences = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        int i =  preferences.getInt("currentItem", 0);
        return i;
    }

    private int getChapter(){
        SharedPreferences preferences = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        int i =  preferences.getInt("currentCpt", 0);
        return i;
    }

    private void showCustomizeDialog() {
        /* @setView 装入自定义View ==> R.layout.dialog_customize
         * 由于dialog_customize.xml只放置了一个EditView，因此和图8一样
         * dialog_customize.xml可自定义更复杂的View
         */
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(MainActivity.this);
        final View dialogView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.dialog_jump_question,null);
        customizeDialog.setTitle("请拖动滑块至指定题目");
        customizeDialog.setView(dialogView);
        final SeekBar seekBar = dialogView.findViewById(R.id.dialog_seek_bar);


        seekBar.setMax(mViewPager.getAdapter().getCount()-1);
        seekBar.setProgress(mViewPager.getCurrentItem());
        MyToast.show(MainActivity.this,mViewPager.getCurrentItem()+"");
        final TextView dialogtextView = dialogView.findViewById(R.id.dialog_tip);
        dialogtextView.setText("跳转到 "+(seekBar.getProgress()+1)+"/"+(seekBar.getMax()+1));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dialogtextView.setText("跳转到 "+(seekBar.getProgress()+1)+"/"+(seekBar.getMax()+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        customizeDialog.setPositiveButton("跳转",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mViewPager.setCurrentItem(seekBar.getProgress(),true);
                    }
                });
        customizeDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                checkUpdate();
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
                break;
            case R.id.quit:
                ActivityCollector.finishAll();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveProgress();
    }
}
