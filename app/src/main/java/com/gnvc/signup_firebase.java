package com.gnvc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class signup_firebase extends AppCompatActivity {
    public EditText emailId, passwd,conf_pass;
    Button btnSignUp;
    TextView signIn;
    FirebaseAuth firebaseAuth;
    final String[] countno = new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_firebase);

        firebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.signup_et_email);
        passwd = findViewById(R.id.signup_et_password);
        btnSignUp = findViewById(R.id.signup_btn_signiup);
        signIn = findViewById(R.id.signup_tv_signin);
        conf_pass=findViewById(R.id.signup_et_conf_password);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailID = emailId.getText().toString();
                final String paswd = passwd.getText().toString();
                if (emailID.isEmpty()) {
                    emailId.setError("Provide your Email first!");
                    emailId.requestFocus();
                } else if (paswd.isEmpty()) {
                    passwd.setError("Set your password");
                    passwd.requestFocus();
                } else if (emailID.isEmpty() && paswd.isEmpty()) {
                    Toast.makeText(signup_firebase.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                }
                else if(!(passwd.getText().toString().trim().equals(conf_pass.getText().toString().trim()))){
                    Toast.makeText(getApplicationContext(),"Enter correct paswword",Toast.LENGTH_LONG).show();
                    conf_pass.setText("");
                }
                else if (!emailID.isEmpty() && !paswd.isEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(signup_firebase.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (!task.isSuccessful()) {
                                //String c=String.valueOf(countno[0]+1);
                                Toast.makeText(signup_firebase.this.getApplicationContext(),
                                        "SignUp unsuccessful: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                DatabaseReference db= FirebaseDatabase.getInstance().getReference("user_data").child(emailID.replace(".","0"));
                                Map newpost=new HashMap<>();
                                newpost.put("email",emailID);
                                newpost.put("pass",paswd);
                                db.setValue(newpost);
                                startActivity(new Intent(signup_firebase.this, MainActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(signup_firebase.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(signup_firebase.this, login.class);
                startActivity(I);
            }
        });
    }
}
