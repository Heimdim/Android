package by.bsu.andrew.canvas_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;


public class MView extends View {

    Paint p;
    RectF rectF;
    float[] lines;
    Path path;


    public MView(Context context) {
        super(context);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.RED);
        p.setStrokeWidth(10);
        p.setStyle(Paint.Style.FILL_AND_STROKE);

        rectF = new RectF(100,400,380,600);
//        lines = new float[]{70,420,240,300,240,300,410,420};
        path = new Path();
        path.reset();
        path.moveTo(70,420);
        path.lineTo(240,300);
        path.lineTo(410,420);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(210,255,210);
        canvas.drawRoundRect(rectF,20,20,p);

//        canvas.drawLines(lines,p);

        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.BLUE);
        canvas.drawPath(path,p);

        path.reset();

        path.addCircle(240,150,100, Path.Direction.CW);
        p.setTextSize(45);
        p.setStrokeWidth(15);
        canvas.drawTextOnPath("Android  graphics  by  Andrew",path,0,0,p);


        path.reset();
        p.setColor(Color.GREEN);
        path.moveTo(200,150);
        path.quadTo(240,220,280,150);
        canvas.drawPath(path,p);
    }
}
