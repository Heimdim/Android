package com.example.lab11;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MyRenderer implements GLSurfaceView.Renderer {

    private MyFigure figure1;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix_x = new float[16];
    private final float[] mRotationMatrix_y = new float[16];
    private final float[] mRotationMatrix = new float[16];

    private float mXAngle = 25;
    private float mYAngle = 25;

    private int zoom = 10;

    public float getXAngle() {
        return mXAngle;
    }
    public float getYAngle() {
        return mYAngle;
    }
    public void setXAngle(float angle) {
        mXAngle = angle;
    }
    public void setYAngle(float angle) {
        mYAngle = angle;
    }

    private Context context;

    public MyRenderer(Context context) {
        this.context = context;
    }

    public static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(1f, 1f, 1f, 0.0f);
        GLES20.glEnable(GL10.GL_DEPTH_TEST);
        figure1 = new MyFigure(context);
    }

    public void setZoom(int zoom)
    {
        this.zoom = zoom;
    }

    public int getZoom()
    {
        return zoom;
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int w, int h)
    {
        GLES20.glViewport(0, 0, w, h);
        float ratio = (float) w / h;

        Matrix.frustumM(mProjectionMatrix, 0, -ratio/zoom, ratio/zoom, -1f/zoom, 1f/zoom, 1, 50);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        float[] scratch = new float[16];

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 25, 0f, 0f, 0f, 0f, 1f, 0.0f);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        Matrix.setRotateM(mRotationMatrix_x, 0, mYAngle, 1.0f, 0, 0);
        Matrix.setRotateM(mRotationMatrix_y, 0, mXAngle, 0, 1.0f,  0);
        Matrix.multiplyMM(mRotationMatrix, 0, mRotationMatrix_y, 0, mRotationMatrix_x, 0);

        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        figure1.draw(scratch, gl10);

    }


}
