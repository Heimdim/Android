package by.bsu.andrew.canvas_view;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MView mw = new MView(this);

        setContentView(mw);
    }
}
