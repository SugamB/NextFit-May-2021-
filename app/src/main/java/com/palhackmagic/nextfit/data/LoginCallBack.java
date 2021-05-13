package com.palhackmagic.nextfit.data;

import android.content.Intent;
import android.os.Bundle;

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

        //get access token with substring (start of token(#access_token=[...]), end of token + 1)
        String accessToken = string.substring(string.indexOf("#access_token") + 14, string.indexOf("&user_id"));
        String userId = string.substring(string.indexOf("&user_id")+9, string.indexOf("&scope"));
        String tokenType = string.substring(string.indexOf("&token_type")+12,string.indexOf("&expires_in"));


        Intent intent = new Intent(getApplicationContext(), Testapi.class);
        intent.putExtra("string", string);
        intent.putExtra("accessToken",accessToken);
        intent.putExtra("userId",userId);
        intent.putExtra("tokenType",tokenType);
        startActivity(intent);

        finish();
    }
}
