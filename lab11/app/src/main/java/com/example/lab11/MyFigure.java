package com.example.lab11;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import javax.microedition.khronos.opengles.GL10;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.*;

public class MyFigure {
    //Geometry
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private int mPositionHandle;
    private int mMVPMatrixHandle;
    private int textureHandle;
    private final int mProgram;

    static final int COORDS_PER_VERTEX = 3;
    private int texture;


    static float cubeCoords[] = {
//            -0.5f,  0.5f, 0.5f,   // top left
//            -0.5f,  -0.5f, 0.5f,   // bottom left
//            0.5f,   -0.5f, 0.5f,   // bottom right
//            0.5f,   0.5f, 0.5f,  // top right
//            -0.5f,  0.5f, -0.5f,   // top left back
//            -0.5f,  -0.5f, -0.5f,   // bottom left back
//            0.5f,   -0.5f, -0.5f,   // bottom right back
//            0.5f,   0.5f, -0.5f  // top right back
            -1.0f,-1.0f,-1.0f, // Треугольник 1 : начало
            -1.0f,-1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f, // Треугольник 1 : конец
            1.0f, 1.0f,-1.0f, // Треугольник 2 : начало
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f,-1.0f, // Треугольник 2 : конец
            1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f,-1.0f,
            1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f,-1.0f, 1.0f,
            1.0f,-1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f,-1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f,-1.0f,
            -1.0f, 1.0f,-1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f,-1.0f, 1.0f
    };

    private final short drawOrder[] = {
            0,1,2,
            0,1,3,
            2,3,5,
            5,3,4,
            5,4, 7,
            5,7,6,
            6,5,2,
            2,1,6,
            1,6,7,
            7,1, 0,
            0,3,7,
            3,7,4
    };


    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    //Rendering
    private final String vertexShaderCode =
                    "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec2 aTexture;"+
                    "varying vec2 vTexture;"+
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  vTexture = aTexture;"+
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform sampler2D uTextureUnit;" +
                    "varying vec2 vTexture;"+
                    "void main() {" +
                    "  gl_FragColor = texture2D(uTextureUnit, vTexture);" +
                    "}";

    short[] finalDrawOrder;
    private int textureLocation;

    public MyFigure(Context context) {
        ByteBuffer bb = ByteBuffer.allocateDirect(cubeCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(cubeCoords);
        vertexBuffer.position(0);

        bb = ByteBuffer.allocateDirect(drawOrder.length * 4);
        bb.order(ByteOrder.nativeOrder());
        drawListBuffer = bb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        finalDrawOrder = drawOrder;

        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        texture  = loadTexture(context, R.drawable.cat);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(float[] mvpMatrix, GL10 gl10){
        GLES20.glUseProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,GLES20.GL_FLOAT, false,vertexStride, vertexBuffer);

        textureLocation = glGetAttribLocation(mProgram, "aTexture");
        textureHandle = glGetUniformLocation(mProgram, "uTextureUnit");
        vertexBuffer.position(0);
        glVertexAttribPointer(textureLocation, 2, GL_FLOAT,
                false, 20, vertexBuffer);
        glEnableVertexAttribArray(textureLocation);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture);

        glUniform1i(textureHandle, 0);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, finalDrawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
       // glDrawArrays(GL_TRIANGLES, 0, 12*3);

        GLES20.glDisableVertexAttribArray(mPositionHandle);

        gl10.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

    public static int loadTexture(Context context, int resourceId) {
        // создание объекта текстуры
        final int[] textureIds = new int[1];
        glGenTextures(1, textureIds, 0);
        if (textureIds[0] == 0) {
            return 0;
        }

        // получение Bitmap
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        final Bitmap bitmap = BitmapFactory.decodeResource(
                context.getResources(), resourceId, options);

        if (bitmap == null) {
            glDeleteTextures(1, textureIds, 0);
            return 0;
        }

        // настройка объекта текстуры
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureIds[0]);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        // сброс target
        glBindTexture(GL_TEXTURE_2D, 0);

        return textureIds[0];
    }

}


