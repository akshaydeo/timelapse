package com.akshaydeo.app;

import android.app.Activity;
import android.os.Bundle;

import com.akshaydeo.app.R;
import com.akshaydeo.timelapse.Measure;


@Measure
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AnotherSampleClass().timeConsumingMethod();
    }
}
