package com.zhangyanye.annotationinject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.BindView;

import annotation.AnnotationProcessor;
import annotation.ContentView;
import annotation.InjectVIew;

@ContentView(id=R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @InjectVIew(id = R.id.tv_hello)
    @BindView(id=R.id.tv_hello)
    public TextView mTvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnnotationProcessor.register(this);
        mTvHello.setText("2333");
    }
}
