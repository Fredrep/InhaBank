package com.example.shakhboz.inhabank.CheckInformation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shakhboz.inhabank.R;
import com.example.shakhboz.inhabank.Transactions.Deposit;
import com.example.shakhboz.inhabank.Transactions.Transfer;
import com.example.shakhboz.inhabank.Transactions.Withdraw;

public class Transactions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        Button toDepositButton;
        Button toWithdrawButton;
        Button toTransferButton;


        toDepositButton = (Button)findViewById(R.id.deposit_id);
        toWithdrawButton = (Button)findViewById(R.id.withdraw_id);
        toTransferButton = (Button)findViewById(R.id.transfer_id);


        toDepositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent depositIntent = new Intent(Transactions.this, Deposit.class);
                startActivity(depositIntent);
            }
        });

        toWithdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent withdrawIntent = new Intent(Transactions.this, Withdraw.class);
                startActivity(withdrawIntent);
            }
        });

        toTransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transferIntent = new Intent(Transactions.this, Transfer.class);
                startActivity(transferIntent);
            }
        });

    }
}
