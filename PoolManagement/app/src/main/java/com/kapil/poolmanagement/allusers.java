package com.kapil.poolmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kapil.poolmanagement.NameComparator;
import com.kapil.poolmanagement.Useradaptor;
import com.kapil.poolmanagement.useritem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class allusers extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    final ArrayList<useritem> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allusers);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_allusers);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference userref = db.collection("user");
        userref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("visitor", "task successfull ");
                    //Toast.makeText(ListComplaints.this, "Fetching Successfull", Toast.LENGTH_SHORT).show();
                    if(task.getResult()!=null) {
                        Log.d("visitor", "Insidee not null block! ");

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String name = (String) document.get("Name");
                            String card = (String) document.get("card");
                            String contact = (String) document.get("Contact");
                            String gendr = (String) document.get("Gender");
                            String validi = (String) document.get("Validity");
                            String visit =document.get("Visits").toString() ;


                            useritem user= new useritem(card, name, contact, gendr,validi, visit);
                            users.add(user);
                            Log.d("visitor", "checkin item added successfully");

                        }
                        Collections.sort(users, new NameComparator());
                        mAdapter = new Useradaptor(allusers.this,users);
                        recyclerView.setAdapter(mAdapter);
                    }
                } else {
                    Toast.makeText(allusers.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
