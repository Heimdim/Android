package com.example.lab7;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;
    MyRenderer myRenderer;


    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(myRenderer!=null) {
                myRenderer.setChangeColor(true);
                glSurfaceView.invalidate();

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);


        ActivityManager activityManager =
                (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configInfo = activityManager.getDeviceConfigurationInfo();
        setTitle("OpenGL ES version - "+ configInfo.getGlEsVersion());

        myRenderer=new MyRenderer();
        glSurfaceView.setRenderer(myRenderer);
        glSurfaceView.setOnClickListener(myListener);

        setContentView(glSurfaceView);
    }
}
