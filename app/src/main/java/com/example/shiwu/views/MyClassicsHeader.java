package com.example.shiwu.views;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.example.shiwu.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;


public class MyClassicsHeader extends LinearLayout implements RefreshHeader {

    private static final String TAG = "MyClassicsHeader";
    private ImageView mProgressView;//刷新动画视图
    private AnimationDrawable mProgressDrawable;//刷新动画
    private LinearLayout llt_container;
    private TextView status;

    public MyClassicsHeader(Context context) {
        super(context);
        initView(context);
    }

    public MyClassicsHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public MyClassicsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    private void initView(Context context) {
//        Log.e(TAG, "initView: ");
        setGravity(Gravity.CENTER);
        mProgressDrawable = new AnimationDrawable();
        //循环播放
        mProgressDrawable.setOneShot(false);
        //准备好资源图片
        int[] ids = {R.mipmap.animation1, R.mipmap.animation2, R.mipmap.animation3, R.mipmap.animation4, R.mipmap.animation5, R.mipmap.animation6, R.mipmap.animation7, R.mipmap.animation8,
                R.mipmap.animation9, R.mipmap.animation10, R.mipmap.animation11, R.mipmap.animation12, R.mipmap.animation13, R.mipmap.animation14, R.mipmap.animation15, R.mipmap.animation16};
        //通过for循环添加每一帧动画
        for (int i = 0; i < ids.length; i++) {
            Drawable frame = getResources().getDrawable(ids[i]);
            //设定每一帧时长 100ms
            mProgressDrawable.addFrame(frame, 100);
        }
//        mProgressView = new ImageView(context);
//        mProgressView.setImageDrawable(mProgressDrawable);
//        addView(mProgressView, dp2px(80), dp2px(80));
//        setMinimumHeight(dp2px(60));
//        mProgressDrawable.stop();

        View view = LayoutInflater.from(context).inflate(R.layout.my_refresh_header, this, false);
        mProgressView = view.findViewById(R.id.iv_refresh_header);
        llt_container = view.findViewById(R.id.llt_container);
        status = view.findViewById(R.id.tv_refresh_status);

        mProgressView.setImageDrawable(mProgressDrawable);

        addView(view);
//        setMinimumHeight(dp2px(60));
        mProgressDrawable.stop();

        // 方法二  使用Glide播放gif图片
        // Glide.with(context).load(R.mipmap.gifphoto).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mProgressView);

    }

    /**
     * 2，获取真实视图（必须返回，不能为null）一般就是返回当前自定义的view
     */
    @NonNull
    public View getView() {
//        Log.e(TAG, "getView: ");
        return this;//真实的视图就是自己，不能返回null
    }

    /**
     * 3，获取变换方式（必须指定一个：平移、拉伸、固定、全屏）,Translate指平移，大多数都是平移
     */
    @Override
    public SpinnerStyle getSpinnerStyle() {
//        Log.e("-----TAG", "getSpinnerStyle: ");
        return SpinnerStyle.Translate;//指定为平移，不能null
    }


    /**
     * 6，结束下拉刷新的时候需要关闭动画
     */
    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
//        Log.e(TAG, "onFinish: " + success);
        mProgressDrawable.stop();//停止动画
        return 500;//延迟500毫秒之后再弹回
    }

    /**
     * 5，一般可以理解为一下case中的三种状态，在达到相应状态时候开始改变
     * 注意：这三种状态都是初始化的状态
     */
    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
//        Log.e("-----TAG", "onStateChanged: " + newState);

        switch (newState) {
            //1,下拉刷新的开始状态：下拉可以刷新
            case PullDownToRefresh:
                status.setText("下拉刷新");
                break;
            //2,下拉到最底部的状态：释放立即刷新
            case ReleaseToRefresh:
                status.setText("松开刷新");
                mProgressDrawable.start();
                break;
            //3,下拉到最底部后松手的状态：正在刷新
            case Refreshing:
                status.setText("正在刷新...");
                break;
        }
    }

    /**
     * 4，执行下拉的过程
     *
     * @param isDragging
     * @param percent       下拉百分比
     * @param offset
     * @param height        当前布局的高度
     * @param maxDragHeight
     */
    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
//        Log.e("-----TAG", "percent: " + percent+"----offset: "+offset+"----height: "+height+"----maxDragHeight: "+maxDragHeight);


        if (percent < 1) {
            mProgressView.setScaleX(percent);
            mProgressView.setScaleY(percent);
        }

        if (offset < height) {
            offset = offset / 2;
            mProgressView.setTranslationX(offset);
            llt_container.setTranslationX(1 - offset);
        }

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int maxDragHeight) {
    }


    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int maxDragHeight) {
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {
    }

    private int dp2px(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

}
