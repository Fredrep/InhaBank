package com.example.shakhboz.inhabank.MixedActions;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.example.shakhboz.inhabank.R;
import com.example.shakhboz.inhabank.Transactions.Deposit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * Created by Nemo on 12/12/2017.
 */

public class HelperFunctions {
    Context context;
    private byte[] key;
    public static String usernamesFileName = "Usernames.txt", hashedPasswords = "hashedPAsswords.txt";
    public HelperFunctions(Context context){
        this.context = context;
    };

    public String getSHA128Password(String passwordToHash){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(getSalt().getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public void appendToFile(String data, String filename){

        ArrayList<String> arrayData = readFromFile(filename);
        arrayData.add(data);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            for(int i = 0; i < arrayData.size(); i++){
                outputStreamWriter.append(arrayData.get(i) + "\n");
            }

            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public void writeToFile(String data, String filename) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public ArrayList<String> readFromFile(String filename) {

        ArrayList<String> ret = new ArrayList<String>();

        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
               // StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                   // stringBuilder.append(receiveString);
                    ret.add(receiveString);
                }

                inputStream.close();
                //ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }



    public String getSalt(){
        Scanner s = new Scanner(context.getResources().openRawResource(R.raw.salt));
        while (s.hasNextLine()){
            return s.nextLine();
        }
        s.close();
        return "";
    }


    public boolean isPasswordExist(String password){
        ArrayList<String> passwords = readFromFile(hashedPasswords);
        for(int i = 0; i < passwords.size(); i++){
            if(passwords.get(i).equals(password)){
                return true;
            }
        }
        return false;
    }


    public boolean isUsernameExist(String username){
        ArrayList<String> usernames = readFromFile(usernamesFileName);
        for(int i = 0; i < usernames.size(); i++){
            if(usernames.get(i).equals(username)){
                return true;
            }
        }
        return false;
    }


    public String retDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = sdf.format(new Date());
        return currentDateTime;
    }
    public String retBalance(String username){
        String fname = username + ".log";
        ArrayList<String> userslog = readFromFile(fname);
        String balance;
        if(userslog.size()>=1){
            String tmp = userslog.get(userslog.size()-1);
            String data = decryptFile(tmp, username);
            String delims = "[/]";
            String[] logs = data.split(delims);
            balance = logs[0];
        }
        else{
            balance="0";
        }

        return balance;
    }



    public byte[] generateKeyWithPswd(String myPassword)
    {
        myPassword=myPassword+getSalt();
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
    public String encryptFile(String input, String username){
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
    public String decryptFile(String input, String username){
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


}
