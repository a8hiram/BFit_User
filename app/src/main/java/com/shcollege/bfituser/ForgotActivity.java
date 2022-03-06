package com.shcollege.bfituser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import io.github.muddz.styleabletoast.StyleableToast;

public class ForgotActivity extends AppCompatActivity {

    EditText forgotemail;
    TextView resetpassword;

    FirebaseAuth auth;

    @Override
    public void onBackPressed() {
        Intent j = new Intent(ForgotActivity.this, LoginActivity.class);
        startActivity(j);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        forgotemail = findViewById(R.id.editText_forgotEmail);
        resetpassword = findViewById(R.id.textView_resetPassword);

        auth = FirebaseAuth.getInstance();
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void reset() {
        String email = forgotemail.getText().toString().trim();

        if (email.isEmpty()){
            forgotemail.setError("Email is required");
            forgotemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forgotemail.setError("Please provide a valid email");
            forgotemail.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    StyleableToast.makeText(ForgotActivity.this, "Check your email to reset your password", Toast.LENGTH_SHORT,R.style.customtoast).show();
                }
                else {
                    StyleableToast.makeText(ForgotActivity.this, "Try again, Something went wrong", Toast.LENGTH_SHORT, R.style.customtoast).show();
                }
            }
        });
    }
}