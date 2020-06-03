package com.example.lab8_2;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;
    MyRenderer myRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);

        myRenderer=new MyRenderer();
        glSurfaceView.setRenderer(myRenderer);

        setContentView(glSurfaceView);
    }
}
