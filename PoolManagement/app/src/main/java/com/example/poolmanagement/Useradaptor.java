package com.example.poolmanagement;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Useradaptor extends RecyclerView.Adapter<Useradaptor.MyViewHolder>  {


    ArrayList<useritem> allusers;
    Context context;

    public Useradaptor(Context context, ArrayList<useritem> checks) {
        this.context = context;
        this.allusers = checks;
    }

    @NonNull
    @Override
    public Useradaptor.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new Useradaptor.MyViewHolder(v);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        ImageButton ib1;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.user_name);
            text2 = (TextView) itemView.findViewById(R.id.user_card);
            text3 = (TextView) itemView.findViewById(R.id.user_visits);
            text4 = (TextView) itemView.findViewById(R.id.user_validity);

            //itemView.setOnClickListener(this);
        }
    }



    @Override
    public void onBindViewHolder(Useradaptor.MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.text1.setText(allusers.get(position).names);
        holder.text2.setText(allusers.get(position).cards);
        holder.text3.setText(allusers.get(position).visits);
        holder.text4.setText(allusers.get(position).validity);


    }


    @Override
    public int getItemCount() {
        return allusers.size();
    }





}
