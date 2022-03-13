package com.example.exmp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    EditText emailbox, passwordbox;
    Button loginbtn, createbtn,forgotbtn;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialization of View
       dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        auth=FirebaseAuth.getInstance();
        emailbox = findViewById(R.id.emailbox);
        passwordbox = findViewById(R.id.passwordbox);


        loginbtn = findViewById(R.id.loginbtn);
        createbtn = findViewById(R.id.createbtn);
        forgotbtn = findViewById(R.id.forgotbtn);

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, signup.class));
            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em,pa;
                dialog.show();
                em=emailbox.getText().toString();
                pa=passwordbox.getText().toString();
                auth.signInWithEmailAndPassword(em,pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(login.this,dashboard.class));
                            //Toast.makeText(login.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(login.this, "Wrong one", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
        forgotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText reset = new EditText(view.getContext());
                AlertDialog.Builder passresetlink = new AlertDialog.Builder(view.getContext());
                passresetlink.setTitle("Reset Password");
                passresetlink.setMessage("Enter Your Email To Reset Password");
                passresetlink.setView(reset);

                passresetlink.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String em=reset.getText().toString();
                        auth.sendPasswordResetEmail(em).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(login.this, "Reset Link Has Been Send", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(login.this, "Error"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });
                passresetlink.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Close the Dialog

                    }
                });
                passresetlink.create().show();

            }
        });
    }
}