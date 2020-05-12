package com.kapil.poolmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class forgotpassword extends AppCompatActivity {

    //private FirebaseAuth mAuth;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
         username = (EditText) findViewById(R.id.editText_forget_password);

    }

    public void resendpassword(View view) {
        String user = username.getText().toString();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("admin").document(user).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        //Toast.makeText(forgotpassword.this,"User found",Toast.LENGTH_SHORT).show();
                     //  String email = document.get("email").toString();
                     //  String password = document.get("password").toString();
                      //  Intent i = new Intent(Intent.ACTION_SEND);
                      //  i.setType("message/rfc822");
                       // i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
                        //i.putExtra(Intent.EXTRA_SUBJECT, "Forgot password for NITC Swimming Pool App");
                        //i.putExtra(Intent.EXTRA_TEXT   , " The password you requested is : "+ password);
                        try {
                            Toast.makeText(forgotpassword.this, "Sent email to admin email-id.", Toast.LENGTH_LONG).show();
                            //  startActivity(Intent.createChooser(i, "Sent mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(forgotpassword.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(forgotpassword.this,"Username not found",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(forgotpassword.this,"Query not completed",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
