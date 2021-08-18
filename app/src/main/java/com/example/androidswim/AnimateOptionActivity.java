package com.example.androidswim;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class AnimateOptionActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_option);
        requestMicrophonePermission();
    }

    public void singleDotAnimation(View view) {
        Intent intent = new Intent(this, SingleDotAnimateActivity.class);
        startActivity(intent);
    }

    public void argandAnimation(View view) {
        Intent intent = new Intent (this, ArgandAnimateActivity.class);
        startActivity(intent);
    }

    public void twoDotsAnimation(View view) {
        Intent intent = new Intent (this, TwoDotsAnimateActivity.class);
        startActivity(intent);
    }

    private void requestMicrophonePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{android.Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
        }
    }

}
