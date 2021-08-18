package com.example.androidswim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;


/**
 * @author yuansui
 * @since 2021/7/2
 */
class TestActivity extends Activity {

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      TestManager.get().setListener(new TestManager.Listener() {
         @Override
         public void onResult(short[] data) {
            // 拿到处理完后的数据
            // 显示 已经在主线程了
         }
      });

      startService(new Intent(this, TestService.class));
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();

      stopService(new Intent(this, TestService.class));
   }
}
