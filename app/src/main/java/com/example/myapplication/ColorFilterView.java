package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ColorFilterView extends View{
    private Paint paint;
    private Bitmap mBitmap;
    private Path mPath;

    public ColorFilterView(Context context) {
        super(context);
        paint=new Paint();
        mBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.cat);
        mPath=new Path();
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LightingColorFilter lightingColorFilter=new LightingColorFilter(0xffff00,0x000000);
//        paint.setColorFilter(lightingColorFilter);
//        canvas.drawBitmap(mBitmap,0,0,paint);

//        PorterDuffColorFilter porterDuffColorFilter=
//                new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN);
//        paint.setColorFilter(porterDuffColorFilter);
//        canvas.drawBitmap(mBitmap,0,0,paint);

//        float[] matrix={
//                -1,0,0,0,255,//RED
//                0,-1,0,0,255,//GREEN
//                0,0,-1,0,255,//BLUE
//                0,0,0,1,0//ALPHA
//        };

//        ColorMatrix cm=new ColorMatrix();
//        cm.setRotate(2,45);
//        ColorMatrixColorFilter colorMatrixColorFilter=new ColorMatrixColorFilter(cm);
//        paint.setColorFilter(colorMatrixColorFilter);
//        canvas.drawBitmap(mBitmap,0,0,paint);

        CornerPathEffect cpe=new CornerPathEffect(10);
        DiscretePathEffect dpe=new DiscretePathEffect(3.0f,5.0f);
//        DashPathEffect dashPathEffect=new DashPathEffect();
        paint.setPathEffect(dpe);
        canvas.drawPath(mPath,paint);
//        canvas.translate();
//        canvas.setMatrix();
//        canvas.drawText();
//        canvas.drawLine();




    }
}
