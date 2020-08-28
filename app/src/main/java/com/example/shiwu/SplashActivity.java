package com.example.shiwu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.shiwu.utils.StatusBarUtil;

public class SplashActivity extends AppCompatActivity {

    private AnimationDrawable animationDrawable;
    private ImageView bgImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置状态栏
        StatusBarUtil.setTranslucentStatus(this);
        bgImage = findViewById(R.id.bg_image);
        initView();
    }

    private void initView() {
        animationDrawable = new AnimationDrawable();
        //循环播放
        animationDrawable.setOneShot(false);
        //准备好资源图片
        int[] ids = {R.mipmap.bg_splash_01, R.mipmap.bg_splash_02, R.mipmap.bg_splash_03, R.mipmap.bg_splash_04,
                R.mipmap.bg_splash_05, R.mipmap.bg_splash_05, R.mipmap.bg_splash_06, R.mipmap.bg_splash_07,
                R.mipmap.bg_splash_08, R.mipmap.bg_splash_09};
        //通过for循环添加每一帧动画
        for (int i = 0; i < ids.length; i++) {
            Drawable frame = getResources().getDrawable(ids[i]);
            //设定每一帧时长 100ms
            animationDrawable.addFrame(frame, 100);
        }
//
        bgImage.setImageDrawable(animationDrawable);
        animationDrawable.start();

        // 方法二  使用Glide播放gif图片
        // Glide.with(context).load(R.mipmap.gifphoto).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mProgressView);

    }

    public void next(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
