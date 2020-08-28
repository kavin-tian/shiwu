package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView tab0;
    private ImageView tab1;
    private ImageView tab2;
    private ImageView tab3;
    private ImageView tab4;
    private LinearLayout mainBottomeSwitcherContainer;
    private ArrayList<BaseFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //修改状态栏颜色

        initView();
        initFragmet();
        selectTab(0);
    }

    private void initView() {
        tab0 = findViewById(R.id.tab0);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        tab4 = findViewById(R.id.tab4);
        mainBottomeSwitcherContainer = findViewById(R.id.mainBottomeSwitcherContainer);
        tab0.setOnClickListener(this);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab0:
                selectTab(0);
                break;
            case R.id.tab1:
                selectTab(1);
                break;
            case R.id.tab2:
                selectTab(2);
                break;
            case R.id.tab3:
                selectTab(3);
                break;
            case R.id.tab4:
                break;
        }

    }


    public void selectTab(int index) {
//        changeUI(index);
        changeFragent(index);
    }


    /**
     * 优化: 使用显示和隐藏的方式, fragment切换时不会重复加载数据, 二造成屏幕显示闪烁
     */
    private void changeFragent(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        for (int i = 0; i < fragments.size(); i++) {
//            if (i == index) {
//                transaction.show(fragments.get(i));
//            } else {
//                transaction.hide(fragments.get(i));
//            }
//        }
        transaction.replace(R.id.frameLayout, fragments.get(index));
        transaction.commit();
    }

    private void changeUI(int index) {
        for (int i = 0; i < mainBottomeSwitcherContainer.getChildCount(); i++) {
            View child = mainBottomeSwitcherContainer.getChildAt(i);
            if (i == index) {
                setEnable(child, false);
            } else {
                setEnable(child, true);
            }
        }
    }

    private void setEnable(View view, boolean b) {
        //1.将view设置为不可用
        view.setEnabled(b);
        //2.处理view的孩子结点状态,ViewGroup容器,只有容器才有孩子结点
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = ((ViewGroup) view).getChildAt(i);
                setEnable(childView, b);
            }
        }
    }


    private void initFragmet() {
        fragments = new ArrayList<>();
        fragments.add(new ArticleFragment());
        fragments.add(new BFragment());
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        for (int i = 0; i < fragments.size(); i++) {
//            transaction.add(R.id.frameLayout, fragments.get(i), "tag" + i);
//        }
//        transaction.commit();
    }


}
