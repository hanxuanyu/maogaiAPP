package com.hxuanyu.tools.maogai.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.hxuanyu.tools.maogai.Bean.QuestionsList;
import com.hxuanyu.tools.maogai.Fragment.CardFragment;
import com.hxuanyu.tools.maogai.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapter{
    private List<CardFragment> mFragments;
    private float mBaseElevation;
    private Context mContext;
    private String filename;
    private ArrayList<QuestionsList.Question> mQuestionList;
    public CardFragmentPagerAdapter(Context mContext,FragmentManager fm, float baseElevation,String filename) {
        super(fm);
        mFragments = new ArrayList<>();
        mBaseElevation = baseElevation;
        this.mContext = mContext;
        this.filename = filename;
        parseUserData();
        for(int i = 0; i< mQuestionList.size(); i++){
            addCardFragment(CardFragment.newInstance(mQuestionList.get(i)));
        }
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mFragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        mFragments.set(position, (CardFragment) fragment);
        return fragment;
    }

    public void addCardFragment(CardFragment fragment) {
        mFragments.add(fragment);
    }

    private boolean parseUserData() {
        String strContent = Util.getJson(mContext,filename);
        if (!TextUtils.isEmpty(strContent)) {
            try {
                Gson mgson = new Gson();
                QuestionsList questionsList = mgson.fromJson(
                        strContent, QuestionsList.class);
                mQuestionList = questionsList.getQuestions();
                Log.e("解析成功",mQuestionList.size()+"");
                return true;
            } catch (Exception e) {
                Log.e("解析失败","yizhikong");
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
