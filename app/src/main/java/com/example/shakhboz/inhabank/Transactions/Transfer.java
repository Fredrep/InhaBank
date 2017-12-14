package com.example.shakhboz.inhabank.Transactions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shakhboz.inhabank.MixedActions.HelperFunctions;
import com.example.shakhboz.inhabank.R;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transfer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Intent main=getIntent();
        String usr=main.getStringExtra("username");
        String pass=main.getStringExtra("password");
        final HelperFunctions ob=new HelperFunctions(getApplicationContext());
        Button submit=findViewById(R.id.submit_btn);
        final TextView transferred_from_user=findViewById(R.id.transfer_from_user);
        transferred_from_user.setText(usr);
        final TextView tr_date=findViewById(R.id.transfer_date);
        final EditText amount=findViewById(R.id.transfer_amount);
        final EditText transferred_to_user=findViewById(R.id.transfer_to_user);

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
        final String currentDateTime = sdf.format(new Date());
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tr_date.setText(currentDateTime);
                final String data=transferred_from_user.getText().toString()+"\n"+amount.toString()+"\n"+currentDateTime;
                final String file_name=transferred_to_user.getText().toString()+".txt";
                    if(!transferred_to_user.getText().toString().isEmpty()&& !amount.getText().toString().isEmpty()) {
                        ob.writeToFile(data, file_name);
                        
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please, fill all element!!!",Toast.LENGTH_LONG).show();
                    }
            }
        });
    }

}
