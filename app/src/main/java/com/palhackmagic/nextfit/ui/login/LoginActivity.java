package com.palhackmagic.nextfit.ui.login;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.palhackmagic.nextfit.MainActivity;
import com.palhackmagic.nextfit.R;
import com.palhackmagic.nextfit.SignUpActivity;
import com.palhackmagic.nextfit.data.Landing;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button btnsignin, btnsignup;
    FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnsignin = findViewById(R.id.signin);
        btnsignup = findViewById(R.id.signup);
        //ProgressBar loadingProgressBar = findViewById(R.id.loading);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailId = email.getText().toString().trim();
                String pwd = password.getText().toString().trim();

                if (emailId.isEmpty()) {
                    email.setError("Please enter the email address");
                    email.requestFocus();
                }
                else if (pwd.isEmpty()) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if (emailId.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT);
                }
                else {
                    mAuth.signInWithEmailAndPassword(emailId, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Landing.class));
                            }else {
                                Toast.makeText(LoginActivity.this, "Login Unsuccessful, check your credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), Landing.class));
//            finish();
//        }
//    }
}

