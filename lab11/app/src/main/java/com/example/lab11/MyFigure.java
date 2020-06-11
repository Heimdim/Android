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
    private FloatBuffer texBuffer;
    private int mPositionHandle;
    private int mMVPMatrixHandle;
    private int textureHandle;
    private final int mProgram;
    private int uTextureUnitLocation;

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

//            -1f,-1f,-1f,
//            -1f,-1f,1f,
//            1f, -1f,1f,
//            1f,-1f,-1f,
//            1f, 1f, -1f,
//            1f, 1f, 1f,
//            -1f,1f,1f,
//            -1f,1f,-1f


            -1,  1, 1,   0, 0,
            -1, -1, 1,   0, 1,
            1,  1, 1,   1, 0,
            1, -1, 1,   1, 1,

             1,1,-1,  0, 0,
            -1,1,-1,   0, 1,
            1,  1, 1,   1, 0,
            -1,  1, 1,  1, 1,

            1,-1,-1,  0, 0,
            1,1,-1,   0, 1,
            1, -1, 1,   1, 0,
            1, 1, 1,  1, 1,

            -1,-1,-1,  0, 0,
            1,-1,-1,   0, 1,
            -1, -1, 1,   1, 0,
            1, -1, 1,  1, 1,

            -1,-1,-1,  0, 0,
            -1,1,-1,   0, 1,
            -1, -1, 1,   1, 0,
            -1, 1, 1,  1, 1,

            -1,1,-1,  0, 0,
            1,1,-1,   0, 1,
            -1, -1, -1,   1, 0,
            1, -1, -1,  1, 1
    };


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

    public MyFigure(Context context) {
        ByteBuffer bb = ByteBuffer.allocateDirect(cubeCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(cubeCoords);
        vertexBuffer.position(0);

        texture  = loadTexture(context, R.drawable.cat);

        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(float[] mvpMatrix, GL10 gl10){
        GLES20.glUseProgram(mProgram);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        vertexBuffer.position(0);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,GLES20.GL_FLOAT, false,(COORDS_PER_VERTEX+2)*4, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        vertexBuffer.position(3);
        textureHandle = glGetAttribLocation(mProgram, "aTexture");
        uTextureUnitLocation = glGetAttribLocation(mProgram, "uTextureUnit");
        glVertexAttribPointer(textureHandle, 2, GL_FLOAT,
                false, (COORDS_PER_VERTEX+2)*4, vertexBuffer);
        glEnableVertexAttribArray(textureHandle);

        // помещаем текстуру в target 2D юнита 0
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture);

        // юнит текстуры
        glUniform1i(uTextureUnitLocation, 0);

      //  GLES20.glDrawElements(GLES20.GL_TRIANGLES, finalDrawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
        GLES20.glDrawArrays(GL_TRIANGLE_STRIP,0,24);

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


