package com.example.poolmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;


public class checkin extends AppCompatActivity {

    EditText card_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        Toolbar toolbar = findViewById(R.id.toolbar_checkin);
        setSupportActionBar(toolbar);
        card_no = (EditText) findViewById(R.id.editText_checkin);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_adduser:
                Intent intent = new Intent(this, User.class);
                startActivity(intent);
                return true;

            case R.id.action_payment:
                Intent intent2 = new Intent(this, payment.class);
                startActivity(intent2);
                return true;

            case R.id.action_visitors:
                Intent intent3 = new Intent(this, visitor.class);
                startActivity(intent3);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void checkinuser(View view) {

        final String cardno = card_no.getText().toString();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference userref = db.collection("user");
        db.collection("user").document(cardno).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        //Toast.makeText(checkin.this,"User found",Toast.LENGTH_SHORT).show();
                        int visit =  document.getLong("Visits").intValue();
                        Toast.makeText(checkin.this,"Visits are:"+visit,Toast.LENGTH_SHORT).show();

                        if(visit>0) {
                            visit= visit -1;
                            Map<Object, Integer> map = new HashMap<>();
                            map.put("Visits", visit );
                            userref.document(cardno).set(map, SetOptions.merge());
                            AlertDialog alertDialog = new AlertDialog.Builder(checkin.this).create();
                            alertDialog.setTitle("Checked in!");
                            alertDialog.setMessage("Remaining visits are : "+visit);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                          //  Toast.makeText(checkin.this,"Reducing one visit, new visits"+visit,Toast.LENGTH_LONG).show();
                        }

                        else if(visit==0) {
                            Toast.makeText(checkin.this,"Visits over!",Toast.LENGTH_SHORT).show();

                            card_no.setText("");
                        }
                    }
                    else{
                        Toast.makeText(checkin.this,"User does not exist!",Toast.LENGTH_SHORT).show();

                        card_no.setText("");
                    }

                }
                else {
                    Toast.makeText(checkin.this,"User not found!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
