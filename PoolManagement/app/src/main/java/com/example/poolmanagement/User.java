package com.example.poolmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class User extends AppCompatActivity {

    private FirebaseFirestore db;
    private Map<String,Object> data = new HashMap<>();
    EditText card_no;
    EditText name;
    EditText contact;
    EditText gender;
    EditText visits;
    EditText validity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        card_no = (EditText) findViewById(R.id.editText_card_user);
        name = (EditText) findViewById(R.id.editText_name_user);
        contact = (EditText) findViewById(R.id.editText_contact_user);
        gender = (EditText) findViewById(R.id.editTextgender_user);
        visits = (EditText) findViewById(R.id.editText_visits_user);
        validity = (EditText) findViewById(R.id.editText_validity_user);
        db = FirebaseFirestore.getInstance();

    }

    public void newuser(View view) {
        final String cardno = card_no.getText().toString();
        final String user = name.getText().toString();
        final String contactno = contact.getText().toString();
        final String genders = gender.getText().toString();
        final String visit = visits.getText().toString();
        final String valid = validity.getText().toString();

        data.put("card",cardno);
        data.put("Name",user);
        data.put("Contact",contactno);
        data.put("Gender",genders);
        data.put("Visits",visit);
        data.put("Validity",valid);

        db.collection("user").document(cardno)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(User.this, "User created successfully", Toast.LENGTH_SHORT).show();
                        finish();                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(User.this, "Something gone wrong!", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
