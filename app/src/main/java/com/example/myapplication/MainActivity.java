package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.canvas_expose.ExposeView;

public class MainActivity extends AppCompatActivity {
    ExposeView exposeView;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new ExposeView(this));
        setContentView(R.layout.activity_main);
        ViewGroup decorView= (ViewGroup) getWindow().getDecorView();
        exposeView=new ExposeView(this);
        decorView.addView(exposeView);
        view=findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exposeView.reStart();
            }
        });

//        PorterDuffXfermode xfermode=new PorterDuffXfermode(PorterDuff.Mode.SRC);

    }


}
