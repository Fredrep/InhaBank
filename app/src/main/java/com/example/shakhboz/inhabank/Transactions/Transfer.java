package com.example.shakhboz.inhabank.Transactions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shakhboz.inhabank.MixedActions.HelperFunctions;
import com.example.shakhboz.inhabank.R;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transfer extends AppCompatActivity {
        HelperFunctions ob=new HelperFunctions(getApplicationContext());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button submit=findViewById(R.id.submit_btn);
        TextView transfering_from_user=findViewById(R.id.transfer_from_user);
        TextView tr_date=findViewById(R.id.transfer_date);
        EditText amount=findViewById(R.id.transfer_amount);
        final EditText transfering_to_user=findViewById(R.id.transfer_to_user);
        setContentView(R.layout.activity_transfer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss");
        String currentDateTime = sdf.format(new Date());
        tr_date.setText(currentDateTime);
       final String data=transfering_from_user.getText().toString()+"\n"+amount.toString()+"\n"+currentDateTime;
             final String file_name=transfering_to_user.getText().toString()+".txt";
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    ob.writeToFile(data,file_name);
            }
        });
    }


}
