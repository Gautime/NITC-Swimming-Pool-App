package com.example.poolmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.UserManager;
import android.view.View;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class User extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseFirestore db;
    private Map<String,Object> data = new HashMap<>();
    String validity;
    String genderscroll;
    EditText card_no;
    EditText name;
    EditText contact;
    //EditText gender;      --to be updated--
    EditText visits;
    //EditText validity;        --to be updated--
    TextView text_validity_user;
    EditText et_validityDate;
    DatePickerDialog.OnDateSetListener setListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        card_no = (EditText) findViewById(R.id.editText_card_user);
        name = (EditText) findViewById(R.id.editText_name_user);
        contact = (EditText) findViewById(R.id.editText_contact_user);

        Spinner spinner_gender = findViewById(R.id.spinner4);
        spinner_gender.setOnItemSelectedListener(this);

        //gender = (EditText) findViewById(R.id.editTextgender_user);       --to be updated--
        visits = (EditText) findViewById(R.id.editText_intvisits_user);
        //validity = (EditText) findViewById(R.id.editText_validity_user);      --to be updated--

        text_validity_user = findViewById(R.id.text_validity_user);
        et_validityDate = findViewById(R.id.et_validityDate);
        Calendar calendar = Calendar.getInstance();
        final  int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);
        text_validity_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        User.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = month+"/"+day+"/"+year;
                text_validity_user.setText(date);
            }
        };
        et_validityDate.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        User.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = month+"/"+day+"/"+year;
                        et_validityDate.setText(date);
                        validity = date;
                    }
                },year,month,day);
                        datePickerDialog.show();
            }
        }));

        db = FirebaseFirestore.getInstance();

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        genderscroll = parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        genderscroll = "Male";

    }

    // --DATABASE UPDATION PENDING--

    public void newuser(View view) {
        final String cardno = card_no.getText().toString();
        final String user = name.getText().toString();
        final String contactno = contact.getText().toString();

        final String genders = genderscroll;
        final Integer visit = Integer.parseInt(visits.getText().toString());
        final String valid = validity;

        if(contactno.length()>10 || contactno.length()<10){
            Toast.makeText(User.this, "Enter valid number", Toast.LENGTH_LONG).show();
            contact.setText("");
            AlertDialog alertDialog = new AlertDialog.Builder(User.this).create();
            alertDialog.setTitle("Data Error!");
            alertDialog.setMessage("Enter valid contact number");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
            //throw new java.lang.Error("this is very bad");
        }
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
