package com.example.lab5;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView image = (ImageView) findViewById(R.id.imageView);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.animation);
        image.startAnimation(hyperspaceJumpAnimation);
    }
}
