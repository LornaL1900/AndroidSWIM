package com.example.androidswim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO = 13;
    ConstraintLayout note;
    LinearLayout noteParent;
    ConstraintLayout mannfun;
    WebView webView;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        note = findViewById(R.id.constraint_layout);
        mannfun = findViewById(R.id.mannfun_layout);
        noteParent = findViewById(R.id.secondParent);

        MannFun();

        decorView.setSystemUiVisibility(uiOptions);
        requestMicrophonePermission();

    }

    public void singleDotAnimation(View view) {
//        Intent intent = new Intent(this, FreqSelectionActivity.class);
        Intent intent = new Intent(this, CustomizeActivity.class);
        intent.putExtra("mode", 1);
        startActivity(intent);
    }

    public void argandAnimation(View view) {
//        Intent intent = new Intent(this, FreqSelectionActivity.class);
        Intent intent = new Intent(this, CustomizeActivity.class);
        intent.putExtra("mode", 2);
        startActivity(intent);
    }

    public void twoDotsAnimation(View view) {
//        Intent intent = new Intent(this, FreqSelectionActivity.class);
        Intent intent = new Intent(this, CustomizeActivity.class);
        intent.putExtra("mode", 3);
        startActivity(intent);
    }

    private void requestMicrophonePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{android.Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
        }
    }

    public void removeNote(View view) {
        noteParent.removeView(note);
    }

    public void removeMannfun(View view) {
        webView.loadUrl("");
        noteParent.removeView(mannfun);
    }

    public void openPaper(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://wearcam.org/PhenomenalAugmentedReality/"));
        startActivity(browserIntent);
    }

    public void openWearCam(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://wearcam.org/swim"));
        startActivity(browserIntent);
    }

    public void MannFun() {
        webView=findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/mannfun.htm");
    }
}
