package com.palhackmagic.nextfit.data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.palhackmagic.nextfit.data.model.Steps;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Testapi extends AppCompatActivity {
    FirebaseAuth mAuth;
    private DatabaseReference mrootref;
    public String[] separated;
    String userId;
    public ArrayList<Steps> stepsArrayList = new ArrayList<>();

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


        // Call for Profile
        profileClient.newCall(profileRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();
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


        // CAll for monthly steps activity
        stepActivityMonthClient.newCall(stepActivityMonthRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
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


        // CAll for Activity
        activityClient.newCall(activityRequest).enqueue(new Callback() {
              @Override
              public void onFailure(Call call, IOException e) {
                  String mMessage = e.getMessage().toString();
//call.cancel();
              }

              @Override
              public void onResponse(Call call, Response response) throws IOException {

                  String mMessage = response.body().string();
                  try {
                      JSONObject jsonRootObject = new JSONObject(mMessage);
                      JSONObject jsonObjectGoals = jsonRootObject.optJSONObject("goals");
                      int goalCalories = Integer.parseInt( jsonObjectGoals.optString("caloriesOut") );
                      JSONObject jsonObjectSummary = jsonRootObject.getJSONObject("summary");

                      int activityCalories = Integer.parseInt( jsonObjectSummary.optString("activityCalories")); //probably means how much calories burned from doing some activity
                      int caloriesBMR = Integer.parseInt( jsonObjectSummary.optString("caloriesBMR")); // dont know what it means
                      int caloriesOut = Integer.parseInt( jsonObjectSummary.optString("caloriesOut")); // probably means how much calories the body actually burned (by metabolism and whatever)


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

//call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String mMessage = response.body().string();
                try {
                    JSONObject jsonRootObject = new JSONObject(mMessage);

                } catch (Exception e) {

                }

            }
        });

        Toast.makeText(getApplicationContext(), "Fitbit Sync Successful", Toast.LENGTH_LONG).show();

        finish();
    }
}