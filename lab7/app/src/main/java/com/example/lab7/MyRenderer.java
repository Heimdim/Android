package com.example.lab7;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.util.Random;

import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glViewport;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;


public class MyRenderer implements GLSurfaceView.Renderer {

    final Random random = new Random();
    private boolean shouldChangeColor;

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config)
    {
        changeColor(1,0,0);

    }

    public void changeColor(float red, float green, float blue)
    {
        glClearColor(red, green, blue, 0.0f);
    }

    @Override
    // Set the OpenGL viewport to fill the entire surface.
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    // Clear the rendering surface.
    public void onDrawFrame(GL10 glUnused) {
        glClear(GL_COLOR_BUFFER_BIT);
        if(shouldChangeColor)
        {
            changeColor(random.nextFloat(),random.nextFloat(),random.nextFloat());
            shouldChangeColor=false;
        }
    }

    public void setChangeColor(boolean b) {
        shouldChangeColor=b;
    }
}
