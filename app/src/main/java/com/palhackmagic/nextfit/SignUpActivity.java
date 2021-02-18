package com.palhackmagic.nextfit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.palhackmagic.nextfit.MainActivity;

public class SignUpActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_register, container, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }

}