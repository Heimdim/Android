package com.example.lab3;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myedittextlibrary.MyEditText;


public class MainActivity extends AppCompatActivity {

    MyEditText tw;

    private View.OnTouchListener myListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            tw.setAngle((int)event.getX(),(int)event.getY());
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tw = (MyEditText) findViewById(R.id.myEditText);

        RelativeLayout rw = (RelativeLayout)findViewById(R.id.myLayout);
        rw.setOnTouchListener(myListener);
    }
}

