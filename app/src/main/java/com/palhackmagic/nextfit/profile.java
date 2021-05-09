package com.palhackmagic.nextfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.palhackmagic.nextfit.data.Landing;
import com.palhackmagic.nextfit.ui.login.LoginActivity;

public class profile extends AppCompatActivity {

    Button more;
    Button logout;
    TextView user_fname, user_lname, user_dob, user_age, user_fullName, user_steps, user_gender, user_height;
    FirebaseAuth mAuth;
    DatabaseReference mref;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user_fname = findViewById(R.id.firstN);
        user_lname = findViewById(R.id.lastN);
        user_fullName = findViewById(R.id.fullN);
        user_age = findViewById(R.id.userage);
        user_dob = findViewById(R.id.dateofB);
        user_gender = findViewById(R.id.gend);
        user_height = findViewById(R.id.ht);
        user_steps = findViewById(R.id.avg);
        more = findViewById(R.id.seemore);
        logout = findViewById(R.id.logout);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("fitbit");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(profile.this, LoginActivity.class));
                finish();
            }
        });

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fullName = snapshot.child("displayName").getValue().toString();
                String firstName = snapshot.child("firstName").getValue().toString();
                String lastName = snapshot.child("lastName").getValue().toString();
                String dateofbirth = snapshot.child("dateOfBirth").getValue().toString();
                String age = snapshot.child("age").getValue().toString();
                String averagesteps = snapshot.child("averageDailySteps").getValue().toString();
                String gender = snapshot.child("gender").getValue().toString();
                String height = snapshot.child("height").getValue().toString();

                user_fullName.setText(fullName);
                user_fname.setText(firstName);
                user_lname.setText(lastName);
                user_dob.setText(dateofbirth);
                user_age.setText(age);
                user_steps.setText(averagesteps);
                user_gender.setText(gender);
                user_height.setText(height);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast. makeText(profile.this, "Error Loading User Profile" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}