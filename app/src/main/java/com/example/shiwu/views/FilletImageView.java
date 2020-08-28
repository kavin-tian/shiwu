package com.example.shiwu.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import com.example.shiwu.R;

/**
 * 可控制部分显示圆角
 */
public class FilletImageView extends androidx.appcompat.widget.AppCompatImageView {
    //最后确认的宽高
    private RectF drawRectF;
    private Paint mPaint;
    private Matrix matrix;
    private BitmapShader bitmapShader;
    private boolean topLeftRightCorner, bottomLeftRightCorner;//定义上面与下面的是否为圆角
    private int radius = 5;

    public FilletImageView(Context context) {
        this(context, null);
    }

    public FilletImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilletImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FilletImageView);
        topLeftRightCorner = typedArray.getBoolean(R.styleable.FilletImageView_topLeftRightCorner, false);
        bottomLeftRightCorner = typedArray.getBoolean(R.styleable.FilletImageView_bottomLeftRightCorner, false);
        radius = typedArray.getDimensionPixelSize(R.styleable.FilletImageView_filletImageRadius, 5);
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        matrix = new Matrix();
    }

    public int getRadius() {
        return radius;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawRectF = new RectF(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //创建一个和原图大小的图片
        if (this.getDrawable() == null) {
            return;
        }
        Bitmap sourceBitMap = ((BitmapDrawable) this.getDrawable()).getBitmap();
        if (sourceBitMap == null) {
            return;
        }
        //创建一个和原图大小的图片
        sourceBitMap = ((BitmapDrawable) this.getDrawable()).getBitmap();

        //BitmapShader 为着色器
        bitmapShader = new BitmapShader(sourceBitMap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // float scaleMax = 1.0f,scaleX=1.0f,scaleY=1.0f;
        float scaleMax = 1.0f;
        // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
        if (getWidth() != sourceBitMap.getWidth() || getHeight() != sourceBitMap.getHeight()) {
            float scaleX = (float) getWidth() / (float) sourceBitMap.getWidth();
            float scaleY = (float) getHeight() / (float) sourceBitMap.getHeight();
            scaleMax = Math.max(scaleX, scaleY);
            //图片填充imageview相当于ScaleType.FIT_XY
            matrix.setScale(scaleX, scaleY);
        }

        //对图片等比缩放
        if (getScaleType() == ScaleType.MATRIX) {
            //对我们创建出来的bitmap进行缩放
            matrix.setScale(scaleMax, scaleMax);
        }

        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);
        //纠正圆角
        //int radius = (int) (getRadius() * scaleMax);
        //画出我们需要的直角图形
        canvas.drawRoundRect(drawRectF, radius, radius, mPaint);
        if (topLeftRightCorner) { //是否遮住底部的圆角
            //左边底部
            canvas.drawRect(0, canvas.getHeight() - radius, radius, canvas.getHeight(), mPaint);
            //右边底部
            canvas.drawRect(canvas.getWidth() - radius, canvas.getHeight() - radius, canvas.getWidth(), canvas.getHeight(), mPaint);
        }

        if (bottomLeftRightCorner) { //是否遮住上部的圆角
            //左边顶部
            canvas.drawRect(0, 0, radius, radius, mPaint);
            //右边顶部
            canvas.drawRect(canvas.getWidth() - radius, 0, canvas.getWidth(), radius, mPaint);
        }
    }

}