package com.kapil.poolmanagement;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kapil.poolmanagement.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

//import android.R;

//import ample.poolmanagement.R;

public class Useradaptor extends RecyclerView.Adapter<Useradaptor.MyViewHolder>  {


    static ArrayList<useritem> allusers;
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
            ib1 = (ImageButton) itemView.findViewById(R.id.imageButton);

        }


    }



    @Override
    public void onBindViewHolder(Useradaptor.MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.text1.setText(allusers.get(position).names);
        holder.text2.setText(allusers.get(position).cards);
        holder.text3.setText(allusers.get(position).visits);
        holder.text4.setText(allusers.get(position).validity);

        holder.ib1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Log.d("User adaptor","button fucking clicked!!");
                 useritem removed = allusers.get(position);
                // remove your item from data base
                 allusers.remove(position);  // remove the item from list
                 notifyItemRemoved(position);// notify the adapter about the removed item
                 notifyItemRangeChanged(0, allusers.size());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("user").document(removed.cards)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                    Log.w("useradaptor", "User deleted!!");
                                //   Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("user adaptor", "Error deleting document", e);
                            }
                        });

            }
        });


    }


    @Override
    public int getItemCount() {
        return allusers.size();
    }





}
