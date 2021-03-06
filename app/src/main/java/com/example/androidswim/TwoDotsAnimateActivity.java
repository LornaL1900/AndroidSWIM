package com.example.androidswim;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class TwoDotsAnimateActivity extends AppCompatActivity {
    MediaRecorder mediaRecorder;
    AudioRecord audioRecord;
    AudioTrack audioTrack;
    int sample_rate = 48000;
    int buffer_size;

    short[] samples;
    short[] samples_img;

    short[] rec_buffer;
    short[] rec_cp;
    short[] play_buffer;
    short[] play_cp;

    float avg_real, avg_img;
    float total_real, total_img;

    boolean square;
    int mode;
    int freq;
    int multiple;
    int sleep_time;
    float freqf;

    float period;
    float time_inc;
    float curr_time;

    View dot_g, dot_r;

    float xOffset, yOffset;

    Thread thread_play, thread_rec, thread_process;
    boolean interrupted;

    final CyclicBarrier gate = new CyclicBarrier(2);

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_dots_animate);

        // Make full screen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(uiOptions);

        dot_g = findViewById(R.id.dot_g);
        dot_r = findViewById(R.id.dot_r);

        dot_g.setX(0);
        dot_g.setY(0);

        dot_r.setX(0);
        dot_r.setY(0);
        ((CircleView) dot_r).setCircleColor(Color.RED);


        findViewById(R.id.constraint_layout).setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(TwoDotsAnimateActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Log.d("www", "onDoubleTap");
                    xOffset = avg_img;
                    yOffset = avg_real;
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("www", "Raw event: " + event.getAction() + ", (" + event.getRawX() + ", " + event.getRawY() + ")");
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        String tips = "1. Hold the phone to free space and double tap on the screen to calibrate.\n" +
                "2. Adjust amplitudes with the volume keys.\n" +
                "3. It's recommended to move the phone straight and slow.";

        Toast.makeText(getApplicationContext(),tips,Toast.LENGTH_SHORT).show();
        // get extras
        Intent intent = getIntent();
        mode = intent.getIntExtra("mode", 0);
        square = intent.getBooleanExtra("square", false);
        freq = intent.getIntExtra("freq", 1000);
        multiple = intent.getIntExtra("multiple", 100);

        freqf = (float) freq;
        period = 1/freqf;
        time_inc = 1/(float) sample_rate;
        curr_time = 0;

        square = intent.getBooleanExtra("square", false);
        freq = intent.getIntExtra("freq", 1000);
        multiple = intent.getIntExtra("multiple", 100);
        freqf = (float) freq;

        buffer_size = (int) (period * sample_rate * multiple);

        if(buffer_size > 9600) buffer_size = 9600;

        samples = new short[buffer_size];
        samples_img = new short[buffer_size];
        rec_buffer = new short[buffer_size];
        play_buffer = new short[buffer_size];
        play_cp = new short[buffer_size];

        Log.d("www", "mode" +mode + "square" +square + "freq" +freq) ;
        xOffset = 0;
        yOffset = 0;
        avg_real = 0;
        avg_img = 0;

//        dot_g = new CircleView(this);
//        linearLayout.addView(dot_g);
//        dot_g.setX(0);
//        dot_g.setY(0);
        mediaRecorder = new MediaRecorder();
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sample_rate,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,
                9600);

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sample_rate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, 9600, AudioTrack.MODE_STREAM);

//        if(mode==3) {
//            dot_r = new CircleView(this);
//            ((CircleView) dot_r).setCircleColor(Color.RED);
//            linearLayout.addView(dot_r);
//            dot_r.setX(0);
//            dot_r.setY(0);
//        }
//
//        else {dot_r = null;}

        SignalGenerator signalGenerator = new SignalGenerator(buffer_size, sample_rate);

        if(square) {
            samples = signalGenerator.generate(freq, true, true);
            samples_img = signalGenerator.generate(freq, false, true);
        }
        else {
            samples = signalGenerator.generate(freq, true, false);
            samples_img = signalGenerator.generate(freq, false, false);
        }

//        thread_play = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    gate.await();
//                } catch (BrokenBarrierException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//                while(!interrupted){
//                    audioTrack.write(samples, 0, buffer_size);
//                }
//            }
//        });
//
//        thread_rec = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    gate.await();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                while(!interrupted){
//                    audioRecord.read(rec_buffer, 0, buffer_size);
//                }
//            }
//        });

        thread_process = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {

                try {
                    gate.await();
                } catch (BrokenBarrierException | InterruptedException e) {
                    e.printStackTrace();
                }

                int i;
                for (i = 0; i < buffer_size; i++) {
                    rec_buffer[i] = 0;
                }

                while(!interrupted) {
                    total_real = 0;
                    total_img = 0;
                    for (i = 0; i < buffer_size; i++) {
                        play_buffer[i] = (short) (Math.cos(2 * Math.PI * freq * curr_time) * 10000);
                        total_real += (float) rec_buffer[i] *(float) play_buffer[i];
                        total_img += (Math.sin(2 * Math.PI * freq * curr_time) * 10000) * rec_buffer[i];
                        curr_time += time_inc;
                        if (curr_time > period) {curr_time -= period;}
                    }
                    play_cp = play_buffer.clone();
                    audioTrack.write(play_cp, 0, buffer_size, AudioTrack.WRITE_BLOCKING);
                    audioRecord.read(rec_buffer, 0, buffer_size, AudioRecord.READ_BLOCKING);
                    avg_real = total_real/buffer_size/12000;
                    avg_img = total_img/buffer_size/12000;

                    runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dot_g.setY(avg_img-xOffset);
                                dot_r.setY(avg_real-yOffset); }
                        });

//
//
//                    try {
//                        Thread.sleep(sleep_time);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                }
            }
        });

        startRecording();

//        thread_play.start();
//        thread_rec.start();
        thread_process.start();

        try {
            gate.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    void startRecording(){
        while(audioRecord.getState() == AudioRecord.STATE_UNINITIALIZED){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while(audioTrack.getPlayState() == AudioTrack.STATE_UNINITIALIZED){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        audioRecord.startRecording();
        audioTrack.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        interrupted = true;
    }

}