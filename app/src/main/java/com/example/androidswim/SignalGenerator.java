package com.example.androidswim;

public class SignalGenerator {
    private int buffer_size;
    private int sample_rate;

    public SignalGenerator(int bs, int sr) {
        buffer_size = bs;
        sample_rate = sr;
    }

    public short[] generate(int freq, boolean real, boolean square) {
        float[] raw_samples = new float[buffer_size];
        short[] samples = new short[buffer_size];
        int i;
        if(square&&real){
//            for (i = 0; i < buffer_size; i++){
//                raw_samples[i] = (float)Math.cos(2*Math.PI*freq*i/sample_rate) + (float)1/3 *
//                        (float)Math.cos(6*Math.PI*freq*i/sample_rate) + (float)1/5 * (float)Math.cos(10*Math.PI*freq*i/sample_rate)
//                        + (float)1/7 * (float)Math.cos(14*Math.PI*freq*i/sample_rate) + (float)1/9 * (float)Math.cos(18*Math.PI*freq*i/sample_rate);
//                samples[i] = (short)(raw_samples[i] * 10000);
//            }

            int half_period = sample_rate/freq/2;
            short curr = 10000;
            for (i = 0; i < half_period/2; i++) {
                samples[i] = curr;
            }
            for (i = half_period/2; i < buffer_size; i++) {
                if ((i - half_period/2) % half_period == 0) {
                    curr = (short) (-1 * curr);
                }
                samples[i] = curr;
            }
        }
        else if (square) {
//            for (i = 0; i < buffer_size; i++){
//                raw_samples[i] = (float)Math.sin(2*Math.PI*freq*i/sample_rate) + (float)1/3 *
//                        (float)Math.sin(6*Math.PI*freq*i/sample_rate) + (float)1/5 * (float)Math.sin(10*Math.PI*freq*i/sample_rate);
//                samples[i] = (short)(raw_samples[i] * 10000);
//            }
            int half_period = sample_rate/freq/2;
            short curr = -10000;
            for (i = 0; i < buffer_size; i++) {
                if (i % half_period == 0) {
                    curr = (short) (-1 * curr);
                }
                samples[i] = curr;
            }
        }

        else if (real) {
            for (i = 0; i < buffer_size; i++) {
                raw_samples[i] = (float) Math.cos(2 * Math.PI * freq * i / sample_rate);
                samples[i] = (short) (raw_samples[i] * 10000);
            }
        }
        else{
            for (i = 0; i < buffer_size; i++) {
                raw_samples[i] = (float) Math.sin(2 * Math.PI * freq * i / sample_rate);
                samples[i] = (short) (raw_samples[i] * 10000);
            }
        }

        return samples;
    }
}
