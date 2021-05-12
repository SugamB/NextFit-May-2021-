package com.palhackmagic.nextfit.data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.palhackmagic.nextfit.R;
import com.palhackmagic.nextfit.SignUpActivity;
import com.palhackmagic.nextfit.data.model.Steps;
import com.palhackmagic.nextfit.profile;
import com.palhackmagic.nextfit.ui.StepsGraph;
import com.palhackmagic.nextfit.ui.StepsUI;
import com.palhackmagic.nextfit.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Landing extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button logout;
    Button prof;
    TextView user_name, user_email, user_phone;
    FirebaseAuth mAuth;
    DatabaseReference mref;
    String userId;
    BottomNavigationView bottomNavigationView;
    public ArrayList<Steps> stepsArrayList = new ArrayList<>();
    // indexShown keeps tracks what item is shown so that item in navigation bar can be checked

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    int indexShown = 1;

    String url = "https://www.fitbit.com/oauth2/authorize?" +
            "response_type=token" +
            "&client_id=22C569" +
            "&redirect_uri=nextfit://logincallback" +
            "&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight" +
            "&expires_in=3625860";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        final Fragment stepsUI = new StepsUI();
        final Fragment stepsGraph = new StepsGraph();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.stepsFragment, stepsUI, "stepsUI");
        fragmentTransaction.commit();

        mref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        /*
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
        removed this section to make room for stepsUI
         */

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.syncFitbit :
                        CustomTabsIntent.Builder customTabIntent = new CustomTabsIntent.Builder();
                        openCustomTabs(Landing.this,customTabIntent.build(), Uri.parse(url));
                        return true;
                    case R.id.home :
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.stepsFragment, stepsUI, "stepsUI");
                        fragmentTransaction.commit();
                        indexShown = 1;
                        return true;
                    case R.id.graph :
                        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                        fragmentTransaction1.replace(R.id.stepsFragment, stepsGraph, "stepsGraph");
                        fragmentTransaction1.commit();
                        indexShown = 2;
                        return true;
                }
                return false;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.getMenu().getItem(indexShown).setChecked(true);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logoutItem :
                mAuth.signOut();
                startActivity(new Intent(Landing.this, LoginActivity.class));
                finish();
                return true;
            case R.id.profileItem :
                startActivity(new Intent(getApplicationContext(), profile.class));
                return true;
        }

        return false;
    }
}
