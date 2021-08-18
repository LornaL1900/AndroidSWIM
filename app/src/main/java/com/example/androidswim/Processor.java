package com.example.androidswim;


/**
 * @author yuansui
 * @since 2021/7/2
 */
class Processor<T> {

    public Processor(T data) {
        mData = data;
    }

    interface Listener {
        void onResult(TestManager.Data data);
    }

    private Listener mListener = null;
    private T mData = null;

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void process() {
        // 处理数据
        new Thread() {

            @Override
            public void run() {
                super.run();

                if (mListener != null) {
//                    mListener.onResult(result);
                }
            }
        }.start();
    }
}

