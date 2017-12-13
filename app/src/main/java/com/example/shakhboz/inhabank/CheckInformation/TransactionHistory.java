package com.example.shakhboz.inhabank.CheckInformation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.shakhboz.inhabank.R;

public class TransactionHistory extends AppCompatActivity {

    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        container = (LinearLayout) findViewById(R.id.containerLL);





    }
}
