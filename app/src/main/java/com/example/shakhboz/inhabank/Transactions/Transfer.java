package com.example.shakhboz.inhabank.Transactions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shakhboz.inhabank.R;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transfer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button submit=findViewById(R.id.submit_btn);
        TextView tr_date=findViewById(R.id.transfer_date);
        EditText amount=findViewById(R.id.transfer_amount);
        EditText transfering_user=findViewById(R.id.transfer_to_user);
        setContentView(R.layout.activity_transfer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss");
        String currentDateTime = sdf.format(new Date());
        tr_date.setText(currentDateTime);

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
    }


}
