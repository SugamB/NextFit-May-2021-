package com.palhackmagic.nextfit.data;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginCallBack extends AppCompatActivity {

    String string;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        string = intent.getDataString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        String accessToken = string.substring(string.indexOf("#access_token") + 14, string.indexOf("&user_id"));
        String userId = string.substring(string.indexOf("&user_id")+9, string.indexOf("&scope"));
        String tokenType = string.substring(string.indexOf("&token_type")+12,string.indexOf("&expires_in"));

//        String accessToken = string.substring(string.indexOf("&access_token") + 14);
//        String userId = string.substring(string.indexOf("&user_id")+9, string.indexOf("&token_type"));
//        String tokenType = string.substring(string.indexOf("&token_type")+12,string.indexOf("&expires_in"));

        Log.i("TAG", string);
        Log.i("TAG", accessToken);
        Log.i("TAG", userId);
        Log.i("TAG", tokenType);

        Intent intent = new Intent(getApplicationContext(), Testapi.class);
        intent.putExtra("string", string);
        intent.putExtra("accessToken",accessToken);
        intent.putExtra("userId",userId);
        intent.putExtra("tokenType",tokenType);
        startActivity(intent);

    }
}
