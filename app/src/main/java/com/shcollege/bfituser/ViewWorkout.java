package com.shcollege.bfituser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media2.widget.VideoView;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewWorkout extends AppCompatActivity {

    private TextView workoutCategory;
    private VideoView workoutVideo;
    private TextView workoutName;
    private TextView workoutLevel;
    private TextView workoutDescription;
    private TextView workoutInstructor;
    private TextView workoutDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout);

        workoutName = findViewById(R.id.textView_workoutName);
        workoutVideo = findViewById(R.id.videoView_workout);
        workoutCategory = findViewById(R.id.textView_categoryName);
        workoutLevel = findViewById(R.id.textView_level);
        workoutDescription = findViewById(R.id.textView_descriptionDetail);
        workoutInstructor = findViewById(R.id.textView_instructorName);
        workoutDuration = findViewById(R.id.textView_durationTime);
    }
}