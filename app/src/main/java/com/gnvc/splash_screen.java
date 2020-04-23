package com.gnvc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class splash_screen extends AppCompatActivity {

    public static final String PREFS_NAME = "login_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        boolean handler=new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                String value = settings.getString("login","");

                if(value.equals("true")){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else
                {
                    Intent intent=new Intent(getApplicationContext(),login.class);
                    startActivity(intent);
                }


            }
        },2*1000);



    }
}
