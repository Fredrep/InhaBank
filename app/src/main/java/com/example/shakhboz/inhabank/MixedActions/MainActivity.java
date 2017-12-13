package com.example.shakhboz.inhabank.MixedActions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.shakhboz.inhabank.CheckInformation.Balance;
import com.example.shakhboz.inhabank.CheckInformation.TransactionHistory;
import com.example.shakhboz.inhabank.CheckInformation.Transactions;
import com.example.shakhboz.inhabank.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView currentUsersRecyclerView;
    private ArrayList<CurrentUsers> currentUsersArrayList;
    Button balance, transactions, history;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        balance = (Button) findViewById(R.id.balance_id);
        transactions = (Button) findViewById(R.id.transactions_id);
        history = (Button) findViewById(R.id.history_id);
        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Balance.class);
                startActivity(intent);
            }
        });
        transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Transactions.class);
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
        currentUsersRecyclerView.setAdapter(new CurrentUsersApapter(this.currentUsersArrayList, MainActivity.this));

    }
}
