package com.palhackmagic.nextfit.data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.palhackmagic.nextfit.R;

public class Landing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

//        https://www.fitbit.com/oauth2/authorize?response_type=code&client_id=22C569&redirect_uri=https://localhost&scope=activity%20nutrition%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight
    }
}