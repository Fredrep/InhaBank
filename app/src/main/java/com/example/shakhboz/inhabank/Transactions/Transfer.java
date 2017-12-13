package com.example.shakhboz.inhabank.Transactions;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shakhboz.inhabank.MixedActions.HelperFunctions;
import com.example.shakhboz.inhabank.MixedActions.MainActivity;
import com.example.shakhboz.inhabank.R;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class Transfer extends AppCompatActivity {
    private String usr;
    private  String pass;
    private byte[] key;
    private HelperFunctions helper;

    Intent main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        main=getIntent();
         usr=main.getStringExtra("username");
         pass=main.getStringExtra("password");
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

                        String curr = retBalance(usr);
                        int currentBalance = Integer.parseInt(curr);
                        int minusBalance = Integer.parseInt(amount.getText().toString());

                        ArrayList<String> usernames = helper.readFromFile(HelperFunctions.usernamesFileName);

                        if(usernames.contains(transferred_to_user.getText().toString())) {
                            if ((currentBalance - minusBalance) > 0) {
                                int finalBalance = currentBalance - minusBalance;
                                String finalMoney = String.valueOf(finalBalance);
                                String addingValue = String.valueOf(minusBalance);
                                String type = "Transfer to: ";
                                String dateTime = currentDateTime;
                                String encryptData = finalMoney + "/" + type + transferred_to_user.getText().toString() + "/" + addingValue + "/" + dateTime;
                                String encryptedFinalData = encryptFile(encryptData);
                                String fname = usr + ".log";
                                helper.appendToFile(encryptedFinalData, fname);
                                ob.writeToFile(data, file_name);
                                Intent intent = new Intent(Transfer.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "You don't have enough money!!", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "There is no any user!!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please, fill all element!!!",Toast.LENGTH_LONG).show();
                    }
            }
        });
    }
    public String retBalance(String username){
        String fname = username + ".log";
        helper = new HelperFunctions(getApplicationContext());
        ArrayList<String> userslog = helper.readFromFile(fname);
        String balance;
        if(userslog.size()>=1){
            String tmp = userslog.get(userslog.size()-1);
            Log.e("FROMFILE::", tmp);
            String alldata = decryptFile(tmp);
            String delims = "[/]";
            String[] logs = alldata.split(delims);
            balance = logs[0];
        }
        else{
            balance="0";
        }

        return balance;
    }
    public byte[] generateKeyWithPswd(String myPassword)
    {
        myPassword=myPassword + helper.getSalt();
        MessageDigest sha = null;
        try {
            key = myPassword.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            return key;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public String encryptFile(String input){
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(generateKeyWithPswd(usr), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] utf8EncryptedData = cipher.doFinal(input.getBytes());
            String encryptedString = new BASE64Encoder().encode(utf8EncryptedData);
            return encryptedString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @TargetApi(Build.VERSION_CODES.O)
    public String decryptFile(String input){
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            SecretKeySpec secretKey = new SecretKeySpec(generateKeyWithPswd(usr), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] cipherText = new BASE64Decoder().decodeBuffer(input);//.decode(input.getBytes("UTF8"));
            String decryptedString = new String(cipher.doFinal(cipherText),"UTF-8");
            return decryptedString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
