package com.example.androidswim;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;

/**
 * @author yuansui
 * @since 2021/7/2
 */
class TestManager {

    interface Listener {
        void onResult(short[] data);
    }

    private static TestManager mInst = null;
    private Listener mListener = null;
    private final ArrayList<Data> list = new ArrayList<>();

    class Data {
        boolean success = false;
        short[] data;

        public Data(short[] data) {
            this.data = data;
        }
    }

    private TestManager() {
    }

    public static TestManager get() {
        if (mInst == null) {
            mInst = new TestManager();
        }
        return mInst;
    }

    public void addItem(short[] data) {
        Data packData = new Data(data);
        list.add(packData);

        if (!list.isEmpty()) {
            Data first = list.get(0);
            if (!first.success) {
                return;
            }
        }

        process(packData);
    }

    private void process(Data data) {
        Processor<Data> processor = new Processor<>(data);
        processor.setListener(new Processor.Listener() {
            @Override
            public void onResult(final Data data) {
                Handler handler = new android.os.Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mListener != null) {
                            mListener.onResult(data.data);
                        }
                        data.success = true;
                        list.remove(data);

                        if (!list.isEmpty()) {
                            process(list.get(0));
                        }
                    }
                });
            }
        });
        processor.process();
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }
}
