package com.example.shiwu.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shiwu.BaseFragment;

import java.util.ArrayList;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "---MyFragmentAdapter";
    private BaseFragment[] mFragments;
    private String[] pageTitles;
    private ArrayList<BaseFragment> fragments;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments, String[] pageTitles) {
        super(fm);
        this.fragments = fragments;
        this.mFragments = new BaseFragment[fragments.size()];
        this.pageTitles = pageTitles;
//        Log.e(TAG, "MyFragmentPagerAdapter: ");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        BaseFragment baseFragment = fragments.get(position);
//        Log.e(TAG, "getItem: " + baseFragment);
        return fragments.get(position);
    }

    @Override
    public int getCount() {
//        Log.e(TAG, "getCount: ");
        return fragments.size();
    }

    @Override
    public long getItemId(int position) {
//        Log.e(TAG, "getItemId: " + position);
        return position;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseFragment fragment = (BaseFragment) super.instantiateItem(container, position);
        mFragments[position] = fragment;
//        Log.e(TAG, "instantiateItem: ");
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }

}