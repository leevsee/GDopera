package com.leeves.h.gdopera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.leeves.h.gdopera.exit.SysApplication;

/**
 * Function：启动过度界面
 * Created by h on 2016/9/7.
 *
 * @author Leevs
 */
public class SplashActivity extends Activity {

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }

    //启动过度后销毁
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
