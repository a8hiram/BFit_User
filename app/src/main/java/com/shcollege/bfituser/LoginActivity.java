package com.shcollege.bfituser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.muddz.styleabletoast.StyleableToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailLogin, passwordLogin;
    TextView forgotPassword, login, no_acc_register;

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
        setContentView(R.layout.activity_login);

        emailLogin = (EditText) findViewById(R.id.editText_loginEmail);
        passwordLogin = (EditText) findViewById(R.id.editText_loginPassword);
        forgotPassword = (TextView) findViewById(R.id.textView_forgotPassword);
        forgotPassword.setPaintFlags(forgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        forgotPassword.setOnClickListener(this);
        login = (TextView) findViewById(R.id.textView_login);
        login.setOnClickListener(this);
        no_acc_register = (TextView) findViewById(R.id.textView_noaccRegister);
        no_acc_register.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_login:
                submitLogin();
                break;
            case R.id.textView_noaccRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.textView_forgotPassword:
                startActivity(new Intent(this, ForgotActivity.class));
                break;
        }
    }

    private void submitLogin() {
        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if(email.isEmpty()){
            emailLogin.setError("This field is required");
            emailLogin.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLogin.setError("Please provide a valid email");
            emailLogin.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordLogin.setError("This field is required");
            passwordLogin.requestFocus();
            return;
        }
        if(password.length() < 6){
            passwordLogin.setError("Minimum password length should be 6 characters");
            passwordLogin.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();

                            if (User.isEmailVerified()) {
                                StyleableToast.makeText(getApplicationContext(), "Start grinding now!", R.style.customtoast).show();
                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }else {
                            StyleableToast.makeText(LoginActivity.this, "Failed to sign in! Please check your credentials", R.style.customtoast).show();
                        }
                    }
                });
    }
}