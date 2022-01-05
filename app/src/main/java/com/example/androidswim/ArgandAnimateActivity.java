package com.example.androidswim;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ArgandAnimateActivity extends AppCompatActivity {

    MediaRecorder mediaRecorder;
    AudioRecord audioRecord1;
    AudioRecord audioRecord2;
    AudioTrack audioTrack;
    short[] buffer1;
    short[] buffer2;

    float[] medians;
    int runs;

    int reqFreq = 48000;

    ArrayList<Integer> wts;
    ArrayList<Integer> freqs;
    ArrayList<Double> phases;
    ArrayList<Double> amps;

    int recBufferSize;
    int writeBufferSize;

    Height height;
    Width width;

    View dot;

    Thread thread1;
    Thread thread2;
    Thread thread3;

    int offset;
    float[] cos;
    float[] sin;
    short[] samples1;
    short[] samples2;
    private Boolean interrupt = false;
    int count=0;
    float avg3, avg4;
    float xOffset, yOffset;

    final CyclicBarrier gate = new CyclicBarrier(4);

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_dot_animate);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();

        findViewById(R.id.main).setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(ArgandAnimateActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Log.d("www", "onDoubleTap");
                    xOffset = avg3;
                    yOffset = avg4;
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

        wts = (ArrayList<Integer>) intent.getSerializableExtra("wts");
        freqs =  (ArrayList<Integer>) intent.getSerializableExtra("freqs");
        phases =  (ArrayList<Double>) intent.getSerializableExtra("phases");
        amps = (ArrayList<Double>) intent.getSerializableExtra("amps");


        mediaRecorder = new MediaRecorder();
        recBufferSize = AudioRecord.getMinBufferSize(reqFreq, MediaRecorder.AudioSource.MIC,
                AudioFormat.ENCODING_PCM_16BIT);

        recBufferSize = 9600;
        writeBufferSize = 9600;
        xOffset = 0;
        yOffset = 0;

        audioRecord1 = new AudioRecord(MediaRecorder.AudioSource.MIC, reqFreq,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,
                recBufferSize);

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, reqFreq, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, writeBufferSize, AudioTrack.MODE_STREAM);

        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        dot = (View)findViewById(R.id.CircleView);

        dot.setY(0);
        dot.setX(0);

        height = new Height();
        height.set(0);

        width = new Width();
        width.set_width(0);

        medians = new float[1000];
        runs = 0;

//        height.setOnHeightChangeListener(new OnHeightChangeListener() {
//            @Override
//            public void onFloatChanged(float newValue) {
//                dot.setY(newValue);
//            }
//        });
//
//        width.setOnWidthChangeListener(new OnWidthChangeListener() {
//            @Override
//            public void onFloattChanged(float newValue) {
//                dot.setX(newValue);
//            }
//        });

        cos = new float[writeBufferSize];
        sin = new float[writeBufferSize];
        samples1 = new short[writeBufferSize];
        samples2 = new short[writeBufferSize];
        int freq = 16000;
        int i;
        for (i = 0; i < writeBufferSize; i++){
            cos[i] = (float)Math.sin(2*Math.PI*freq*i/reqFreq);
            samples1[i] = (short)(cos[i] * 10000);
            sin[i] = (float) ((float)(-1)*Math.cos(2*Math.PI*freq*i/reqFreq));
            samples2[i] = (short)(sin[i] * 10000);
        }

        offset = 0;

        startRecording();

        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    gate.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(!interrupt){
                    audioTrack.write(samples1, 0, writeBufferSize);
                }
            }
        });

        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    gate.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while(!interrupt) {
//                    short[] buffer1Cp = buffer1.clone();
                    int i;
                    float total3 = 0;
                    float total4 = 0;
                    float[] buffer3 = new float[recBufferSize];
                    float[] buffer4 = new float[recBufferSize];
                    for (i = 0; i < buffer1.length; i++) {
                        float temp1 = (float) buffer1[i];
                        float temp2 = (float) samples1[i];
                        float temp3 = (float) samples2[i];
                        buffer3[i] = temp1 * temp2;
                        buffer4[i] = temp1 * temp3;
                        total3 += buffer3[i];
                        total4 += buffer4[i];
                    }

                    avg3 = total3/buffer1.length/12000;
                    avg4 = total4/buffer1.length/12000;
                    count = count + 1;
//                    if (count%2==0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                height.set((short)avg4);
//                                width.set_width((short)avg3);
                                dot.setY(avg4-yOffset);
                                dot.setX(avg3-xOffset);
                            }
                        });
//                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    gate.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(!interrupt){
                    audioRecord1.read(buffer1, 0, recBufferSize);
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            gate.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    void startRecording(){
        buffer1 = new short[recBufferSize];
        while(audioRecord1.getState() == AudioRecord.STATE_UNINITIALIZED){
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

        audioRecord1.startRecording();
        audioTrack.play();
    }

    


    @Override
    protected void onDestroy() {
        super.onDestroy();
        interrupt = true;
    }



}
