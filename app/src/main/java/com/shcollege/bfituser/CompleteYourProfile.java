package com.shcollege.bfituser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CompleteYourProfile extends AppCompatActivity {

    TextView gender, dob, weightUnit, heightUnit, next;
    EditText weight, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_your_profile);

        gender = (TextView) findViewById(R.id.textView_gender);
        dob = (TextView) findViewById(R.id.textView_dob);
        weightUnit = (TextView) findViewById(R.id.textView_weightUnit);
        heightUnit = (TextView) findViewById(R.id.textView_heightUnit);
        next = (TextView) findViewById(R.id.textView_next);
        weight = (EditText) findViewById(R.id.editText_yourWeight);
        height = (EditText) findViewById(R.id.editText_yourHeight);
    }
}