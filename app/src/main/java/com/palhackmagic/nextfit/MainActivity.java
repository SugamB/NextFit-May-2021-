package com.palhackmagic.nextfit;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.palhackmagic.nextfit.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private Handler mWaitHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWaitHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                try {
                    //This leads to the next page i.e, start the next activity.
                    Intent intent = new Intent(MainActivity.this,  LoginActivity.class);
                    startActivity(intent);
                    //Let's Finish Splash Activity since we don't want to show this when user press back button.
                    finish();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 3500);  // Give a 5 seconds delay.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed. 
        mWaitHandler.removeCallbacksAndMessages(null);
    }
}