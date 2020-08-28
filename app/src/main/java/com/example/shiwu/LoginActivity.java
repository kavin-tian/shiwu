package com.example.shiwu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {

    private ImageView imageView;
    private int toXDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imageView = findViewById(R.id.image);

        decodeStream();

    }

    private void startAnimation(int fromXDelta, final int toXDelta) {
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, 0, 0);
//        使用java代码的方式创建TranslateAnimation，传入六个参数，fromXType、fromXValue、toXType、toXValue和fromYType、fromYValue、toYType、toYValue，使用如下构造方法。
//        参数说明：
//        fromXType：动画开始前的X坐标类型。取值范围为ABSOLUTE（绝对位置）、RELATIVE_TO_SELF（以自身宽或高为参考）、RELATIVE_TO_PARENT（以父控件宽或高为参考）。
//        fromXValue：动画开始前的X坐标值。当对应的Type为ABSOLUTE时，表示绝对位置；否则表示相对位置，1.0表示100%。
//        toXType：动画结束后的X坐标类型。
//        toXValue：动画结束后的X坐标值。
//        fromYType：动画开始前的Y坐标类型。
//        fromYValue：动画开始前的Y坐标值。
//        toYType：动画结束后的Y坐标类型。
//        toYValue：动画结束后的Y坐标值。
        translateAnimation.setDuration(50000);
        translateAnimation.setInterpolator(new LinearInterpolator());
//        translateAnimation.setFillEnabled(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (toXDelta < 0) {
                    startAnimation(toXDelta, 0);
                } else {
                    startAnimation(0, fromXDelta);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imageView.startAnimation(translateAnimation);
    }


    private void decodeStream() {
        //************************************************************************************
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        //创建获取图片祥细信息的对象
        BitmapFactory.Options options = new BitmapFactory.Options();
        //true表示不去加载整个大图片，而是获取大图片的祥细信息，例如：宽和高
        //false表示去加载整个大图片，需要消耗大量的手机内存空间
        options.inJustDecodeBounds = true;
        //获取大图片的宽和高，参数一：图片的路径，参数二：是否想要获取图片的祥细信息，如果带上这个参数，表示可以获取图片的祥细信息
        Bitmap bitmap = BitmapFactory.decodeStream(openInputStream(), null, options);
        //获取图片的宽和高
        float imageWidth = options.outWidth;
        float imageHeight = options.outHeight;
        //在控制台输出
        System.out.println("图片宽:" + imageWidth);
        System.out.println("图片高:" + imageHeight);
        //************************************************************************************
        //获取手机屏幕的宽和高
        float phoneWidth = windowManager.getDefaultDisplay().getWidth();
        float phoneHeight = windowManager.getDefaultDisplay().getHeight();
        //在控制台输出
        System.out.println("手机宽:" + phoneWidth);
        System.out.println("手机高:" + phoneHeight);
        //***********************************************************************************
        //options.inSampleSize = 1;
        options.inJustDecodeBounds = false;
        //返回的bitmap是非空的
        bitmap = BitmapFactory.decodeStream(openInputStream(), null, options);
        //将缩放后的bitmap设置到ImageView中
        imageView.setImageBitmap(bitmap);

        //把图片高度设置为手机高度, 图片宽度按 图片比例缩放
        float scale = imageWidth / imageHeight;
        float width = scale * phoneHeight;
        imageView.setLayoutParams(new FrameLayout.LayoutParams((int) width, (int) phoneHeight));

        toXDelta = (int) (width - phoneWidth);
        startAnimation(0, -toXDelta);
    }

    private InputStream openInputStream() {
        AssetManager assets = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assets.open("picture/img.jpg");
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
