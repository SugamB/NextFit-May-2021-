package com.palhackmagic.nextfit.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;
import com.palhackmagic.nextfit.R;

public class StepsUI extends AppCompatActivity {

    public int goal_steps = 100;
    public int current_steps = 75;

    public int getGoal_steps() {
        return goal_steps;
    }

    public void setGoal_steps(int goal_steps) {
        this.goal_steps = goal_steps;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_u_i);

        final Button btn_inc = (Button) findViewById(R.id.btn_inc);
        final Button btn_dec = (Button) findViewById(R.id.btn_dec);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        final Slider slider = (Slider) findViewById(R.id.slider);
        final TextView tvSteps = (TextView) findViewById(R.id.TVSteps);
        final EditText etSteps = (EditText) findViewById(R.id.ETSteps);

        progressBar.setMax(goal_steps);
        progressBar.setProgress(current_steps);
        slider.setValue(current_steps);
        slider.setValueFrom(0);
        slider.setValueTo(goal_steps);
        String temp = current_steps + "/";
        tvSteps.setText(temp);
        etSteps.setText(Integer.toString(goal_steps));

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                progressBar.setProgress((int) value);
                current_steps = progressBar.getProgress();
                updateText(tvSteps, current_steps, goal_steps);
            }
        });

        btn_inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prog = progressBar.getProgress();
                if(prog < goal_steps) {
                    progressBar.setProgress(prog + 10);
                    current_steps = progressBar.getProgress();
                    slider.setValue(current_steps);
                    updateText(tvSteps, current_steps, goal_steps);
                }
            }
        });

        btn_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prog = progressBar.getProgress();
                if(prog > 0) {
                    progressBar.setProgress(prog - 10);
                    current_steps = progressBar.getProgress();
                    slider.setValue(current_steps);
                    updateText(tvSteps, current_steps, goal_steps);
                }
            }
        });

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
//                    slider.setValueTo(goal_steps);
//                    slider.setValue(current_steps);
                }
                catch (Exception e) {
                    etSteps.setText(Integer.toString(goal_steps));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void updateText(TextView textView, int current_steps, int goal_steps) {
        String temp = current_steps + "/";
        textView.setText(temp);
    }
}