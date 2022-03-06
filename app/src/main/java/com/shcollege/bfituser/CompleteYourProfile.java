package com.shcollege.bfituser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import io.github.muddz.styleabletoast.StyleableToast;

public class CompleteYourProfile extends AppCompatActivity implements View.OnClickListener {

    TextView  weightUnit, heightUnit, next;
    EditText dob, weight, height;
    AutoCompleteTextView gender;

    final Calendar myCalendar = Calendar.getInstance();

    private FirebaseAuth fUser;

    private long backPressedTime;
    private StyleableToast backToast;

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
        setContentView(R.layout.activity_complete_your_profile);

        fUser = FirebaseAuth.getInstance();

        gender = (AutoCompleteTextView) findViewById(R.id.textView_gender);
        dob = (EditText) findViewById(R.id.editText_dob);
        weightUnit = (TextView) findViewById(R.id.textView_weightUnit);
        heightUnit = (TextView) findViewById(R.id.textView_heightUnit);
        next = (TextView) findViewById(R.id.textView_next);
        next.setOnClickListener(this);
        weight = (EditText) findViewById(R.id.editText_yourWeight);
        height = (EditText) findViewById(R.id.editText_yourHeight);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CompleteYourProfile.this,R.style.MyDatePickerDialogTheme,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, getResources()
                .getStringArray(R.array.Gender_Names));
        final String[] selection = new String[1];
        gender.setAdapter(arrayAdapter);
        gender.setCursorVisible(false);
        gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gender.showDropDown();
                selection[0] = (String) parent.getItemAtPosition(position);
            }
        });

        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                gender.showDropDown();
            }
        });
    }

    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_next:
                submitDetails();
                break;
        }
    }

    private void submitDetails() {
        String setGender = gender.getText().toString().trim();
        String setDOB = dob.getText().toString().trim();
        String setWeight = weight.getText().toString().trim();
        String setHeight = height.getText().toString().trim();

        if (setGender.isEmpty()){
            gender.setError("This field is required");
            gender.requestFocus();
            return;
        }
        if (setDOB.isEmpty()){
            dob.setError("This field is required");
            dob.requestFocus();
            return;
        }
        if (setWeight.isEmpty()){
            weight.setError("This field is required");
            weight.requestFocus();
            return;
        }
        if (setHeight.isEmpty()){
            height.setError("This field is required");
            height.requestFocus();
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("gender", setGender);
        map.put("dob", setDOB);
        map.put("weight", setWeight);
        map.put("height", setHeight);
    }
}