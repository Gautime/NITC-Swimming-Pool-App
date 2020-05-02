package com.kapil.poolmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.io.ObjectStreamException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class visitor extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    final ArrayList<checkinitem> checkins = new ArrayList<>();
    //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        final String checkindate = formatter.format(date);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference userref = db.collection("checkin");
        userref.whereEqualTo("date",checkindate).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("visitor", "task successfull ");
                    //Toast.makeText(ListComplaints.this, "Fetching Successfull", Toast.LENGTH_SHORT).show();
                    if(task.getResult()!=null) {
                        Log.d("visitor", "Insidee not null block! ");

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String name = (String) document.get("name");
                            String date = (String) document.get("date");
                            String time = (String) document.get("time");
                            String card = (String) document.get("card");

                            checkinitem checks = new checkinitem(card, date, time, name);
                            checkins.add(checks);
                            Log.d("visitor", "checkin item added successfully");

                        }
                        Collections.sort(checkins, new TimeComparator());
                        mAdapter = new Visitoradaptor(visitor.this,checkins);
                        recyclerView.setAdapter(mAdapter);
                    }
                } else {
                    Toast.makeText(visitor.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }
        });



        }


}
