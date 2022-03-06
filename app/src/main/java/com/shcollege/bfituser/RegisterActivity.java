package com.shcollege.bfituser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;
import io.github.muddz.styleabletoast.StyleableToast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText firstName, lastName, emailRegister, passwordRegister;
    TextView already_login, register;

    private FirebaseAuth mAuth;

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
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        firstName = (EditText) findViewById(R.id.editText_registerFirstName);
        lastName = (EditText) findViewById(R.id.editText_registerLastName);
        emailRegister = (EditText) findViewById(R.id.editText_registerEmail);
        passwordRegister = (EditText) findViewById(R.id.editText_registerPassword);
        already_login = (TextView) findViewById(R.id.textView_alreadyLogin);
        already_login.setOnClickListener(this);
        register = (TextView) findViewById(R.id.textView_register);
        register.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_register:
                submitRegister();
                break;
            case R.id.textView_alreadyLogin:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
        }
    }

    private void submitRegister() {
        String firstname = firstName.getText().toString().trim();
        String lastname = lastName.getText().toString().trim();
        String email = emailRegister.getText().toString().trim();
        String password = passwordRegister.getText().toString().trim();

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
        if (email.isEmpty()){
            emailRegister.setError("This field is required");
            emailRegister.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailRegister.setError("Please provide a valid email");
            emailRegister.requestFocus();
            return;
        }
        if (password.isEmpty()){
            passwordRegister.setError("This field is required");
            passwordRegister.requestFocus();
            return;
        }
        if(password.length() < 6){
            passwordRegister.setError("Minimum password length should be 6 characters");
            passwordRegister.requestFocus();
            return;
        }
        {
            mAuth.fetchSignInMethodsForEmail(emailRegister.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean check = !task.getResult().getSignInMethods().isEmpty();
                            if(check)
                            {
                                StyleableToast.makeText(getApplicationContext(), "Email already exists", R.style.customtoast).show();
                            }
                        }
                    });
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(firstname, lastname, email, password);
                    FirebaseDatabase.getInstance().getReference("User")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {


                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();

                                if (User.isEmailVerified()) {
                                    startActivity(new Intent(RegisterActivity.this, CompleteYourProfile.class));
                                }
                                else {
                                    User.sendEmailVerification();
                                    StyleableToast.makeText(RegisterActivity.this, "Check your email to verify your account", R.style.customtoast).show();
                                    startActivity(new Intent(RegisterActivity.this, CompleteYourProfile.class));
                                }
                                if(!task.isSuccessful())
                                {
                                    StyleableToast.makeText(RegisterActivity.this, "Failed to register", R.style.customtoast).show();
                                }
                            }
                        }

                    });
                }
            }
        });
    }
}