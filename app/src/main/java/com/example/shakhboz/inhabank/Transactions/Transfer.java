package com.example.shakhboz.inhabank.Transactions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.shakhboz.inhabank.R;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transfer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss");
        String currentDateTime = sdf.format(new Date());
        TextView tr_date=findViewById(R.id.transfer_date);
        tr_date.setText(currentDateTime);
    }
}
