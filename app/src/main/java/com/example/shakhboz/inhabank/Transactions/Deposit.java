package com.example.shakhboz.inhabank.Transactions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shakhboz.inhabank.R;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Deposit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        Intent depositIntent = getIntent();

        Button depositButton;
        Button showbtn;
        final EditText inputDepositText;
        final TextView txtview;

        depositButton = (Button)findViewById(R.id.depositButtonAction);
        showbtn = (Button)findViewById(R.id.depositShow);
        inputDepositText = (EditText)findViewById(R.id.depositInputEditText);
        txtview = (TextView)findViewById(R.id.showBalanceText);
        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintStream output = null;
                try {
                    output = new PrintStream(
                            openFileOutput("out.log",
                                    MODE_PRIVATE));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                output.println(inputDepositText.getText());
                output.close();
            }
        });

        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Scanner scan = new Scanner(
                            openFileInput("out.log"));
                    String allText = "";   // read entire file
                    while (scan.hasNextLine()) {
                        String line = scan.nextLine();
                        allText += line;
                    }
                    txtview.setText(allText);
                    scan.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
