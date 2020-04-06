package com.example.poolmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class payment extends AppCompatActivity {

    private FirebaseFirestore db;
    private Map<String,Object> data = new HashMap<>();
    EditText card_no;
    EditText receipt_no;
    EditText  total;
    EditText newvisits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        card_no = (EditText) findViewById(R.id.editText_card_payment);
        receipt_no = (EditText) findViewById(R.id.editText_receipt_payment);
        total = (EditText) findViewById(R.id.editText_amount_payment);
        newvisits = (EditText) findViewById(R.id.editText_visits_payment);

    }

    public void updatepayment(View view) {
        final String cardno = card_no.getText().toString();
        final String receipt = receipt_no.getText().toString();
        final String visit = newvisits.getText().toString();
        final String amount = total.getText().toString();

        data.put("card",cardno);
        data.put("amount",amount);
        data.put("receipt",receipt);
        data.put("visits",visit);

        db.collection("payment").document(cardno)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(payment.this, "User created successfully", Toast.LENGTH_SHORT).show();
                        finish();                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(payment.this, "Something gone wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
