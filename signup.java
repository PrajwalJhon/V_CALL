package com.example.exmp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.FirestoreGrpc;

public class signup extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;
    EditText emailbox, passwordbox,namebox;
    Button loginbtn, createbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //initialization of View
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        namebox = findViewById(R.id.namebox);
        emailbox = findViewById(R.id.emailbox);
        passwordbox = findViewById(R.id.passwordbox);


        loginbtn = findViewById(R.id.loginbtn);
        createbtn = findViewById(R.id.createbtn);

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em,pa,na;
                em=emailbox.getText().toString();
                pa=passwordbox.getText().toString();
                na=namebox.getText().toString();

                user u=new user();
                u.setName(na);
                u.setEmail(em);
                u.setPass(pa);


                auth.createUserWithEmailAndPassword(em,pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            db.collection("users").document().set(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(signup.this,login.class));
                                }
                            });
                            Toast.makeText(signup.this, "Account Created", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(signup.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}