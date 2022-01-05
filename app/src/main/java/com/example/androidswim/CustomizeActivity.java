package com.example.androidswim;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class CustomizeActivity extends AppCompatActivity {

    int mode;
    Intent new_intent;
    int multiple;
    int freq;
    EditText et_buffersize, et_frequency;
    SeekBar sb_buffersize, sb_freq;
    TextView curr;
    Typeface tf;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        multiple = 100;
        freq = 440;

        mode = intent.getIntExtra("mode", 0);
        if (mode < 3) {
            new_intent = new Intent(this, OneDotAnimateActivity.class);
        }
        else {
            new_intent = new Intent(this, TwoDotsAnimateActivity.class);
        }

        new_intent.putExtra("mode", mode);

        et_buffersize = findViewById(R.id.et_buffersize);
        et_frequency = findViewById(R.id.et_frequency);
        sb_buffersize = (SeekBar) findViewById(R.id.sb_buffersize);
        sb_freq = (SeekBar) findViewById(R.id.sb_freq);
        curr = null;

        TextView tv_choose6A = findViewById(R.id.tv_choose6A);
        tf = tv_choose6A.getTypeface();

        et_buffersize.setText(String.valueOf(multiple));
        et_frequency.setText(String.valueOf(freq));

        sb_buffersize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                multiple = progress;
                et_buffersize.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_freq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                freq =  progress;
                et_frequency.setText(String.valueOf(freq));
                if (curr!=null) {
                    curr.setTypeface(tf);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void buffersize_enter(View view) {
        multiple = Integer.parseInt(et_buffersize.getText().toString());
        sb_buffersize.setProgress(multiple);
        et_buffersize.setText(String.valueOf(multiple));
    }

    public void freq_enter(View view) {
        freq = Integer.parseInt(et_frequency.getText().toString());
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
    }

    public void Animate(View view){
        new_intent.putExtra("freq", freq);
        new_intent.putExtra("square", false);
        new_intent.putExtra("multiple", multiple);
        startActivity(new_intent);
    }

    public void Choose1000(View view) {
        freq = 1000;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(null, Typeface.NORMAL);
        }
        curr = findViewById(R.id.tv_choose1000);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose2000(View view) {
        freq = 2000;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose2000);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose4000(View view) {
        freq = 4000;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose4000);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose5000(View view) {
        freq = 5000;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose5000);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose8000(View view) {
        freq = 8000;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose8000);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose10000(View view) {
        freq = 10000;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose10000);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose12000(View view) {
        freq = 12000;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose12000);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose6A(View view) {
        freq = 880;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose6A);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose7A(View view) {
        freq = 1760;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose7A);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose7C(View view) {
        freq = 2093;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose7C);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose7E(View view) {
        freq = 2637;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose7E);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose7G(View view) {
        freq = 3136;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose7G);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose8A(View view) {
        freq = 3520;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose8A);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose8C(View view) {
        freq = 4186;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose8C);
        curr.setTypeface(null, Typeface.BOLD);
    }
    public void Choose8Eb(View view) {
        freq = 4973;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose8Eb);
        curr.setTypeface(null, Typeface.BOLD);
    }
    public void Choose8E(View view) {
        freq = 5274;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose8E);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose9A(View view) {
        freq = 7040;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose9A);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose3000(View view) {
        freq = 3000;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose3000);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose6000(View view) {
        freq = 6000;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose6000);
        curr.setTypeface(null, Typeface.BOLD);
    }

    public void Choose16000(View view) {
        freq = 16000;
        sb_freq.setProgress(freq);
        et_frequency.setText(String.valueOf(freq));
        if (curr!=null) {
            curr.setTypeface(tf);
        }
        curr = findViewById(R.id.tv_choose16000);
        curr.setTypeface(null, Typeface.BOLD);
    }



}
