package com.palhackmagic.nextfit.data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.palhackmagic.nextfit.R;
import com.palhackmagic.nextfit.SignUpActivity;
import com.palhackmagic.nextfit.profile;
import com.palhackmagic.nextfit.ui.login.LoginActivity;

public class Landing extends AppCompatActivity {

    Button button1;
    Button logout;
    Button prof;
    TextView user_name, user_email, user_phone;
    FirebaseAuth mAuth;
    DatabaseReference mref;
    String userId;

    String url = "https://www.fitbit.com/oauth2/authorize?" +
            "response_type=token" +
            "&client_id=22C569" +
            "&redirect_uri=nextfit://logincallback" +
            "&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight" +
            "&expires_in=3625860";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        mAuth = FirebaseAuth.getInstance();

        button1= findViewById(R.id.button);
        logout= findViewById(R.id.signout);
        prof = findViewById(R.id.profile);
        user_name = findViewById(R.id.fName);
        user_email = findViewById(R.id.email);
        user_phone = findViewById(R.id.phoneNum);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = snapshot.child("email").getValue().toString();
                String fName = snapshot.child("fname").getValue().toString();
                String phone = snapshot.child("phoneNumber").getValue().toString();

                user_name.setText(fName);
                user_phone.setText(phone);
                user_email.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast. makeText(Landing.this, "Error Loading User Profile" , Toast.LENGTH_SHORT).show();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder customTabIntent = new CustomTabsIntent.Builder();
                openCustomTabs(Landing.this,customTabIntent.build(), Uri.parse(url));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Landing.this, LoginActivity.class));
                finish();
            }
        });

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), profile.class));
            }
        });
    }

    public static void openCustomTabs(Activity activity, CustomTabsIntent customTabsIntent, Uri uri){
        String packageName = "com.android.chrome";
        if (packageName!=null){
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity,uri);
        }
        else{
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity,uri);
//            activity.startActivity(new Intent(Intent.ACTION_VIEW));
        }
    }}