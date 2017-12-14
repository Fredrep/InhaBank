package com.example.shakhboz.inhabank.MixedActions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shakhboz.inhabank.R;

public class Signup extends AppCompatActivity {

    Button btnLogin, btnSignUp;
    TextView txtForget;
    EditText editUsername, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnLogin = (Button) findViewById(R.id.btnLogIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        txtForget = (TextView) findViewById(R.id.haveAccount);
        editUsername = (EditText) findViewById(R.id.usernameSignUp);
        editPassword = (EditText) findViewById(R.id.passwordSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                HelperFunctions helper = new HelperFunctions(Signup.this);
                if(!username.isEmpty()){
                    if(password.isEmpty()){
                        if(helper.isUsernameAvailable(username)){

                        }else{
                            Toast.makeText(Signup.this, "This username is already exists", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Signup.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Signup.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup.this, Login.class);
                startActivity(i);
            }
        });


    }
}
