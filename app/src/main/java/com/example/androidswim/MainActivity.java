package com.example.androidswim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Integer> wts;
    ArrayList<Integer> freqs;
    ArrayList<Double> phases;
    ArrayList<Double> amps;
    ArrayList<String> wavestrings;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        wts = new ArrayList<>();
        freqs = new ArrayList<>();
        phases = new ArrayList<>();
        amps = new ArrayList<>();
        wavestrings = new ArrayList<>();
//        recyclerView = findViewById(R.id.recyclerView);
////        myAdapter = new MyAdapter(this, wts, freqs, phases, amps);
//        myAdapter = new MyAdapter(this, wavestrings);
//        recyclerView.setAdapter(myAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void startAddingHarmonic(View view){
        Intent intent = new Intent(this, AddHarmonicActivity.class);
//        intent.putIntegerArrayListExtra("wts", wts);
//        intent.putIntegerArrayListExtra("freqs", freqs);
//        intent.putExtra("phases", phases);
//        intent.putExtra("amps", amps);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                // get String data from Intent
                int new_wt = intent.getIntExtra("new_wt", 0);
                int new_freq = intent.getIntExtra("new_freq", 0);
                double new_phase = intent.getDoubleExtra("new_phase", 0);
                double new_amp = intent.getDoubleExtra("new_amp", 0);
                String wavestring = intent.getStringExtra("new_ws");
                wavestrings.add(wavestring);
                wts.add(new_wt);
                freqs.add(new_freq);
                phases.add(new_phase);
                amps.add(new_amp);
                myAdapter.notifyItemInserted(wavestrings.size() - 1);
            }
        }
    }

    public void startAnimating(View view) {
        Intent intent = new Intent (this, SingleDotAnimateActivity.class);
        intent.putIntegerArrayListExtra("wts", wts);
        intent.putIntegerArrayListExtra("freqs", freqs);
        intent.putExtra("phases", phases);
        intent.putExtra("amps", amps);
        startActivity(intent);
    }

    public void animateOptions(View view) {
        Intent intent = new Intent  (this, AnimateOptionActivity.class);
//        Intent intent = new Intent  (this, SingleDotAnimateActivity.class);
        startActivity(intent);
    }
}
