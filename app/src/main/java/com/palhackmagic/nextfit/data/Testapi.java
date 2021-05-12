package com.palhackmagic.nextfit.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.palhackmagic.nextfit.R;
import com.palhackmagic.nextfit.data.model.Calories;
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
    public Calories calories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mrootref = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        String profileUrl = "https://api.fitbit.com/1/user/-/profile.json";
        String stepActivityMonthUrl = "https://api.fitbit.com/1/user/-/activities/steps/date/today/1m.json";
        String stepsActivityWeekUrl = "https://api.fitbit.com/1/user/-/activities/steps/date/today/1w.json";
        String activityUrl = "https://api.fitbit.com/1/user/-/activities/date/today.json";
        String heartrateUrl = "https://api.fitbit.com/1/user/-/activities/heart/date/today/1d/1min.json";

        Log.i("TAG", profileUrl);

        OkHttpClient profileClient = new OkHttpClient();
        OkHttpClient stepActivityMonthClient = new OkHttpClient();
        OkHttpClient activityClient = new OkHttpClient();
        OkHttpClient heartrateClient = new OkHttpClient();

        Request profileRequest = new Request.Builder()
                .url(profileUrl)
                .header("Authorization", "Bearer " + getIntent().getStringExtra("accessToken"))
                .build();

        Request stepActivityMonthRequest = new Request.Builder()
                .url(stepActivityMonthUrl)
                .header("Authorization", "Bearer " + getIntent().getStringExtra("accessToken"))
                .build();

        Request activityRequest = new Request.Builder()
                .url(activityUrl)
                .header("Authorization", "Bearer " + getIntent().getStringExtra("accessToken"))
                .build();

        Request heartrateRequest = new Request.Builder()
                .url(heartrateUrl)
                .header("Authorization", "Bearer " + getIntent().getStringExtra("accessToken"))
                .build();
        Log.i("TAG", "------------------11111111---------------");


        // Call for Profile
        profileClient.newCall(profileRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
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
                            }
                        });
                        final HashMap<String, Object> map = new HashMap<>();
                        map.put(key, value);
                        mrootref.child("Users").child(userId).child("fitbit").updateChildren(map);
                    }

                } catch (Exception e) {

                }
            }

        });


        Log.i("TAG", "------------------222222---------------");
        // CAll for monthly steps activity
        stepActivityMonthClient.newCall(stepActivityMonthRequest).enqueue(new Callback() {
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
                    int steps = 0;
                    String date = "";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        steps = Integer.parseInt(jsonObject.optString("value"));
                        date = jsonObject.optString("dateTime");

                        Log.i("TAG", date + " -> " + steps);
                        stepsArrayList.add(new Steps(date, steps));

                        final HashMap<String, Object> map = new HashMap<>();
                        map.put(date, steps);
                        mrootref.child("Users").child(userId).child("fitbitsteps").updateChildren(map);
                    }
                    mrootref.child("Users").child(userId).child("todaySteps").setValue(steps);

                } catch (Exception e) {

                }
            }

        });


        Log.i("TAG", "------------------333333---------------");
        // CAll for Activity
        activityClient.newCall(activityRequest).enqueue(new Callback() {
              @Override
              public void onFailure(Call call, IOException e) {
                  String mMessage = e.getMessage().toString();
                  Log.w("failure Response", mMessage);
//call.cancel();
              }

              @Override
              public void onResponse(Call call, Response response) throws IOException {

                  String mMessage = response.body().string();
                  Log.i("TAG", mMessage);
                  try {
                      JSONObject jsonRootObject = new JSONObject(mMessage);
                      JSONObject jsonObjectGoals = jsonRootObject.optJSONObject("goals");
                      int goalCalories = Integer.parseInt( jsonObjectGoals.optString("caloriesOut") );
                      JSONObject jsonObjectSummary = jsonRootObject.getJSONObject("summary");

                      int activityCalories = Integer.parseInt( jsonObjectSummary.optString("activityCalories")); //probably means how much calories burned from doing some activity
                      int caloriesBMR = Integer.parseInt( jsonObjectSummary.optString("caloriesBMR")); // dont know what it means
                      int caloriesOut = Integer.parseInt( jsonObjectSummary.optString("caloriesOut")); // probably means how much calories the body actually burned (by metabolism and whatever)

                      Log.i("TAG", goalCalories + ".." + activityCalories + ".." + caloriesBMR + ".." + caloriesOut);

                      calories = new Calories(goalCalories, activityCalories, caloriesBMR, caloriesOut);
                      mrootref.child("Users").child(userId).child("Calories").setValue(activityCalories);

                  } catch (Exception e) {

                  }
              }
        });


        // Call for Heart Rate
        heartrateClient.newCall(heartrateRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException ioe) {
                String mMessage = ioe.getMessage().toString();
                Log.w("failure Response", mMessage);

//call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String mMessage = response.body().string();
                Log.i("TAG", "Heartrate should be here");
                Log.i("TAG", mMessage);
                try {
                    JSONObject jsonRootObject = new JSONObject(mMessage);

                } catch (Exception e) {

                }

            }
        });

        finish();
    }
}