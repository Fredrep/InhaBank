package com.example.shakhboz.inhabank.Transactions;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shakhboz.inhabank.MixedActions.HelperFunctions;
import com.example.shakhboz.inhabank.R;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class Deposit extends AppCompatActivity {
    private byte[] key;

    Intent intent;
    /*private static byte[] key = {
            0x2d, 0x2a, 0x2d, 0x42, 0x55, 0x49, 0x4c, 0x44, 0x41, 0x43, 0x4f, 0x44, 0x45, 0x2d, 0x2a, 0x2d
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        Button depositButton;
        Button showbtn;
        final EditText inputDepositText;
        final TextView txtview;

        intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");

        HelperFunctions helper = new HelperFunctions(Deposit.this);
        String salt = helper.getSalt();

        depositButton = (Button)findViewById(R.id.depositButtonAction);
        showbtn = (Button)findViewById(R.id.depositShow);
        inputDepositText = (EditText)findViewById(R.id.depositInputEditText);
        txtview = (TextView)findViewById(R.id.showBalanceText);
        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encryptData = inputDepositText.getText().toString();
                String encryptedFinalData = encryptFile(encryptData);

                PrintStream output = null;
                try {
                    output = new PrintStream(
                            openFileOutput("out.log",
                                    MODE_PRIVATE));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                output.println(encryptedFinalData);
                output.close();
                txtview.setText(encryptedFinalData);
            }
        });

        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String allText = "";   // read entire file
                try {
                    Scanner scan = new Scanner(
                            openFileInput("out.log"));

                    String line = scan.nextLine();

                    String output = decryptFile(line);
                    txtview.setText(output);
                    //txtview.setText(output);
                    scan.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });



    }



    public byte[] generateKeyWithPswd(String myPassword)
    {
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
            SecretKeySpec secretKey = new SecretKeySpec(generateKeyWithPswd("paswdNuriddin"), "AES");
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
            SecretKeySpec secretKey = new SecretKeySpec(generateKeyWithPswd("paswdNuriddin"), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] cipherText = new BASE64Decoder().decodeBuffer(input);//.decode(input.getBytes("UTF8"));
            String decryptedString = new String(cipher.doFinal(cipherText),"UTF-8");
            return decryptedString;
            /*byte[] decreyptedData = cipher.doFinal(input.getBytes("UTF8"));
            String decryptedString = new String(decreyptedData);

            //byte[] cipherText = Base64.getDecoder().decode(input.getBytes("UTF8"));
            //String decryptedString = new String(cipherText);*/
            //return decryptedString;
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
