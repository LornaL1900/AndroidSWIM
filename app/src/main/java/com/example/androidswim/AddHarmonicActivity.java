package com.example.androidswim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

public class AddHarmonicActivity extends AppCompatActivity {

    EditText et_freq, et_phase, et_phase_denom, et_amp, et_amp_denom;
    CheckBox cb_neg_phase, cb_div_phase, cb_pi, cb_neg_amp, cb_div_amp;
    double phase, amp;
    int wt, freq;
    RadioGroup rg;
    String wavestring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_harmonic);
        Intent intent = getIntent();
        et_freq = findViewById(R.id.et_freq);
        et_phase = findViewById(R.id.et_phase);
        et_phase_denom = findViewById(R.id.et_phase_denom);
        et_amp = findViewById(R.id.et_amp);
        et_amp_denom = findViewById(R.id.et_amp_denom);
        cb_neg_phase = findViewById(R.id.cb_neg_phase);
        cb_div_phase = findViewById(R.id.cb_div_phase);
        cb_pi = findViewById(R.id.cb_pi);
        cb_neg_amp = findViewById(R.id.cb_neg_amp);
        cb_div_amp = findViewById(R.id.cb_div_amp);
        rg = findViewById(R.id.radioGroup);
    }

    public void create_harmonic(View view) {
        wt = get_wt();
        freq = Integer.parseInt(et_freq.getText().toString());
        phase = get_phase();
        amp = get_amp();
        wavestring = get_wavestring();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("new_wt", wt);
        returnIntent.putExtra("new_freq", freq);
        returnIntent.putExtra("new_phase", phase);
        returnIntent.putExtra("new_amp", amp);
        returnIntent.putExtra("new_ws", wavestring);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public int get_wt() {
        int selectedId = rg.getCheckedRadioButtonId();
        if (selectedId == R.id.rb_sine) return 0;
        else return 1;
    }

    public double get_phase() {
        double phase = (double) Integer.parseInt(et_phase.getText().toString());
        if (cb_pi.isChecked()) phase = phase * Math.PI;
        if (cb_div_phase.isChecked()) phase = phase / Integer.parseInt(et_phase_denom.getText().toString());
        if (cb_neg_phase.isChecked()) phase = phase * -1;
        return phase;
    }

    public double get_amp() {
        double amp = (double) Integer.parseInt(et_amp.getText().toString());
        if (cb_div_amp.isChecked()) amp = amp / Integer.parseInt(et_amp_denom.getText().toString());
        if (cb_neg_amp.isChecked()) amp = amp * -1;
        return amp;
    }

    public String get_wavestring(){
        String wavestring;
        if (wt==0) wavestring = "sin(";
        else wavestring = "cos(";
        wavestring = wavestring + Integer.toString(2*freq) + "PIt";
        if (cb_neg_phase.isChecked()) wavestring = wavestring + "-";
        else wavestring = wavestring + "+";
        wavestring = wavestring + et_phase.getText().toString();
        if (cb_div_phase.isChecked()) wavestring = wavestring + "/" + et_phase_denom.getText().toString();
        if (cb_pi.isChecked()) wavestring = wavestring + "PI";
        wavestring = wavestring + ')';
        if (cb_div_amp.isChecked()) wavestring = "/" + et_amp_denom.getText().toString() + wavestring;
        wavestring = et_amp.getText().toString() + wavestring;
        if (cb_neg_amp.isChecked()) wavestring = "-" + wavestring;
        return wavestring;
    };

}
