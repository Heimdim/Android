package com.example.myedittextlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.appcompat.app.AppCompatActivity;

public class MyEditText extends androidx.appcompat.widget.AppCompatEditText {

    double angle=0;
    TypedArray ta;

    public MyEditText(Context context) {
        super(context);
        init(context, null);
    }


    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.MyEditText);
            this.setBackgroundColor(ta.getColor(R.styleable.MyEditText_bckgrColor, Color.WHITE));
            this.setOutlineAmbientShadowColor(ta.getColor(R.styleable.MyEditText_shadowColor, Color.WHITE));
            this.setTextSize(ta.getInt(R.styleable.MyEditText_textSize, 8));
            ((AppCompatActivity)context).setTitle(ta.getString(R.styleable.MyEditText_title));
        }
        catch (NullPointerException ex)
        {}
        finally {
            this.setText("Touch to rotate!");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.setRotation((float)angle);
        super.onDraw(canvas);
    }


    public void setAngle(int touchPointX, int touchPointY)
    {
        this.angle = Math.atan2 (getY()-touchPointY,getX()-touchPointX)/Math.PI *180;
        this.invalidate();
    }

}
