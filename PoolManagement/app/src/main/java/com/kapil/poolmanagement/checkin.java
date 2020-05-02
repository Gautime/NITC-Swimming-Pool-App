package com.kapil.poolmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class checkin extends AppCompatActivity {

    EditText card_no;
    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        Toolbar toolbar = findViewById(R.id.toolbar_checkin);
        setSupportActionBar(toolbar);
        card_no = (EditText) findViewById(R.id.editText_checkin);
        details = (TextView) findViewById((R.id.textView_userdetails));
        Intent intent = getIntent();
        String str = intent.getStringExtra("Email");
        toolbar.setTitle("Welcome back to Pool - "+str+"!");

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
            case R.id.action_searchall:
                Intent intent4 = new Intent(this, allusers.class);
                startActivity(intent4);
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
        final CollectionReference checkinref = db.collection("checkin");

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        final String checkindate = formatter.format(date);

        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");
        Date time = new Date();
        final String checkintime = formatter1.format(time);

        //Toast.makeText(checkin.this,"Date is :"+datewa + "and time is : "+timewa,Toast.LENGTH_LONG).show();

        System.out.println(formatter.format(date));
        final Date[] date1 = new Date[1];
        final Date[] date2 = {new Date()};

        db.collection("user").document(cardno).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        //Toast.makeText(checkin.this,"User found",Toast.LENGTH_SHORT).show();
                        int visit =  document.getLong("Visits").intValue();
                        String validity = document.get("Validity").toString();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                             date1[0] =formatter.parse(validity);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(checkin.this,"Visits are:"+visit,Toast.LENGTH_SHORT).show();

                        if(visit>0 && date1[0].compareTo(date2[0])>=0) {
                            visit= visit -1;
                            Map<Object, Integer> map = new HashMap<>();
                            map.put("Visits", visit );
                            userref.document(cardno).set(map, SetOptions.merge());
                            final String name = document.get("Name").toString();
                            Map<Object,String> check  = new HashMap<>();
                            check.put("card",cardno);
                            check.put("date", checkindate);
                            check.put("time", checkintime);
                            check.put("name", name);
                            checkinref.document(cardno).set(check, SetOptions.merge());
                            AlertDialog alertDialog = new AlertDialog.Builder(checkin.this).create();
                            alertDialog.setTitle("Checked in!");
                            alertDialog.setMessage("Remaining visits are : "+visit + "\n Validity is : "+validity);
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

                            AlertDialog alertDialog = new AlertDialog.Builder(checkin.this).create();
                            alertDialog.setTitle("Cannot check in!");
                            alertDialog.setMessage("No visits remaining. Please pay and update the visits!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                            //Toast.makeText(checkin.this,"Visits over!",Toast.LENGTH_SHORT).show();

                            card_no.setText("");
                        }
                        else if(date1[0].compareTo(date2[0])<0) {

                            AlertDialog alertDialog = new AlertDialog.Builder(checkin.this).create();
                            alertDialog.setTitle("Cannot check in!");
                            alertDialog.setMessage("Validity of the card is over. Please update payment!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                            //Toast.makeText(checkin.this,"Visits over!",Toast.LENGTH_SHORT).show();

                            card_no.setText("");
                        }
                    }
                    else{
                        Toast.makeText(checkin.this,"User does not exist!",Toast.LENGTH_SHORT).show();

                        card_no.setText("");
                    }

                }
                else {
                    Toast.makeText(checkin.this,"Cannot complete checkin at the moment!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void searchuser(View view) {

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
                        String name = document.get("Name").toString();
                        String validity = document.get("Validity").toString();
                        details.setText("User details are found as : \n Name : " + name + "\n Remaining visits :"+visit+"\n Validity upto : "+validity);
                        Toast.makeText(checkin.this,"Visits are:"+visit,Toast.LENGTH_SHORT).show();


                    }
                    else{
                        Toast.makeText(checkin.this,"User does not exist!",Toast.LENGTH_SHORT).show();

                        card_no.setText("");
                    }

                }
                else {
                    Toast.makeText(checkin.this,"Cannot complete query at the moment!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void clearscreen(View view) {

        card_no.setText("");
        details.setText("");
    }
}
