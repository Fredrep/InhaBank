package com.example.shakhboz.inhabank.MixedActions;

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

import java.util.ArrayList;

public class Signup extends AppCompatActivity {

    Button btnSignUp;
    TextView txtSignIn;
    EditText editUsername, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        txtSignIn = (TextView) findViewById(R.id.haveAccount);
        editUsername = (EditText) findViewById(R.id.usernameSignUp);
        editPassword = (EditText) findViewById(R.id.passwordSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                HelperFunctions helper = new HelperFunctions(getApplicationContext());
                if(!username.isEmpty()){
                    if(!password.isEmpty()){
                        if(!helper.isUsernameExist(username)){
                            String hashedPassw = helper.getSHA128Password(password);
                            helper.appendToFile(username, HelperFunctions.usernamesFileName);
                            helper.appendToFile(hashedPassw, HelperFunctions.hashedPasswords);

                            ArrayList<String> usernames = helper.readFromFile(HelperFunctions.usernamesFileName);
                            ArrayList<String> passwords = helper.readFromFile(HelperFunctions.hashedPasswords);
                            for(int i = 0; i < usernames.size(); i++){
                                Log.e("USERNAME::", usernames.get(i));
                                Log.e("PASSWORD::", passwords.get(i));
                            }


                            Intent intent = new Intent(Signup.this, MainActivity.class);
                            intent.putExtra("username", username);
                            intent.putExtra("password", password);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
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

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup.this, Login.class);
                startActivity(i);
            }
        });


    }
}
