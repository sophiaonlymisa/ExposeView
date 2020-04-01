package com.example.myapplication.canvas_expose;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class ExposeView extends View {
    private Paint mPaint;//绘制球的画笔
    private Paint mHolePaint;//绘制空心圆画笔
    private int[] colorArray;
    private int mBackgroundColor= Color.WHITE;

    //表示斜对角线长度的一半
    private float mDistance;
    private float mCircleRadius =18;//6个小球的半径
    private float mRotateCircle =90;//旋转大圆的半径
    private float CenterX;//旋转圆中心x坐标
    private float CenterY;//旋转圆中心y坐标
    private float mCurrentRotateAngle;//当前大圆旋转角度
    private float mCurrentRotateRadius = mRotateCircle;//当前大圆的旋转半径
    private float mHoleCircleRadius=0f;//扩散圆半径

    private ValueAnimator valueAnimator;

    public ExposeView(Context context) {
        this(context,null);
    }

    public ExposeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ExposeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mHolePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mHolePaint.setColor(mBackgroundColor);
        mHolePaint.setStyle(Paint.Style.STROKE);
        colorArray=context.getResources().getIntArray(R.array.expose_circle_colors);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        CenterY=h/2;
        CenterX=w/2;
        mDistance= (float) Math.hypot(w,h)/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(expose==null)expose=new Rotate();
        expose.drawChange(canvas);

    }
    private Expose expose;
    private abstract class Expose {
        abstract void drawChange(Canvas canvas);
    }

    public class Rotate extends Expose{
        public Rotate(){
            valueAnimator=ValueAnimator.ofFloat(0, (float) (2*Math.PI));
            valueAnimator.setDuration(1000);
            valueAnimator.setRepeatCount(2);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotateAngle= (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    expose=new Mergin();
                }
            });
            valueAnimator.start();
        }

        @Override
        void drawChange(Canvas canvas) {
            drawBackground(canvas);
            drawCircle(canvas);
        }
    }

    public class Mergin extends Expose{
        public Mergin() {
            valueAnimator=ValueAnimator.ofFloat(mCircleRadius,mRotateCircle);
            valueAnimator.setDuration(1200);
            valueAnimator.setInterpolator(new OvershootInterpolator(10f));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotateRadius= (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    expose=new Expose3();
                }
            });
            valueAnimator.reverse();
        }

        @Override
        void drawChange(Canvas canvas) {
            drawBackground(canvas);
            drawCircle(canvas);
        }
    }

    public class Expose3 extends Expose{
        public Expose3(){
            valueAnimator=ValueAnimator.ofFloat(mCircleRadius,mDistance);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setDuration(1200);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mHoleCircleRadius= (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.start();

        }

        @Override
        void drawChange(Canvas canvas) {
            drawBackground(canvas);

        }
    }



    private void drawCircle(Canvas canvas) {
        float rotateAngle= (float) (2*Math.PI/colorArray.length);
        for(int i=0;i<colorArray.length;i++){
            float angle=i*rotateAngle+mCurrentRotateAngle;
            float cx= (float) (CenterX+Math.cos(angle)* mCurrentRotateRadius);
            float cy= (float) (CenterY+Math.sin(angle)* mCurrentRotateRadius);
            mPaint.setColor(colorArray[i]);
            canvas.drawCircle(cx,cy, mCircleRadius,mPaint);
        }
    }

    private void drawBackground(Canvas canvas){
        if(mHoleCircleRadius>0){
            float strokeWidth=mDistance-mHoleCircleRadius;
            mHolePaint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(CenterX,CenterY,mHoleCircleRadius+strokeWidth/2,mHolePaint);

        }else canvas.drawColor(mBackgroundColor);

    }

    public void reStart(){
        if(valueAnimator.isRunning())return;
        mHoleCircleRadius=0f;
        mCurrentRotateRadius=mRotateCircle;
        mCurrentRotateAngle=0f;
        expose=new Rotate();
        invalidate();
    }
}
