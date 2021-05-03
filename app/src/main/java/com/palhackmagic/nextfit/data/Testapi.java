package com.palhackmagic.nextfit.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.palhackmagic.nextfit.R;
import com.palhackmagic.nextfit.data.model.Steps;
import com.palhackmagic.nextfit.ui.StepsGraph;
import com.palhackmagic.nextfit.ui.StepsUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.io.Serializable;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Testapi extends AppCompatActivity {
    FirebaseAuth mAuth;
    private DatabaseReference mrootref;
    public String[] separated;
    String userId;
    public ArrayList<Steps> stepsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testapi);

        mrootref = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        final TextView textViewSteps = (TextView) findViewById(R.id.textviewSteps);
        final TextView textViewProfile = (TextView) findViewById(R.id.textviewProfile);
        final Button nextButton = (Button) findViewById(R.id.nextActivity);
        final Button btnGraph = (Button) findViewById(R.id.nextActivityGraph);

        Log.i("TAG", "------------------TestAPI activity starts here ---------------");
        Log.i("TAG", getIntent().getStringExtra("string"));
        Log.i("TAG", getIntent().getStringExtra("accessToken"));
        Log.i("TAG", getIntent().getStringExtra("userId"));
        Log.i("TAG", getIntent().getStringExtra("tokenType"));


        String profileUrl = "https://api.fitbit.com/1/user/-/profile.json";
        String stepActivityMonthUrl = "https://api.fitbit.com/1/user/-/activities/steps/date/today/1m.json";
        String stepsActivityWeekUrl = "https://api.fitbit.com/1/user/-/activities/steps/date/today/1w.json";

        ;

//        Log.i("TAG", "------------------TestAPI activity starts here ---------------");
//        Log.i("TAG", getIntent().getStringExtra("string"));
//        Log.i("TAG", getIntent().getStringExtra("accessToken"));
//        Log.i("TAG", getIntent().getStringExtra("userId"));
//        Log.i("TAG", getIntent().getStringExtra("tokenType"));



//        String header1 = ""
        Log.i("TAG", profileUrl);

        OkHttpClient client = new OkHttpClient();
        OkHttpClient client2 = new OkHttpClient();

        Request request = new Request.Builder()
                .url(profileUrl)
                .header("Authorization", "Bearer " + getIntent().getStringExtra("accessToken"))
                .build();

        Request request2 = new Request.Builder()
                .url(stepActivityMonthUrl)
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
                Log.i("TAG", mMessage);
                try {
                    JSONObject jsonRootObject = new JSONObject(mMessage);
                    JSONObject jsonObject = jsonRootObject.optJSONObject("user");
                    final String[] interestedValues = {"age", "averageDailySteps", "dateOfBirth", "displayName", "firstName", "lastName", "height", "fullname", "gender"};
                    for (int i = 0; i < interestedValues.length; i++) {
                        final String key = interestedValues[i];
                        final String value  = jsonObject.optString(key);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String a = key + " -> " + value;
//                                textViewProfile.append(a + "\n");
                            }
                        });
                        final HashMap<String, Object> map = new HashMap<>();
                        map.put(key, value);
                        mrootref.child("Users").child(userId).child("fitbit").updateChildren(map);
                    }
//                    DONT START ACTIVITY JUST AFTER GETTING PROFILE, NEED TO GET DATA TOO
//                    MAYBE NEED TO SPLIT UP THESE CALLS LATER
//                    startActivity(new Intent(getApplicationContext(), profile.class));

                } catch (Exception e) {

                }
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
                    JSONObject jsonRootObject = new JSONObject(mMessage);
                    JSONArray jsonArray = jsonRootObject.optJSONArray("activities-steps");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        final int steps = Integer.parseInt(jsonObject.optString("value"));
                        final String date = jsonObject.optString("dateTime");

                        Log.i("TAG", date + " -> " + steps);
                        stepsArrayList.add(new Steps(date, steps));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String a = date + "->" + steps;
                                textViewSteps.append(a + "\n");
                            }
                        });

                        final HashMap<String, Object> map = new HashMap<>();
                        map.put(date, steps);
                        mrootref.child("Users").child(userId).child("fitbitsteps").updateChildren(map);
                    }

                } catch (Exception e) {

                }
            }

        });

        Log.i("TAG", "------------------333333---------------");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StepsUI.class);
                startActivity(intent);
            }
        });

        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StepsGraph.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("StepsArrayList", (Serializable)stepsArrayList);
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });

    }
}