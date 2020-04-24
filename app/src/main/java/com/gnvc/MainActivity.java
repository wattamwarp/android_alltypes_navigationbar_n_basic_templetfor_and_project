package com.gnvc;

import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

//import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerlayout;
    Toolbar toolbar;
    ActionBarDrawerToggle toogle;
    NavigationView navigation_view;

    Button btn_logout;

    TextView email, f_name;

    public static final String PREFS_NAME = "login_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation_view = findViewById(R.id.navigation_menu);
        View view = navigation_view.inflateHeaderView(R.layout.header);

        email = view.findViewById(R.id.header_emailid);
        f_name = view.findViewById(R.id.header_fname);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String value = settings.getString("emailid", "");
        value.replace(".", "0");

        email.setText(value.replace("0", "."));


        btn_logout = findViewById(R.id.nav_btn_logout);


        //loading the default fragment
        loadFragment(new fragment_home());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(MainActivity.this);

        settoolbar();//creating toolbar icon
        profilrclicks();


        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerlayout.closeDrawers();
                Fragment fragment1 = null;
                switch (item.getItemId()) {

                    case R.id.drawaer_nav_home:
                        fragment1 = new fragment_home();
                        break;

                    case R.id.drawaer_nav_about:
                        fragment1 = new fragment_account();
                        break;

                    case R.id.drawaer_nav_rate:
                        {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=" + getPackageName())));
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }

                        break;
                    }
                    //code for share item listner
                    case R.id.drawaer_nav_share:{
                        Intent myIntent = new Intent(Intent.ACTION_SEND);
                        myIntent.setType("text/plain");
                        String shareBody = "https://youtu.be/-vGluJMNClc";
                        String shareSub = "Your subject";
                        myIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        myIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(myIntent, "Share using"));

                    }

                }

                return loadFragment(fragment1);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //code for skipping login activity
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

                // Writing data to SharedPreferences
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("login", "false");
                editor.commit();

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });


    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new fragment_home();
                break;

            case R.id.nav_account:
                fragment = new fragment_account();
                break;

            case R.id.nav_course:
                fragment = new fragment_course();
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void settoolbar() {
        drawerlayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toogle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.app_name, R.string.app_name);
        drawerlayout.setDrawerListener(toogle);
        toogle.syncState();


        //email=findViewById(R.id.hea)


    }

    public void profilrclicks() {
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new fragment_account();
                replaceFragment(fragment);
                drawerlayout.closeDrawers();

            }
        });

        f_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new fragment_account();
                replaceFragment(fragment);
                drawerlayout.closeDrawers();
            }
        });

    }

    public void replaceFragment(Fragment someFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, someFragment)
                .commit();
    }

}
