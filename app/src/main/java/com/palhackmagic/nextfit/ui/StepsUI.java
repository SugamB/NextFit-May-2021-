package com.palhackmagic.nextfit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    public int goal_steps = 100;
    public int current_steps = 75;

    FirebaseAuth mAuth;
    String userId;

    int calories = 0;


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

        DatabaseReference mrootref = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        final TextView tvSteps = (TextView) view.findViewById(R.id.TVSteps);
        final EditText etSteps = (EditText) view.findViewById(R.id.ETSteps);

        progressBar.setMax(goal_steps);
        progressBar.setProgress(current_steps);
        String temp = current_steps + "/";
        tvSteps.setText(temp);
        etSteps.setText(Integer.toString(goal_steps));

        etSteps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String temp = s.toString();
                    goal_steps = Integer.parseInt(temp);
                    progressBar.setMax(goal_steps);
                    progressBar.setProgress(current_steps);
                }
                catch (Exception e) {
                    etSteps.setText(Integer.toString(goal_steps));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        DatabaseReference caloriesRef = mrootref.child("Users").child(userId).child("Calories");
        caloriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    calories = (int) snapshot.getValue();
                }
                catch (Exception e) {
                }
                updateCalories(calories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;

    }

    public void updateText(TextView textView, int current_steps, int goal_steps) {
        String temp = current_steps + "/";
        textView.setText(temp);
    }

    public void updateCalories(int calories) {
        Log.i("TAG", String.valueOf(calories));
    }
}