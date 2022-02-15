package com.shcollege.bfituser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText emailLogin, passwordLogin;
    TextView forgotPassword, login, register;
    ImageView googleIcon, facebookIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = (EditText) findViewById(R.id.editText_email);
        passwordLogin = (EditText) findViewById(R.id.editText_password);
        forgotPassword = (TextView) findViewById(R.id.textView_forgotPassword);
        forgotPassword.setPaintFlags(forgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        login = (TextView) findViewById(R.id.textView_login);
        register = (TextView) findViewById(R.id.textView_register);
        googleIcon = (ImageView) findViewById(R.id.imageView_google);
        facebookIcon = (ImageView) findViewById(R.id.imageView_facebook);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
            }
        });
    }
}