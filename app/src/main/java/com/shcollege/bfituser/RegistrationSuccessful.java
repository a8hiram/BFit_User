package com.shcollege.bfituser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.muddz.styleabletoast.StyleableToast;

public class RegistrationSuccessful extends AppCompatActivity {

    private TextView goToLogin;
    private TextView welcomeUsername;

    private long backPressedTime;
    private StyleableToast backToast;

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
        setContentView(R.layout.activity_registration_successful);

        welcomeUsername = (TextView) findViewById(R.id.textView_welcome);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        profileID = fUser.getUid();
        goToLogin = (TextView) findViewById(R.id.textView_goToLogin);

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationSuccessful.this, LoginActivity.class));
            }
        });

        welcomeUser();
    }

    private void welcomeUser() {
        FirebaseDatabase.getInstance().getReference().child("User Credentials").child(profileID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserCredentials user = dataSnapshot.getValue(UserCredentials.class);
                welcomeUsername.setText("Welcome, "+ user.getFirstname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}