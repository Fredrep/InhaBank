package com.example.shakhboz.inhabank.MixedActions;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shakhboz.inhabank.R;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Login extends AppCompatActivity {

    Button btnLogin, btnSignUp;
    TextView txtForget;
    EditText editUsername, editPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.loginBtn);
        btnSignUp = (Button) findViewById(R.id.signup);
        txtForget = (TextView) findViewById(R.id.forgotPass);
        editUsername = (EditText) findViewById(R.id.username);
        editPassword = (EditText) findViewById(R.id.password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                HelperFunctions helper = new HelperFunctions(Login.this);
//                String salt = helper.getSalt();
//                Log.e("SALT:::", salt);
            }
        });


    }
}
