package com.kapil.poolmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;


public class MainActivity extends AppCompatActivity {

    EditText text_email;
    EditText text_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_email = (EditText) findViewById(R.id.editText_username);
        text_password = (EditText) findViewById(R.id.editText_password);
    }

    public void showcheckin(View view) {


        final String username = text_email.getText().toString();
        final String password = text_password.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("admin").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        Toast.makeText(MainActivity.this,"User found",Toast.LENGTH_SHORT).show();
                        if(password.equals(document.get("password"))) {
                            Toast.makeText(MainActivity.this,"User found and password matched",Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(MainActivity.this, checkin.class);
                            intent.putExtra("Email", username);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Wrong password",Toast.LENGTH_SHORT).show();

                            text_password.setText("");
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this,"User not found",Toast.LENGTH_SHORT).show();
                        text_email.setText("");
                        text_password.setText("");
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,"Query not completed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void forgotpass(View view) {
        Intent intent = new Intent(MainActivity.this, forgotpassword.class);
       // intent.putExtra("Email", username);
        startActivity(intent);

    }
}
