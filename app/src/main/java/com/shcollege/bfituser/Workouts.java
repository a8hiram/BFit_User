package com.shcollege.bfituser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class Workouts extends AppCompatActivity {

    RecyclerView workoutsRecyclerView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Workouts.this, MenuActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        workoutsRecyclerView = findViewById(R.id.recyclerView_workouts);
    }
}