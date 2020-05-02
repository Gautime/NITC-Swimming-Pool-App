package com.kapil.poolmanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Visitoradaptor extends RecyclerView.Adapter<Visitoradaptor.MyViewHolder> {

    ArrayList<checkinitem> checks;
    Context context;

    public Visitoradaptor(Context context, ArrayList<checkinitem> checks) {
        this.context = context;
        this.checks = checks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkin_item, parent, false);
        return new MyViewHolder(v);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView text1;
        TextView text2;
        TextView text3;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.visitor_name);
            text2 = (TextView) itemView.findViewById(R.id.visitor_card);
            text3 = (TextView) itemView.findViewById(R.id.visitor_time);
            //itemView.setOnClickListener(this);
        }
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.text1.setText(checks.get(position).names);
        holder.text2.setText(checks.get(position).cards);
        holder.text3.setText(checks.get(position).times);

    }


    @Override
    public int getItemCount() {
        return checks.size();
    }






}
