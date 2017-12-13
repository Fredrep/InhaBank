package com.example.shakhboz.inhabank.MixedActions;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shakhboz.inhabank.R;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Login extends AppCompatActivity {

    Button btnLogin, btnSignUp;
    TextView txtForget;
    EditText editUsername, editPassword;
    HelperFunctions helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.loginBtn);
        btnSignUp = (Button) findViewById(R.id.signup);
        txtForget = (TextView) findViewById(R.id.forgotPass);
        editUsername = (EditText) findViewById(R.id.username);
        editPassword = (EditText) findViewById(R.id.password);
        helper = new HelperFunctions(getApplicationContext());

        try {
            File usernamesFile = new File(HelperFunctions.usernamesFileName);
            File passwFile = new File(HelperFunctions.hashedPasswords);
            usernamesFile.createNewFile();
            passwFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                ArrayList<String> tempInfo = helper.readFromFile(username+".txt");

            

                String password = editPassword.getText().toString();
                String hashedPassw = helper.getSHA128Password(password);
                if(!username.isEmpty()){
                    if(!password.isEmpty()){
                        if(helper.isUsernameExist(username)){
                            if(helper.isPasswordExist(hashedPassw)) {
//                                ArrayList<String> usernames = helper.readFromFile(HelperFunctions.usernamesFileName);
//                                ArrayList<String> passwords = helper.readFromFile(HelperFunctions.hashedPasswords);
//                                for(int i = 0; i < usernames.size(); i++){
//                                    Log.e("USERNAME::", usernames.get(i));
//                                    Log.e("PASSWORD::", passwords.get(i));
//                                }

                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("username", username);
                                intent.putExtra("password", password);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(Login.this, "Username is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Login.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Login.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });

        txtForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
