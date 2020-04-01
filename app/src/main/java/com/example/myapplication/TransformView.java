package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Xfermode;
import android.view.View;

public class TransformView extends View {
    Paint mPaint;
    Bitmap mbitmap;

    public TransformView(Context context) {
        super(context);
        mPaint=new Paint();
        mbitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.cat);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        int state=canvas.save();//保存状态1
//        canvas.translate(50,50);
//        canvas.drawRect(0,0,400,400,mPaint);
//        canvas.save();//保存状态2
//        canvas.restore();//返回最新状态（状态2）

//        canvas.scale(0.5f,0.5f);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawRect(0,0,400,400,mPaint);
//        canvas.restoreToCount(state);//手动返回状态1
        int state=canvas.saveLayer(null,null,Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mbitmap,0,0,mPaint);
        mPaint.setXfermode(new Xfermode());
//        canvas.drawBitmap(rectBitmap,0,0,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(state);

        View view;
//        view.setLayerType(LAYER_TYPE_SOFTWARE);

    }
}
