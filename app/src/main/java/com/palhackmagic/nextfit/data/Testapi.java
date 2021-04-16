package com.palhackmagic.nextfit.data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.palhackmagic.nextfit.R;

import java.io.IOException;

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

        final TextView text = (TextView)findViewById(R.id.textview);

//        Log.i("TAG", "------------------TestAPI activity starts here ---------------");
//        Log.i("TAG", getIntent().getStringExtra("string"));
//        Log.i("TAG", getIntent().getStringExtra("accessToken"));
//        Log.i("TAG", getIntent().getStringExtra("userId"));
//        Log.i("TAG", getIntent().getStringExtra("tokenType"));


        String url = "https://api.fitbit.com/1/user/-/profile.json";

        String url2 = "https://api.fitbit.com/1/user/-/activities/steps/date/today/1w.json";
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
                String mMessage = e.getMessage().toString();
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
                separated = mMessage.split("[{]",0);
                Log.i("TAG", "--------------------Daily Step Counts of last 7 days--------------------");
                for (String a : separated)
                    Log.i("TAG", a);
            }

        });

        Log.i("TAG", "------------------333333---------------");
//        ListView simpleList;
//        String[] JsonList = separated;
//        simpleList = (ListView)findViewById(R.id.simpleListView);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, JsonList);
//        simpleList.setAdapter(arrayAdapter);
    }
}