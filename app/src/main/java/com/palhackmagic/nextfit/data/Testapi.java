package com.palhackmagic.nextfit.data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.palhackmagic.nextfit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

public class Testapi extends AppCompatActivity {

    public String[] separated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testapi);

        final TextView textView = (TextView) findViewById(R.id.textview);
        Log.i("TAG", "------------------TestAPI activity starts here ---------------");
        Log.i("TAG", getIntent().getStringExtra("string"));
        Log.i("TAG", getIntent().getStringExtra("accessToken"));
        Log.i("TAG", getIntent().getStringExtra("userId"));
        Log.i("TAG", getIntent().getStringExtra("tokenType"));


        String url = "https://api.fitbit.com/1/user/-/profile.json";
        String stepActivity = "https://api.fitbit.com/1/user/-/activities/steps/date/today/1m.json";
        String url2 = "https://api.fitbit.com/1/user/-/activities/steps/date/today/1w.json";

        ;

//        Log.i("TAG", "------------------TestAPI activity starts here ---------------");
//        Log.i("TAG", getIntent().getStringExtra("string"));
//        Log.i("TAG", getIntent().getStringExtra("accessToken"));
//        Log.i("TAG", getIntent().getStringExtra("userId"));
//        Log.i("TAG", getIntent().getStringExtra("tokenType"));



//        String header1 = ""
        Log.i("TAG", url);

        OkHttpClient client = new OkHttpClient();
        OkHttpClient client2 = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + getIntent().getStringExtra("accessToken"))
                .build();

        Request request2 = new Request.Builder()
                .url(url2)
                .header("Authorization", "Bearer " + getIntent().getStringExtra("accessToken"))
                .build();

//        Response response = client.newCall(request).execute();
//        Log.e(TAG, response.body().string());
        Log.i("TAG", "------------------11111111---------------");


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();
                separated = mMessage.split("[{]",0);
                Log.i("TAG", "--------------------User Profile--------------------");
                for (String a : separated)
                    Log.i("TAG", a);
            }

        });


        Log.i("TAG", "------------------222222---------------");
        client2.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String mMessage = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(mMessage);
                    Iterator<String> iter = jsonObject.keys();
                    while (iter.hasNext()) {
                        final String key = iter.next();
                        try {
                            final Object value = jsonObject.get(key);
                            Log.i("TAG", key + " -> " + value);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(value.toString());
                                }
                            });

                        }
                        catch (JSONException e) {

                        }
                    }
                    Log.i("TAG", jsonObject.toString());
                    Log.i("TAG", jsonObject.names().toString());
                } catch (JSONException e) {

                }
                String[] separated = mMessage.split("[,]",0);
                separated = mMessage.split("[{]",0);
            }

        });

        Log.i("TAG", "------------------333333---------------");

    }
}