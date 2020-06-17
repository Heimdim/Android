package com.example.lab13;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;
    private MyRenderer myRenderer;

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        float previousX,previousY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event != null) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    previousX = event.getX();
                    previousY = event.getY();
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    final float deltaX = event.getX() - previousX;
                    final float deltaY = event.getY() - previousY;

                    previousX = event.getX();
                    previousY = event.getY();

                    glSurfaceView.queueEvent(new Runnable() {
                        @Override
                        public void run() {
                            myRenderer.handleTouchDrag(deltaX, deltaY);
                        }
                    });
                }
                return true;
            } else {
                return false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glSurfaceView = new GLSurfaceView(this);

        setContentView(glSurfaceView);

        myRenderer = new MyRenderer(this);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(myRenderer);

        glSurfaceView.setOnTouchListener(onTouchListener);
    }
}
