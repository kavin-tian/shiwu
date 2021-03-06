package com.example.shiwu;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.shiwu.adapter.MyFragmentPagerAdapter;
import com.example.shiwu.views.MyToolbar;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolBar();
        initContent();
    }

    private void initToolBar() {
        MyToolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "搜索", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initContent() {
        String[] pageTitles = {"今日更新",
                "行业资讯",
                "客厅收纳",
                "八分生活",
                "美居美家",
                "厨房整理",
                "卫生间收纳",
                "综合整理",
                "美食特工",
                "旅行游记",
        };
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new PageFragment());
        fragments.add(new PageFragment());
        fragments.add(new PageFragment());
        fragments.add(new PageFragment());
        fragments.add(new PageFragment());
        fragments.add(new PageFragment());
        fragments.add(new PageFragment());
        fragments.add(new PageFragment());
        fragments.add(new PageFragment());
        fragments.add(new PageFragment());

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragments,pageTitles);
        viewPager.setAdapter(adapter);


        final SmartTabLayout viewPagerTab = (SmartTabLayout) rootView.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

        setTabTextColor(adapter, viewPagerTab, 0);
        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getContext(), "HomeFragment: " + position, Toast.LENGTH_SHORT).show();
                setTabTextColor(adapter, viewPagerTab, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setTabTextColor(FragmentPagerAdapter adapter, SmartTabLayout viewPagerTab, int position) {
        int childCount = adapter.getCount();

        for (int i = 0; i < childCount; i++) {
            if (i == position) {
                TextView tabAt = (TextView) viewPagerTab.getTabAt(position);
                tabAt.setTextColor(Color.BLACK);
                tabAt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                //加粗字体
                tabAt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                TextView tabAt = (TextView) viewPagerTab.getTabAt(i);
                tabAt.setTextColor(Color.parseColor("#8D000000"));

                //android中为textview动态设置字体为粗体
                tabAt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                //正常字体
                tabAt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

            }

        }
    }


}
