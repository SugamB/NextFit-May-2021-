package com.palhackmagic.nextfit.ui.login;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.palhackmagic.nextfit.MainActivity;
import com.palhackmagic.nextfit.R;
import com.palhackmagic.nextfit.SignUpActivity;
import com.palhackmagic.nextfit.data.Landing;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button signin = findViewById(R.id.signin);
        final Button signup = findViewById(R.id.signup);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);


//        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
//            @Override
//            public void onChanged(@Nullable LoginFormState loginFormState) {
//                if (loginFormState == null) {
//                    return;
//                }
//                signin.setEnabled(loginFormState.isDataValid());
//                if (loginFormState.getUsernameError() != null) {
//                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
//                }
//                if (loginFormState.getPasswordError() != null) {
//                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
//                }
//            }
//        });

//        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
//            @Override
//            public void onChanged(@Nullable LoginResult loginResult) {
//                if (loginResult == null) {
//                    return;
//                }
//                loadingProgressBar.setVisibility(View.GONE);
//                if (loginResult.getError() != null) {
//                    showLoginFailed(loginResult.getError());
//                }
////                if (loginResult.getSuccess() != null) {
////                    updateUiWithUser(loginResult.getSuccess());
////                }
//                setResult(Activity.RESULT_OK);
//
//                //Complete and destroy login activity once successful
//                finish();
//            }
//        });

//        usernameEditText.addTextChangedListener(afterTextChangedListener);
//        passwordEditText.addTextChangedListener(afterTextChangedListener);
//        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
////                    loginViewModel.login(usernameEditText.getText().toString(),
////                            passwordEditText.getText().toString());
//                }
//                return false;
//            }
//        });

//        signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadingProgressBar.setVisibility(View.VISIBLE);
////                loginViewModel.login(usernameEditText.getText().toString(),
////                        passwordEditText.getText().toString());
//            }
//        });

//        signup.setOnClickListener(new OnClickListener(){
//                //loadingProgressBar.setVisibility(View.VISIBLE);
//                public void onClick(View v){
//                    startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
//                }

                signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                }
        });

            //This is test for sign in button and going to the landing page
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Landing.class));
            }
        });
    }

//    private void showLoginFailed(@StringRes Integer errorString) {
//        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
//    }
}