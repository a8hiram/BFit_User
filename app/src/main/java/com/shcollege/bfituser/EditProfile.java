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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    private ImageView profilePicture;
    private EditText firstName;
    private EditText lastName;
    private AutoCompleteTextView gender;
    private EditText dob;
    private EditText weight;
    private EditText height;
    private TextView save;

    private FirebaseUser fUser;
    private String profileID;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditProfile.this, Profile.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profilePicture = findViewById(R.id.imageView_editProfilePicture);
        firstName = findViewById(R.id.editText_editFirstName);
        lastName = findViewById(R.id.editText_editLastName);
        gender = findViewById(R.id.textView_editGender);
        dob = findViewById(R.id.editText_editDOB);
        weight = findViewById(R.id.editText_editYourWeight);
        height = findViewById(R.id.editText_editYourHeight);
        save = findViewById(R.id.textView_editSave);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        profileID = fUser.getUid();

        getName();
        getDetails();

        save.setOnClickListener(this);

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
                new DatePickerDialog(EditProfile.this,R.style.MyDatePickerDialogTheme,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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

    private void getName() {
        FirebaseDatabase.getInstance().getReference().child("User Credentials").child(profileID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserCredentials user = dataSnapshot.getValue(UserCredentials.class);
                firstName.setText(user.getFirstname());
                lastName.setText(user.getLastname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDetails() {
        FirebaseDatabase.getInstance().getReference().child("User Details").child(profileID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDetails user = dataSnapshot.getValue(UserDetails.class);
                height.setText(user.getHeight());
                weight.setText(user.getWeight());
                dob.setText(user.getDob());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_editSave:
                editProfilePicture();
                editName();
                editDetails();
                break;
        }
    }

    private void editProfilePicture() {

    }

    private void editName() {
        String firstname = firstName.getText().toString().trim();
        String lastname = lastName.getText().toString().trim();

        if (firstname.isEmpty()){
            firstName.setError("This field is required");
            firstName.requestFocus();
            return;
        }
        if (lastname.isEmpty()){
            lastName.setError("This field is required");
            lastName.requestFocus();
            return;
        }
    }

    private void editDetails() {
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
    }
}