package com.shcollege.bfituser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WorkoutFinishedActivity extends AppCompatActivity {

    private TextView backToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_finished);

        backToHome = findViewById(R.id.textView_backToHome);
    }
}