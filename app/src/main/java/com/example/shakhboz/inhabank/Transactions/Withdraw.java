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

public class Withdraw extends AppCompatActivity {


    private byte[] key;
    private HelperFunctions helper;

    Intent intent;
    /*private static byte[] key = {
            0x2d, 0x2a, 0x2d, 0x42, 0x55, 0x49, 0x4c, 0x44, 0x41, 0x43, 0x4f, 0x44, 0x45, 0x2d, 0x2a, 0x2d
    };*/

    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        Button withdrawButton;
        //Button showbtnwith;
        final EditText inputWithText;
        //final TextView txtviewWith;

        intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");

        helper = new HelperFunctions(Withdraw.this);

        withdrawButton = (Button)findViewById(R.id.withdrawButtonAction);
        //showbtnwith = (Button)findViewById(R.id.withdrawShow);
        inputWithText = (EditText)findViewById(R.id.withdrawInputEditText);
        //txtviewWith = (TextView)findViewById(R.id.showwithdrawBalanceText);



        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curr = retBalance(username);
                int currentBalance = Integer.parseInt(curr);
                int addingBalance = Integer.parseInt(inputWithText.getText().toString());
                int finalBalance = currentBalance - addingBalance;
                //String encryptData = inputDepositText.getText().toString();
                String finalMoney = String.valueOf(finalBalance);
                String addingValue = String.valueOf(addingBalance);
                String type = "Withdraw";
                String dateTime = retDate();
                PrintStream output = null;


                String encryptData = finalMoney + "/" + type + "/" + addingValue + "/" + dateTime;
                String encryptedFinalData = encryptFile(encryptData);
                String fname = username + ".log";
                helper.appendToFile(encryptedFinalData,fname);
                /*try {
                    output = new PrintStream(
                            openFileOutput("out.log",
                                    MODE_PRIVATE));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                output.println(encryptedFinalData);
                output.close();*/
                //txtviewWith.setText(encryptedFinalData);
                inputWithText.setText("");
                Toast.makeText(Withdraw.this, "Completed withdraw", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(Withdraw.this, MainActivity.class);
                mainIntent.putExtra("username", username);
                mainIntent.putExtra("password", password);
                startActivity(mainIntent);
            }
        });

        /*showbtnwith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = username + ".log";
                String allText = "";   // read entire file
                ArrayList<String> changes;
                changes = helper.readFromFile(fname);
                String lastchanges = "";
                String output="";

                if(changes.size()>=1){
                    output = changes.get(changes.size()-1);
                    lastchanges = decryptFile(output);

                }
                else{
                    lastchanges = "0";
                }
                /*try {
                    Scanner scan = new Scanner(
                            openFileInput("out.log"));

                    String line = scan.nextLine();

                    String output = decryptFile(line);
                    txtview.setText(output);
                    scan.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                txtviewWith.setText(lastchanges);

            }
        });*/


    }
    public String retDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = sdf.format(new Date());
        return currentDateTime;
    }
    public String retBalance(String username){
        String fname = username + ".log";
        helper = new HelperFunctions(Withdraw.this);
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
        myPassword=myPassword+helper.getSalt();
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
            SecretKeySpec secretKey = new SecretKeySpec(generateKeyWithPswd(username), "AES");
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
            SecretKeySpec secretKey = new SecretKeySpec(generateKeyWithPswd(username), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] cipherText = new BASE64Decoder().decodeBuffer(input);//.decode(input.getBytes("UTF8"));
            String decryptedString = new String(cipher.doFinal(cipherText),"UTF-8");
            return decryptedString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*private static SecretKeySpec generateKey(final String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();

        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }*/
}
