package com.example.lab1;

import android.content.Context;
import android.graphics.*;
import android.view.View;

public class MView extends View {

    Paint p;
    RectF rectF;

    public MView(Context context) {
        super(context);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.GREEN);
        p.setStrokeWidth(10);
        p.setStyle(Paint.Style.FILL_AND_STROKE);

        rectF = new RectF(400,400,700,550);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
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
    }
}
