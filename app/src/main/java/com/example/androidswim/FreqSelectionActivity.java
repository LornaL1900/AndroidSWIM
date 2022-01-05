package com.example.androidswim;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class FreqSelectionActivity extends AppCompatActivity {

    int mode;
    Intent new_intent;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freq_selection);
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        mode = intent.getIntExtra("mode", 0);
        if (mode < 3) {
            new_intent = new Intent(this, OneDotAnimateActivity.class);
        }
        else {
            new_intent = new Intent(this, TwoDotsAnimateActivity.class);
        }

        new_intent.putExtra("mode", mode);

    }

    public void button1(View view){
        new_intent.putExtra("freq", 1000);
        new_intent.putExtra("square", false);
        startActivity(new_intent);
    }

    public void button2(View view){
        new_intent.putExtra("freq", 3000);
        new_intent.putExtra("square", false);
        startActivity(new_intent);
    }
    public void button3(View view){
        new_intent.putExtra("freq", 6000);
        new_intent.putExtra("square", false);
        startActivity(new_intent);
    }
    public void button4(View view){
        new_intent.putExtra("freq", 8000);
        new_intent.putExtra("square", false);
        startActivity(new_intent);
    }
    public void button5(View view){
        new_intent.putExtra("freq", 12000);
        new_intent.putExtra("square", false);
        startActivity(new_intent);
    }
    public void button6(View view){
        new_intent.putExtra("freq", 16000);
        new_intent.putExtra("square", false);
        startActivity(new_intent);
    }
    public void button7(View view){
        new_intent.putExtra("freq", 1000);
        new_intent.putExtra("square", true);
        startActivity(new_intent);
    }
    public void button8(View view){
        new_intent.putExtra("freq", 2000);
        new_intent.putExtra("square", true);
        startActivity(new_intent);
    }
    public void button9(View view){
        new_intent.putExtra("freq", 4000);
        new_intent.putExtra("square", true);
        startActivity(new_intent);
    }
}
