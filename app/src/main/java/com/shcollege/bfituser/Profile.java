package com.shcollege.bfituser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.muddz.styleabletoast.StyleableToast;

public class Profile extends AppCompatActivity {

    private ImageView profilePicture;
    private TextView username;
    private TextView program;
    private TextView height;
    private TextView weight;
    private TextView dob;
    private TextView logout;
    private TextView edit;

    private FirebaseUser fUser;
    private String profileID;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Profile.this, MenuActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePicture = findViewById(R.id.imageView_profilePicture);
        username =  findViewById(R.id.textView_name);
        program = findViewById(R.id.textView_program);
        height = findViewById(R.id.textView_currentHeight);
        weight = findViewById(R.id.textView_currentWeight);
        dob = findViewById(R.id.textView_currentDOB);

        edit = findViewById(R.id.textView_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, EditProfile.class));
            }
        });

        logout = findViewById(R.id.textView_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                StyleableToast.makeText(Profile.this, "We miss you, come back soon!", R.style.customtoast).show();
                startActivity(new Intent(Profile.this, LoginActivity.class));
                finish();
            }
        });

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        profileID = fUser.getUid();

        setUsername();
        setDetails();
    }

    private void setUsername() {
        FirebaseDatabase.getInstance().getReference().child("User Credentials").child(profileID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserCredentials user = dataSnapshot.getValue(UserCredentials.class);
                username.setText(user.getFirstname() + " " + user.getLastname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setDetails() {
        FirebaseDatabase.getInstance().getReference().child("User Details").child(profileID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDetails user = dataSnapshot.getValue(UserDetails.class);
                height.setText(user.getHeight() + "cm");
                weight.setText(user.getWeight() + "kg");
                dob.setText(user.getDob());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}