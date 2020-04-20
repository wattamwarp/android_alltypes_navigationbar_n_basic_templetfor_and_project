package com.gnvc;

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

public class login extends AppCompatActivity {
    EditText username,password;
    Button login;
    FirebaseAuth firebaseauth;
    TextView signup;

    //Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseauth=FirebaseAuth.getInstance();

        username = (EditText)findViewById(R.id.et_username);
        password=(EditText)findViewById(R.id.et_password);
        login=(Button)findViewById(R.id.btn_login);
        signup=findViewById(R.id.login_tv_signin);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e,p;
                e=username.getText().toString();
                p=password.getText().toString();

                {
                    firebaseauth.signInWithEmailAndPassword(e,p).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"enter corect appss",Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),signup_firebase.class);
                startActivity(intent);
            }
        });


    }
}
