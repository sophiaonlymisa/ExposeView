package com.example.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.example.myapplication.object.Ball;

import java.util.ArrayList;
import java.util.Random;

public class BallRollDownView extends View {
    Bitmap mBitmap;
    Paint mPaint;
    int numb=30;
    private float d = numb;//粒子直径
    ArrayList<Ball> balls;
    ValueAnimator animation;


    public BallRollDownView(Context context) {
        super(context);
        mBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.cat);
        mPaint=new Paint();
        balls=new ArrayList();

        initBall();
    }

    private void initBall() {
        balls.clear();
        for(int i=0;i<mBitmap.getWidth();i+=numb){
            for(int j=0;j<mBitmap.getHeight();j+=numb){
                Ball ball=new Ball();
                ball.color=mBitmap.getPixel(i,j);
                ball.r=d/2;
                ball.x=i+ball.r;
                ball.y=j+ball.r;
                //水平初速度（-20，20）
                ball.vx= (float) ((Math.random()-0.5)*40);
                //水平初速度（-30，30）
                ball.vy=(float) ((Math.random()-0.5)*60);
                ball.ay=9.8f;
                ball.ax=0;

                balls.add(ball);
            }
        }
        animation=ValueAnimator.ofFloat(0,1);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBalls();
                invalidate();
            }
        });
    }

    private void updateBalls() {
        for(Ball mBall:balls){
            mBall.x += mBall.vx;
            mBall.y += mBall.vy;
            mBall.vx += mBall.ax;
            mBall.vy +=mBall.ay;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.scale(0.5f,0.5f);
        canvas.translate(300f,300f);
        if(!animation.isStarted()){
          canvas.drawBitmap(mBitmap,0,0,mPaint);
        }else {
            for(Ball ball:balls){
                mPaint.setColor(ball.color);
                canvas.drawCircle(ball.x,ball.y,ball.r,mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            initBall();
            animation.end();
            animation.start();
        }
        return super.onTouchEvent(event);
    }
}
