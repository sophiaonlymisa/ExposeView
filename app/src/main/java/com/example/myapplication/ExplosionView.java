package com.example.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.myapplication.object.Ball;

import java.util.ArrayList;
import java.util.List;

public class ExplosionView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;
    private float d = 16;//粒子直径
    private List<Ball> mBalls;
    private ValueAnimator animator;

    public ExplosionView(Context context) {
        this(context, null);
    }

    public ExplosionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExplosionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //数据初始化
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.demo);
        mBalls = new ArrayList<>();
        initBalls();
    }

    private void initBalls() {
        for (int i = 0; i < mBitmap.getWidth(); i += 16) {
            for (int j = 0; j < mBitmap.getHeight(); j += 16) {
                Ball ball = new Ball();
                ball.color = mBitmap.getPixel(i, j);
                ball.r = d / 2;
                ball.x = i + ball.r;
                ball.y = j + ball.r;
                //水平初速度（-20，20）
                ball.vx = (float) ((Math.random() - 0.5) * 40);
                //垂直初速度（-30，30）
                ball.vy = (float) ((Math.random() - 0.5) * 60);
                ball.ax = 0;
                ball.ay = 9.8f;
                mBalls.add(ball);
            }
        }
        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBalls();
                invalidate();
            }
        });
    }

    private void updateBalls() {
        //更新粒子位置
        for (Ball mBall : mBalls) {
            mBall.x += mBall.vx;
            mBall.y += mBall.vy;
            mBall.vx += mBall.ax;
            mBall.vy += mBall.ay;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(400, 400);
        if (!animator.isStarted()) {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        } else
            for (Ball mBall : mBalls) {
                mPaint.setColor(mBall.color);
                canvas.drawCircle(mBall.x, mBall.y, mBall.r, mPaint);
            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initBalls();
            System.out.println("BallNum:"+mBalls.size());
            animator.end();
            animator.start();
        }
        return super.onTouchEvent(event);
    }
}
