package com.palhackmagic.nextfit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.palhackmagic.nextfit.R;

public class StepsUI extends Fragment {

    public int goal_steps = 5000;
    public int current_steps = 0;


    FirebaseAuth mAuth;
    String userId;

    int goal_calories = 200;
    int current_calories = 0;

    ProgressBar stepsProgressBar;
    TextView tvSteps;
    EditText etSteps;

    ProgressBar caloriesProgressBar;
    TextView tvCalories;
    EditText etCalories;

    ProgressBar scoreProgressBar;
    TextView tvScore;

    public int getGoal_steps() {
        return goal_steps;
    }

    public void setGoal_steps(int goal_steps) {
        this.goal_steps = goal_steps;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_steps_u_i, container, false);

        final DatabaseReference mrootref = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        DatabaseReference userRef = mrootref.child("Users").child(userId);

        stepsProgressBar = (ProgressBar) view.findViewById(R.id.stepsProgressbar);
        final TextView tvSteps = (TextView) view.findViewById(R.id.TVSteps);
        final EditText etSteps = (EditText) view.findViewById(R.id.ETSteps);

        caloriesProgressBar = (ProgressBar) view.findViewById(R.id.caloriesProgressbar);
        final TextView tvCalories = (TextView) view.findViewById(R.id.TVCalories);
        final EditText etCalories = (EditText) view.findViewById(R.id.ETCalories);

        scoreProgressBar = (ProgressBar) view.findViewById(R.id.scoreProgressbar);
        final TextView tvScore = (TextView) view.findViewById(R.id.TVScore);

        etSteps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String temp = s.toString();
                    goal_steps = Integer.parseInt(temp);
                    stepsProgressBar.setMax(goal_steps);
                    stepsProgressBar.setProgress(current_steps);

                    mrootref.child("Users").child(userId).child("goalSteps").setValue(goal_steps);

                    Scoring(tvScore);
                }
                catch (Exception e) {
                    etSteps.setText(Integer.toString(goal_steps));

                }
                Scoring(tvScore);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCalories.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String temp = s.toString();
                    goal_calories = Integer.parseInt(temp);
                    caloriesProgressBar.setMax(goal_calories);
                    caloriesProgressBar.setProgress(current_calories);

                    mrootref.child("Users").child(userId).child("goalCalories").setValue(goal_calories);

                    Scoring(tvScore);
                }
                catch (Exception e) {
                    etCalories.setText(Integer.toString(goal_calories));
                }
                Scoring(tvScore);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        DatabaseReference caloriesRef = userRef.child("Calories");
        caloriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    current_calories = ((Long) snapshot.getValue()).intValue();
                    caloriesProgressBar.setProgress(current_calories);
                    updateText(tvCalories, current_calories);
                    Scoring(tvScore);
                }
                catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference stepRef = userRef.child("todaySteps");
        stepRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    current_steps = ((Long) snapshot.getValue()).intValue();
                    stepsProgressBar.setProgress(current_steps);
                    updateText(tvSteps, current_steps);
                    Scoring(tvScore);

                }
                catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference goalStepRef = userRef.child("goalSteps");
        goalStepRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    goal_steps = ((Long) snapshot.getValue()).intValue();
                    stepsProgressBar.setMax(goal_steps);
                    stepsProgressBar.setProgress(current_steps);
                    etSteps.setText(Integer.toString(goal_steps));
                    Scoring(tvScore);
                }
                catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final DatabaseReference goalCaloriesRef = userRef.child("goalCalories");
        goalCaloriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    goal_calories = ((Long) snapshot.getValue()).intValue();
                    caloriesProgressBar.setMax(goal_calories);
                    caloriesProgressBar.setProgress(current_calories);
                    updateText(tvCalories, current_calories);
                    etCalories.setText(Integer.toString(goal_calories));
                    Scoring(tvScore);
                }
                catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // NOT UPDATING OR INITIALIZING ANY UI BECAUSE IT MESSES UP THE DATABASE LISTENERS
        // IT SETS THE VALUE BEFORE THE VALUE IS RETRIEVED FROM DATABASE SO EVERYTIME THE APP IS RUN, IT ONLY HAS DUMMY VALUES FOR GOALS
        // SO BY NOT INITIALIZING, WE LET DATABASE LISTENERS DO THE INITIALIZATION FOR US

        return view;

    }


    public void updateText(TextView textView, int current_value) {
        String temp = current_value + "/";
        textView.setText(temp);
    }

    public void Scoring(TextView textView) {
        double steps = current_steps;
        double stepscore =0;
        double stepgoal = goal_steps;
        double caloriegoal = goal_calories;
        double calories = current_calories;
        double caloriescore=0;

        double ran = Math.random();
        if(steps>=stepgoal){ stepscore = 1;}
        else{stepscore = 1 - ((stepgoal-steps)/stepgoal) ;}
        if(calories>=caloriegoal){ caloriescore =1;}
        else{caloriescore = 1 - (caloriegoal - calories)/caloriegoal ;}
        int score = (int) ( ( (stepscore*0.45) + (caloriescore*0.45) + (ran*0.1) )*100 );
        updateText(textView, score);
        scoreProgressBar.setProgress(score);
    }
}