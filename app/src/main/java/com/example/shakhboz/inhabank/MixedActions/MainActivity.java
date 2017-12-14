package com.example.shakhboz.inhabank.MixedActions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.shakhboz.inhabank.CheckInformation.Balance;
import com.example.shakhboz.inhabank.CheckInformation.TransactionHistory;
import com.example.shakhboz.inhabank.R;
import com.example.shakhboz.inhabank.Transactions.Deposit;
import com.example.shakhboz.inhabank.Transactions.Transfer;
import com.example.shakhboz.inhabank.Transactions.Withdraw;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView currentUsersRecyclerView;
    private ArrayList<CurrentUsers> currentUsersArrayList;
    Button deposit,withdraw ,transfer, history;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        deposit = (Button) findViewById(R.id.deposit_id);
        withdraw=(Button)findViewById(R.id.withrdaw_id);
        transfer = (Button) findViewById(R.id.transfer_id);
        history = (Button) findViewById(R.id.history_id);
        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Deposit.class);
                startActivity(intent);
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Withdraw.class);
                startActivity(intent);
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Transfer.class);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransactionHistory.class);
                startActivity(intent);
            }
        });


        currentUsersArrayList = new ArrayList<CurrentUsers>();
        CurrentUsers currentUsers = new CurrentUsers("Shakhboz");
        currentUsersArrayList.add(currentUsers);
        currentUsersRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        RecyclerView.LayoutManager currentUsersLayoutManager = new LinearLayoutManager(getApplicationContext());
        currentUsersRecyclerView.setLayoutManager(currentUsersLayoutManager);
        currentUsersRecyclerView.setAdapter(new CurrentUsersAdapter(this.currentUsersArrayList, MainActivity.this));

    }
}
