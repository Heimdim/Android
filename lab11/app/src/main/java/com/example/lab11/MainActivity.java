package com.example.lab11;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;
    MyRenderer myRenderer;

    private float mPreviousX;
    private float mPreviousY;
    private float minScale;
    private float maxScale;

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent e) {
            
            float x = e.getX();
            float y = e.getY();

            switch (e.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;

                    myRenderer.setYAngle(myRenderer.getYAngle() +dy/5);
                    myRenderer.setXAngle(myRenderer.getXAngle() + dx/5);
                    glSurfaceView.requestRender();
            }

            mPreviousX = x;
            mPreviousY = y;

            return true;
        }
    };

    private View.OnClickListener scaleUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            float scaleX = glSurfaceView.getScaleX();

            if(minScale == 0)
                minScale = scaleX;
            if(maxScale == 0)
                maxScale = scaleX*5f;

            if(scaleX*1.1f<maxScale) {
                glSurfaceView.setScaleX(glSurfaceView.getScaleX() * 1.1f);
                glSurfaceView.setScaleY(glSurfaceView.getScaleY() * 1.1f);
            }
        }
    };

    private View.OnClickListener scaleDownListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            float scaleX = glSurfaceView.getScaleX();

            if(minScale == 0)
                minScale = scaleX;
            if(maxScale == 0)
                maxScale = scaleX*5f;

            if(scaleX/1.1f>=minScale) {
                glSurfaceView.setScaleX(glSurfaceView.getScaleX() / 1.1f);
                glSurfaceView.setScaleY(glSurfaceView.getScaleY() / 1.1f);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);

        glSurfaceView.setOnTouchListener(onTouchListener);

        myRenderer=new MyRenderer(this);
        glSurfaceView.setRenderer(myRenderer);

        setContentView(glSurfaceView);

        LinearLayout rl = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        Button buttonScaleUp = new Button(this);
        buttonScaleUp.setText("+");
        Button buttonScaleDown = new Button(this);
        buttonScaleDown.setText("-");

        buttonScaleUp.setOnClickListener(scaleUpListener);
        buttonScaleDown.setOnClickListener(scaleDownListener);

        rl.addView(buttonScaleUp,lp);
        rl.addView(buttonScaleDown,lp);

        addContentView(rl, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //addContentView(buttonScaleDown, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
