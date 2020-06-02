package com.example.lab4;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageLoader = (ImageView) findViewById(R.id.imageView);
        imageLoader.setBackgroundResource(R.drawable.animation);


        final AnimationDrawable animation = (AnimationDrawable) imageLoader.getBackground();
        animation.start();

        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.start();
            }

        });
        Button btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
              animation.stop();
            }
        });
    }
}
