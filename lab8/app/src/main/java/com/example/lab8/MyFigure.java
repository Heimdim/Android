package com.example.lab8;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class MyFigure {
    //Geometry
    private FloatBuffer vertexBuffer;
    private int positionCount;
    private int colorCount;

    private int coordinatesPerVertex;

    static float triangleCoords[] = {
            -0.5f,  0.8f,
            -1f, 0.2f,
            0f, 0.2f
    };

    static float rectangleCoords[] = {0.1f, 0.8f, 0.1f, 0.2f, 0.5f, 0.8f, 0.5f, 0.2f};

    static float hexagonCoords[] = {
            0.0f, -0.5f,
            -0.4f, -0.1f,
            0.4f, -0.1f,
            0.8f, -0.5f,
            0.4f, -0.9f,
            -0.4f, -0.9f,
            -0.8f, -0.5f,
            -0.4f, -0.1f,
    };

    //Rendering
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "attribute vec4 vColor;" +
                    "varying vec4 oColor;" +
                    "void main() {" +
                    "  oColor = vColor;" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 oColor;" +
                    "void main() {" +
                    "  gl_FragColor = oColor;" +
                    "}";
    private final int mProgram;


    private int vertexCount;
    private int glDrawMode;

    public MyFigure(FIGURE_TYPE figure_type) {
        ByteBuffer bb;
        switch (figure_type)
        {
            case TRIANGLE:
                vertexCount = 3;
                glDrawMode = GLES20.GL_TRIANGLES;
                bb= ByteBuffer.allocateDirect(triangleCoords.length*4);//4 bytes for 1 float
                bb.order(ByteOrder.nativeOrder());
                vertexBuffer = bb.asFloatBuffer();
                vertexBuffer.put(triangleCoords);
                break;
            case HEXAGON:
                vertexCount = 8;
                glDrawMode = GLES20.GL_TRIANGLE_FAN;
                bb= ByteBuffer.allocateDirect(hexagonCoords.length*4);//4 bytes for 1 float
                bb.order(ByteOrder.nativeOrder());
                vertexBuffer = bb.asFloatBuffer();
                vertexBuffer.put(hexagonCoords);
                break;
            case RECTANGLE:
                vertexCount = 4;
                glDrawMode = GLES20.GL_TRIANGLE_STRIP;
                bb= ByteBuffer.allocateDirect(rectangleCoords.length*4);//4 bytes for 1 float
                bb.order(ByteOrder.nativeOrder());
                vertexBuffer = bb.asFloatBuffer();
                vertexBuffer.put(rectangleCoords);
                break;
        }

        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(){

        GLES20.glUseProgram(mProgram);
        //Draw
        int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        int mColorHandle = GLES20.glGetAttribLocation(mProgram, "vColor");

        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, 2,
                GLES20.GL_FLOAT, false,0, vertexBuffer);


        vertexBuffer.position(positionCount);
        GLES20.glVertexAttribPointer(mColorHandle, 2,
                GLES20.GL_FLOAT, false,0, vertexBuffer);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(mColorHandle);

       // GLES20.glDrawElements(GLES20.GL_TRIANGLES, positionCount, GLES20.GL_FLOAT, vertexBuffer);
        GLES20.glDrawArrays(glDrawMode, 0, vertexCount);


        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);
    }


}


