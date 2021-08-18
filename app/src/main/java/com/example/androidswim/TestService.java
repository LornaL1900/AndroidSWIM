package com.example.androidswim;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @author yuansui
 * @since 2021/7/2
 */
class TestService extends Service {

    private short[] buffer;

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                // 开始接收输入
                // read(buffer)
                // 判断samples大小??
                TestManager.get().addItem(buffer);
            }
        }.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
