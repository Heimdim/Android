package com.example.lab8_2;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class MyFigure {
    //Geometry
    private FloatBuffer vertexBuffer;
    private FloatBuffer vertexBufferC;
    private ShortBuffer drawListBuffer;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
    private final int mProgram;

    static final int COORDS_PER_VERTEX = 3;
    static final int COLORS_PER_VERTEX = 4;

    static float cubeCoords[] = {
            -0.5f,  1.5f, 0.5f,   // top left
            -0.5f,  0.5f, 0.5f,   // bottom left
            0.5f,   0.5f, 0.5f,   // bottom right
            0.5f,   1.5f, 0.5f,  // top right
            -0.5f,  1.5f, -0.5f,   // top left back
            -0.5f,  0.5f, -0.5f,   // bottom left back
            0.5f,   0.5f, -0.5f,   // bottom right back
            0.5f,   1.5f, -0.5f  // top right back
    };

    private final short drawOrder[] = {
            0, 1, 2, 0, 2, 3,
            3, 2, 6, 3, 6, 7,
            0, 3, 7, 0, 7, 4,
            1, 5, 6, 1, 6, 2,
            4, 5, 1, 4, 1, 0,
            7, 6, 5, 7, 5, 4  };

    float color[] = { 1f, 1f, 1f, 1.0f,
            1f, 0f, 1f, 1.0f,
            1f, 0f, 0f, 1.0f,
            1f, 1f, 0f, 1.0f,
            0f, 1f, 1f, 1.0f,
            0f, 1f, 0f, 1.0f,
            0f, 0f, 0f, 1.0f,
            0f, 0f, 1f, 1.0f,};

    float pyramidCoords[] = {
             0f,  0f,  0f,
            -1f, -2f,  1f,
             1f, -2f,  1f,
             0f,  0f,  0f,
             1f, -2f,  1f,
             0f, -2f, -1f,
             0f,  0f,  0f,
            -1f, -2f,  1f,
             0f, -2f, -1f,
            -1f, -2f,  1f,
             0f, -2f, -1f,
             1f, -2f,  1f
    };

    short drawPyramid[] = {
            0, 1, 2,
            3, 4, 5,
            6, 7, 8,
            9, 10, 11
    };


    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    private final int vertexStrideC = COLORS_PER_VERTEX * 4; // 4 bytes per vertex
    //Rendering
    private final String vertexShaderCode =
                    "uniform mat4 uMVPMatrix;" +
                            "attribute vec4 vPosition;" +
                            "attribute vec4 aColor;" +
                            "varying vec4 vColor;" +
                            "void main() {" +
                            "  vColor=aColor;" +
                            "  gl_Position = uMVPMatrix * vPosition;" +
                            "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    short[] finalDrawOrder;
    public MyFigure(FIGURE_TYPE figure_type) {
        ByteBuffer bb;
        switch (figure_type)
        {
            case CUBE:
                bb = ByteBuffer.allocateDirect(cubeCoords.length * 4);
                bb.order(ByteOrder.nativeOrder());
                vertexBuffer = bb.asFloatBuffer();
                vertexBuffer.put(cubeCoords);
                vertexBuffer.position(0);

                bb = ByteBuffer.allocateDirect( color.length*4);
                bb.order(ByteOrder.nativeOrder());
                vertexBufferC = bb.asFloatBuffer();
                vertexBufferC.put(color);
                vertexBufferC.position(0);


                bb = ByteBuffer.allocateDirect(drawOrder.length * 2);
                bb.order(ByteOrder.nativeOrder());
                drawListBuffer = bb.asShortBuffer();
                drawListBuffer.put(drawOrder);
                drawListBuffer.position(0);

                finalDrawOrder = drawOrder;
                break;
            case PYRAMID:
                bb = ByteBuffer.allocateDirect(pyramidCoords.length * 4);
                bb.order(ByteOrder.nativeOrder());
                vertexBuffer = bb.asFloatBuffer();
                vertexBuffer.put(pyramidCoords);
                vertexBuffer.position(0);

                bb = ByteBuffer.allocateDirect( color.length*4);
                bb.order(ByteOrder.nativeOrder());
                vertexBufferC = bb.asFloatBuffer();
                vertexBufferC.put(color);
                vertexBufferC.position(0);


                bb = ByteBuffer.allocateDirect(drawPyramid.length * 2);
                bb.order(ByteOrder.nativeOrder());
                drawListBuffer = bb.asShortBuffer();
                drawListBuffer.put(drawPyramid);
                drawListBuffer.position(0);

                finalDrawOrder = drawPyramid;
                break;
        }

        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(float[] mvpMatrix){
        GLES20.glUseProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,GLES20.GL_FLOAT, false,vertexStride, vertexBuffer);

        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");

        GLES20.glEnableVertexAttribArray(mColorHandle);

        GLES20.glVertexAttribPointer(mColorHandle, COLORS_PER_VERTEX,GLES20.GL_FLOAT, false,vertexStrideC, vertexBufferC
        );

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, finalDrawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }


}


