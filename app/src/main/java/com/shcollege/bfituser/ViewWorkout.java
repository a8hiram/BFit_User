package com.shcollege.bfituser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media2.widget.VideoView;

import android.os.Bundle;
import android.widget.TextView;

public class ViewWorkout extends AppCompatActivity {

    private TextView workoutName;
    private VideoView workoutVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout);

        workoutName = findViewById(R.id.textView_workoutName);
        workoutVideo = findViewById(R.id.videoView_workout);
    }
}