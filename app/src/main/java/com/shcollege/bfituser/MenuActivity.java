package com.shcollege.bfituser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.muddz.styleabletoast.StyleableToast;

public class  MenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private long backPressedTime;
    private StyleableToast backToast;

    private TextView welcomeUser;
    private TextView bmiNumber;
    private TextView bmiStatus;
    private int weight;
    private float heightcm, heightm, bmi;
    private String bmiValue;
    private FirebaseUser fUser;
    private String profileID;

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            finishAffinity();
            return;
        } else {
            backToast = StyleableToast.makeText(getBaseContext(), "Press back again to exit", R.style.customtoast);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        bmiNumber = (TextView) findViewById(R.id.textView_bmiNumber);
        bmiStatus = (TextView) findViewById(R.id.textView_bmiStatus);
        welcomeUser = (TextView) findViewById(R.id.username);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        profileID = fUser.getUid();

        helloUser();
        calculateBMI();
        setBottomNavigationView();
    }

    private void setBottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.workouts:
                        startActivity(new Intent(MenuActivity.this, Workouts.class));
                        break;
                    case R.id.meal_planner:
                        startActivity(new Intent(MenuActivity.this, Meals.class));
                        break;
                    case R.id.profile:
                        startActivity(new Intent(MenuActivity.this, Profile.class));
                        break;
                }
                return true;
            }
        });
    }

    private void helloUser() {
        FirebaseDatabase.getInstance().getReference().child("User Credentials").child(profileID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserCredentials user = dataSnapshot.getValue(UserCredentials.class);
                welcomeUser.setText(user.getFirstname() + " " + user.getLastname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateBMI() {
        FirebaseDatabase.getInstance().getReference().child("User Details").child(profileID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDetails user = dataSnapshot.getValue(UserDetails.class);
                heightcm = Integer.parseInt(user.getHeight());
                heightm = (heightcm / 100);
                weight = Integer.parseInt(user.getWeight());
                bmi = (weight / (heightm*heightm));
                bmiValue = String.format("%.2f", bmi);
                bmiNumber.setText(bmiValue);

                if (bmi < 18.5)
                    bmiStatus.setText("You are underweight");
                else if (bmi > 18.5 && bmi < 24.9)
                    bmiStatus.setText("You have a healthy weight");
                else if (bmi > 25 && bmi < 29.9)
                    bmiStatus.setText("You are overweight");
                else if (bmi > 30)
                    bmiStatus.setText("You are obese");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}