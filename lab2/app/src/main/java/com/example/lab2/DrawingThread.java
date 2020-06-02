package com.example.lab2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.SurfaceHolder;


public class DrawingThread extends Thread {
    private SurfaceHolder surfaceHolder;


    public DrawingThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }


    @Override
    public void run() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if(canvas != null){

            RectF rectF = new RectF(400,400,700,550);
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            canvas.drawRGB(200,255,100);

            p.setStyle(Paint.Style.FILL);
            p.setColor(Color.MAGENTA);

            canvas.drawLine(550,100, 100,900,p);
            canvas.drawLine(100,900,1000,900,p);
            canvas.drawLine(1000,900,550,100,p);

            p.setColor(Color.GREEN);

            canvas.drawRoundRect(rectF,20,20,p);

            p.setColor(Color.BLUE);

            canvas.drawCircle(450,580,50,p);
            canvas.drawCircle(650,580,50,p);

            p.setTextSize(70);
            p.setStrokeWidth(15);
            canvas.drawText("Bus",500,750,p);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
