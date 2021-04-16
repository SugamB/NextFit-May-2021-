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

public class Testapi extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testapi);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(100); // 0 <= value <= 100
        Log.i("TAG", "------------------TestAPI activity starts here ---------------");
        Log.i("TAG", getIntent().getStringExtra("string"));
        Log.i("TAG", getIntent().getStringExtra("accessToken"));
        Log.i("TAG", getIntent().getStringExtra("userId"));
        Log.i("TAG", getIntent().getStringExtra("tokenType"));


        String url = "https://api.fitbit.com/1/user/-/profile.json";
        String stepActivity = "https://api.fitbit.com/1/user/-/activities/steps/date/today/1m.json";
//        String header1 = ""

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(stepActivity)
                .header("Authorization", "Bearer " + getIntent().getStringExtra("accessToken"))
                .build();

//        Response response = client.newCall(request).execute();
//        Log.e(TAG, response.body().string());

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
                try {
                    JSONObject jsonObject = new JSONObject(mMessage);
                    Iterator<String> iter = jsonObject.keys();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        try {
                            Object value = jsonObject.get(key);
                            Log.i("TAG", key + " -> " + value);
                        }
                        catch (JSONException e) {

                        }
                    }
                    Log.i("TAG", jsonObject.toString());
                    Log.i("TAG", jsonObject.names().toString());
                } catch (JSONException e) {

                }
                String[] separated = mMessage.split("[,]",0);
                for (String a : separated)
                    Log.i("TAG", a);

            }
        });

    }

}