package com.example.poolmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class payment extends AppCompatActivity {

    private FirebaseFirestore db;
    private Map<String,Object> data = new HashMap<>();
    EditText card_no;
    EditText receipt_no;
    EditText  total;
    EditText newvisits;
    TextView tv_validity;
    EditText et_validity;
    DatePickerDialog.OnDateSetListener setListener1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        card_no = (EditText) findViewById(R.id.editText_card_payment);
        receipt_no = (EditText) findViewById(R.id.editText_receipt_payment);
        total = (EditText) findViewById(R.id.editText_amount_payment);
        newvisits = (EditText) findViewById(R.id.editText_visits_payment);

    tv_validity = findViewById(R.id.tv_validity);
    et_validity = findViewById(R.id.et_validity);
    Calendar calendar1 = Calendar.getInstance();
    final int year = calendar1.get(Calendar.YEAR);
    final int month = calendar1.get(Calendar.MONTH);
    final int day = calendar1.get(Calendar.DAY_OF_YEAR);
    tv_validity.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    payment.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener1, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        }
    });
    setListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month = month+1;
            String date = day+"/"+month+"/"+year;
            tv_validity.setText(date);
        }
    };
    et_validity.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    payment.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    month = month+1;
                    String date = day+"/"+month+"/"+year;
                    et_validity.setText(date);
                }
            },year,month,day);
            datePickerDialog.show();
        }
    });

    }

    // --DATABASE UPDATION PENDING--

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
