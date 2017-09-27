package com.lyj.panel;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yu on 2017/9/25.
 *
 * 使用
 *
 * 设置最大值
 * mPanelView.setMaxValue(5000);
 * 设置当前值
 * mPanelView.setCurrentValue(3000);
 * 设置阶梯颜色
 * mPanelView.setStepColorList(new String[]{"#ffffff","#000000"});
 * 设置刻度背景画笔颜色
 * mPanelView.setScaleBgColor("#00ff00");
 * 设置刻度颜色  若设置此项，阶梯颜色不生效
 * mPanelView.setScaleColor("#ff0000");
 */

public class PanelView extends View {

    private Paint mScaleBgPaint;
    private Bitmap mScaleBgBitmapBuf;//刻度背景的bitmap缓存

    private Paint mScalePaint;
    private Bitmap mScaleBitmapBuf;//刻度的bitmap缓存
    private Canvas mScaleCanvas;

    private int mMaxValue;
    private int mCurrentValue;
    private float mNowValue;

    private String mScaleBgColor = "#CACFD5";
    private String mScaleColor;

    //跑完所需要的时间
    private int mDuration = 3000;


    //阶梯颜色
    private String[] mStepColors = new String[]{"#D02E30","#EB6D35","#F9BA51","#06D1F5","#1081E6","#1D39D7","#2C0DC5"};

    public PanelView(Context context) {
        this(context,null);
    }

    public PanelView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //Log.d("LYJTEST","PanelView");
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //初始化参数
        initParams();
        //绘制刻度背景
        drawScaleBg();

        startAnim();
        //Log.d("LYJTEST","onSizeChanged");
    }

    private void initParams() {
        mScaleBgPaint = new Paint();
        mScaleBgPaint.setAntiAlias(true);
        mScaleBgPaint.setColor(Color.parseColor(mScaleBgColor));

        mScalePaint = new Paint();
        mScalePaint.setAntiAlias(true);
        if(!TextUtils.isEmpty(mScaleColor)){
            mScalePaint.setColor(Color.parseColor(mScaleColor));
        }

    }

    /**
     * 绘制刻度背景
     */
    private void drawScaleBg() {
        mScaleBgBitmapBuf = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        Canvas scaleBgCanvas = new Canvas(mScaleBgBitmapBuf);
        scaleBgCanvas.rotate(-30,getWidth()/2,getHeight()/2);
        for(int i=0;i<=120;i++){
            if(i%10 == 0){
                scaleBgCanvas.drawRect(0,getHeight()/2-4,30,getHeight()/2+4, mScaleBgPaint);
            }
            else if(i%5 == 0){
                scaleBgCanvas.drawRect(5,getHeight()/2-3,25,getHeight()/2+3, mScaleBgPaint);
            }else {
                scaleBgCanvas.drawRect(8,getHeight()/2-2,22,getHeight()/2+2, mScaleBgPaint);
            }
            scaleBgCanvas.rotate(2,getWidth()/2,getHeight()/2);
        }
    }
    int width;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Log.d("LYJTEST","onMeasure");

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST){
            throw new RuntimeException(
                    "width or height can not use wrap_content");
        }

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heigthSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = Math.min(widthSize,heigthSize);
        setMeasuredDimension(width,width);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right+width, bottom+width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.d("LYJTEST","onDraw");

        if(mScaleBgBitmapBuf == null || mScaleBgBitmapBuf.isRecycled()){
            drawScaleBg();
        }
        canvas.drawBitmap(mScaleBgBitmapBuf,0,0, mScaleBgPaint);

        if(mMaxValue == 0){
            return;
        }
        drawScale(canvas);
    }

    /**
     * 绘制刻度
     * @param canvas
     */
    private void drawScale(Canvas canvas) {
        mScaleBitmapBuf = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        mScaleCanvas = new Canvas(mScaleBitmapBuf);
        mScaleCanvas.rotate(-30,getWidth()/2,getHeight()/2);

        List<String > resultColorList = ColorUtil.getColorList(mStepColors,120);
        for(int i=0;i<Math.round(120* (mNowValue/mMaxValue));i++){
            if(TextUtils.isEmpty(mScaleColor)){
                //若设置了刻度画笔颜色 则不使用渐变色
                mScalePaint.setColor(Color.parseColor(resultColorList.get(i)));
            }
            if(i%10 == 0){
                mScaleCanvas.drawRect(0,getHeight()/2-4,30,getHeight()/2+4, mScalePaint);
            } else if(i%5 == 0){
                mScaleCanvas.drawRect(5,getHeight()/2-3,25,getHeight()/2+3, mScalePaint);
            }else {
                mScaleCanvas.drawRect(8,getHeight()/2-2,22,getHeight()/2+2, mScalePaint);
            }
            mScaleCanvas.rotate(2,getWidth()/2,getHeight()/2);
        }

        canvas.drawBitmap(mScaleBitmapBuf,0,0,null);
    }

    public void setMaxValue(int maxValue) {
        this.mMaxValue = maxValue;
    }

    public void setCurrentValue(int currentValue) {
        //Log.d("LYJTEST","setCurrentValue");
        this.mCurrentValue = currentValue;
    }

    public void startAnim(){
        if(mCurrentValue == 0){
            return;
        }
        mDuration = (int) (mDuration*((float)mCurrentValue/mMaxValue));
        ValueAnimator objectAnimator =  ObjectAnimator.ofFloat(0,mCurrentValue);
        objectAnimator.setInterpolator(new DecelerateInterpolator());//插值器
        objectAnimator.setDuration(mDuration);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mNowValue = (float) animation.getAnimatedValue();
                invalidate();
                //Log.d("LYJTEST","mNowValue == " + mNowValue);

            }
        });
        objectAnimator.start();
    }

    public void setStepColorList(String[] stepColors) {
        if(stepColors.length<2){
            throw new RuntimeException(
                    "stepcolors.length must >= 2");
        }
        //Log.d("LYJTEST","setStepColorList");
        this.mStepColors = stepColors;
    }

    //设置刻度背景 画笔颜色
    public void setScaleBgColor(String scaleBgColor) {
        this.mScaleBgColor = scaleBgColor;
    }

    //设置刻度 画笔的颜色
    public void setScaleColor(String scaleColor) {
        this.mScaleColor = scaleColor;
    }

    public void setMaxDuration(int maxDuration) {
        this.mDuration = maxDuration;
    }

    public void start(){
        startAnim();
    }




    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mScaleBgBitmapBuf != null && !mScaleBgBitmapBuf.isRecycled()){
            mScaleBgBitmapBuf.recycle();
            mScaleBgBitmapBuf = null;
        }

        if(mScaleBitmapBuf != null && !mScaleBitmapBuf.isRecycled()){
            mScaleBitmapBuf.recycle();
            mScaleBitmapBuf = null;
        }
    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        //Log.d("LYJTEST","onLayout");
//    }
}
