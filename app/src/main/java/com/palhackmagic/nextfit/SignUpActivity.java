package com.palhackmagic.nextfit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.palhackmagic.nextfit.R;
import com.palhackmagic.nextfit.data.Landing;
import com.palhackmagic.nextfit.ui.login.LoginActivity;
import com.palhackmagic.nextfit.data.Landing;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText mphone, memail, mpassword, mrepassword, mfullname;
    Button mbtn_register, mbtn_gotologin;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_register, container, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mphone = findViewById(R.id.phone);
        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        mfullname = findViewById(R.id.fullName);
        mrepassword = findViewById(R.id.repassword);
        mbtn_register = findViewById(R.id.btn_register);
        mbtn_gotologin = findViewById(R.id.btn_gotologin);

        mAuth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressbar);

        mbtn_gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        mbtn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                String repassword = mrepassword.getText().toString().trim();
                String phoneNum = mphone.getText().toString().trim();
                String phone = phoneNum.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
                String fullName = mfullname.getText().toString().trim();

                if (TextUtils.isEmpty(fullName)) {
                    mfullname.setError("Full Name is Required");
                    return;
                }

                else if (TextUtils.isEmpty(email)) {
                    memail.setError("Email is Required");
                    return;
                }

                else if (TextUtils.isEmpty(password)) {
                    mpassword.setError("Password is Required");
                    return;
                }

                else if (TextUtils.isEmpty(repassword)) {
                    mpassword.setError("Confirm your password");
                    return;
                }

                else if (password.length() <= 5) {
                    mpassword.setError("Password must be greater than 5 characters");
                    return;
                }

                else if(!(password.equals(repassword))) {
                    mrepassword.setError("Password Not matching");
                    return;
                }

                progressbar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Landing.class));
                        }else {
                            Toast. makeText(SignUpActivity.this, "Error Signing Up" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
