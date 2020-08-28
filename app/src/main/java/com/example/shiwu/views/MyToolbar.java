package com.example.shiwu.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.shiwu.R;

public class MyToolbar extends Toolbar {

    private ImageView leftIcon;
    private ImageView rightIcon;

    public MyToolbar(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public MyToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MyToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        //使用LayoutInflater会保留xml里的布局宽高属性
        View view = LayoutInflater.from(context).inflate(R.layout.tool_bar_view, this, false);
        addView(view);
        //设置添加view的离父控件的间隔
        setContentInsetsAbsolute(0, 0);
        setContentInsetsRelative(0, 0);

        leftIcon = view.findViewById(R.id.left_icon);
        rightIcon = view.findViewById(R.id.right_icon);

    }

    public ImageView getLeftIcon() {
        if (leftIcon != null) {
            return leftIcon;
        } else {
            return null;
        }
    }

    public ImageView getReftIcon() {
        if (rightIcon != null) {
            return rightIcon;
        } else {
            return null;
        }
    }

}
