package com.example.androidswim;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class FreqSelectNew extends AppCompatActivity {

    int mode;
    Intent new_intent;
    int multiple;
    TextView tv_description;
    SeekBar mySeekbar;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freq_select_new);
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        multiple = 100;
        mode = intent.getIntExtra("mode", 0);
        if (mode < 3) {
            new_intent = new Intent(this, OneDotAnimateActivity.class);
        }
        else {
            new_intent = new Intent(this, TwoDotsAnimateActivity.class);
        }

        new_intent.putExtra("mode", mode);

        tv_description = (TextView) findViewById(R.id.tv_description);
        mySeekbar = (SeekBar) findViewById(R.id.seekBar);

        mySeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                multiple = progress;
                tv_description.setText(progress + "x period ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }




    public void button1(View view){
        new_intent.putExtra("freq", 1000);
        new_intent.putExtra("square", false);
        new_intent.putExtra("multiple", multiple);
        startActivity(new_intent);
    }

    public void button2(View view){
        new_intent.putExtra("freq", 3000);
        new_intent.putExtra("square", false);
        new_intent.putExtra("multiple", multiple);
        startActivity(new_intent);
    }
    public void button3(View view){
        new_intent.putExtra("freq", 6000);
        new_intent.putExtra("square", false);
        new_intent.putExtra("multiple", multiple);
        startActivity(new_intent);
    }
    public void button4(View view){
        new_intent.putExtra("freq", 8000);
        new_intent.putExtra("square", false);
        new_intent.putExtra("multiple", multiple);
        startActivity(new_intent);
    }
    public void button5(View view){
        new_intent.putExtra("freq", 12000);
        new_intent.putExtra("square", false);
        new_intent.putExtra("multiple", multiple);
        startActivity(new_intent);
    }
    public void button6(View view){
        new_intent.putExtra("freq", 16000);
        new_intent.putExtra("square", false);
        new_intent.putExtra("multiple", multiple);
        startActivity(new_intent);
    }
    public void button7(View view){
        new_intent.putExtra("freq", 1000);
        new_intent.putExtra("square", true);
        new_intent.putExtra("multiple", multiple);
        startActivity(new_intent);
    }
    public void button8(View view){
        new_intent.putExtra("freq", 2000);
        new_intent.putExtra("square", true);
        new_intent.putExtra("multiple", multiple);
        startActivity(new_intent);
    }
    public void button9(View view){
        new_intent.putExtra("freq", 4000);
        new_intent.putExtra("square", true);
        new_intent.putExtra("multiple", multiple);
        startActivity(new_intent);
    }
}
