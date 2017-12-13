package com.example.shakhboz.inhabank.MixedActions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shakhboz.inhabank.CheckInformation.TransactionHistory;
import com.example.shakhboz.inhabank.R;
import com.example.shakhboz.inhabank.Transactions.Deposit;
import com.example.shakhboz.inhabank.Transactions.Transfer;
import com.example.shakhboz.inhabank.Transactions.Withdraw;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView currentUsersRecyclerView;
    private ArrayList<String> usernames;
    private ArrayList<User> currentUsersArrayList;
    Button deposit,withdraw ,transfer, history;
    private TextView currentBalance;
    HelperFunctions helper;

    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        deposit = (Button) findViewById(R.id.deposit_id);
        withdraw=(Button)findViewById(R.id.withrdaw_id);
        transfer = (Button) findViewById(R.id.transfer_id);
        history = (Button) findViewById(R.id.history_id);

        currentBalance = (TextView) findViewById(R.id.current_balance_id);

        intent = getIntent();
        helper = new HelperFunctions(getApplicationContext());
        currentUsersArrayList = new ArrayList<User>();
        final String username = intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");



        ArrayList<String> tempInfo = helper.readFromFile(username + ".txt");
        if(tempInfo.size() != 0){
            currentBalance.setText("$" + tempInfo.get(1));
        }else{
            currentBalance.setText("$0");
        }


        Log.i("username + password",username+password);


        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Deposit.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Withdraw.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Transfer.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransactionHistory.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });



        HelperFunctions helper = new HelperFunctions(getApplicationContext());
        usernames = helper.readFromFile(HelperFunctions.usernamesFileName);
        ArrayList<String> usernames = helper.readFromFile(HelperFunctions.usernamesFileName);
        Log.e("SIZE:::", usernames.size() + "");
        for(int i = 0; i < currentUsersArrayList.size(); i++){
            User user = new User(usernames.get(i));
            Log.e("USERNAMES:::", usernames.get(i));
            currentUsersArrayList.add(user);
        }


       User user = new User("Shakhboz");
      currentUsersArrayList.add(user);
        currentUsersRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        RecyclerView.LayoutManager currentUsersLayoutManager = new LinearLayoutManager(getApplicationContext());
        currentUsersRecyclerView.setLayoutManager(currentUsersLayoutManager);
        currentUsersRecyclerView.setAdapter(new CurrentUsersAdapter(username, this.usernames, MainActivity.this));

    }
}
