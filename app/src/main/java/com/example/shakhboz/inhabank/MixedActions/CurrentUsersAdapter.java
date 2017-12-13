package com.example.shakhboz.inhabank.MixedActions;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shakhboz.inhabank.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ShakhboZ on 12/12/2017.
 */

public class CurrentUsersAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    String username;
    ArrayList<String> currentUsers;
    Context context;


    public CurrentUsersAdapter(String username, ArrayList<String> currentUsers, Context context) {
        this.username = username;
        this.currentUsers = currentUsers;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v1 = inflater.inflate(R.layout.current_user_for_recyclerview, parent, false);
        viewHolder = new CurrentUsersViewHolder(v1);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CurrentUsersViewHolder currentUsersViewHolder=(CurrentUsersViewHolder)holder;
        configureCurrentUsersViewHolder(currentUsersViewHolder,position);




    }

    private void configureCurrentUsersViewHolder(final CurrentUsersViewHolder currentUsersViewHolder, int position) {

        String messageText = String.valueOf(currentUsers.get(position));
        currentUsersViewHolder.userName.setText(messageText);

        currentUsersViewHolder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence balance[];
                HelperFunctions helper = new HelperFunctions(context);
                if(currentUsersViewHolder.userName.getText().toString().equals(username)){
                    balance = new CharSequence[]{"$" + helper.retBalance(username)};
                }else{
                    balance = new CharSequence[]{helper.retBalanceGarbage(currentUsersViewHolder.userName.getText().toString())};
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Balance");
                builder.setItems(balance, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.currentUsers.size();
    }
    public static class CurrentUsersViewHolder extends RecyclerView.ViewHolder{
        TextView userName;

        public CurrentUsersViewHolder(View itemView) {
            super(itemView);

             userName =(TextView)itemView.findViewById(R.id.user_name_id);
        }
    }
    }


