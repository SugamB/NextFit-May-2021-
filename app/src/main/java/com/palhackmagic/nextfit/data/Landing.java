package com.palhackmagic.nextfit.data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.palhackmagic.nextfit.R;

public class Landing extends AppCompatActivity {

    Button button1;
    String url = "https://www.fitbit.com/oauth2/authorize?" +
                "response_type=token" +
                "&client_id=22C569" +
                "&redirect_uri=nextfit://logincallback" +
                "&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight" +
                "&expires_in=604800";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        button1= findViewById(R.id.button);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder customTabIntent = new CustomTabsIntent.Builder();
                openCustomTabs(Landing.this,customTabIntent.build(), Uri.parse(url));


            }
        });
    }

    public static void openCustomTabs(Activity activity, CustomTabsIntent customTabsIntent, Uri uri){
        String packageName = "com.android.chrome";
        if (packageName!=null){
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity,uri);
        }
        else{
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity,uri);
//            activity.startActivity(new Intent(Intent.ACTION_VIEW));
        }
    }}