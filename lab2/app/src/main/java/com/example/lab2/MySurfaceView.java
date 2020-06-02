package com.example.lab2;
import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    DrawingThread drawingThread;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        drawingThread = new DrawingThread(surfaceHolder);
        drawingThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean tryAgain = true;
        while (tryAgain) {
            try {
                drawingThread.join();
                tryAgain=false;
            } catch (InterruptedException e) {
            }
        }
    }
}
