package com.example.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "NavigationActivity";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDatesetListener;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth firebaseAuth;

    private Button btnBus;
    private Button btnCustomer;
    private Button btnLocation;
    private Button btnPlace;
    private Button btnSeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        btnBus=(Button)findViewById(R.id.btnBus);
        btnCustomer=(Button)findViewById(R.id.btnCustomer);
        btnLocation=(Button)findViewById(R.id.btnLocation);
        btnPlace=(Button)findViewById(R.id.btnPlace);
        btnSeat=(Button)findViewById(R.id.btnSeat);

        firebaseAuth = FirebaseAuth.getInstance();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        btnBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddBusActivity.class));
            }
        });
        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddCustomerActivity.class));
            }
        });
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddLocationActivity.class));
            }
        });
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddPlaceActivity.class));
            }
        });
        btnSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SendEmailActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                Intent h = new Intent(NavigationActivity.this, NavigationActivity.class);
                startActivity(h);
                break;
            case R.id.setting:
                Intent s = new Intent(NavigationActivity.this, SettingActivity.class);
                startActivity(s);
                break;
            case R.id.help:
                Intent he = new Intent(NavigationActivity.this, HelpActivity.class);
                startActivity(he);
                break;
            case R.id.detail:
                Intent d = new Intent(NavigationActivity.this, DetailActivity.class);
                startActivity(d);
                break;
            case R.id.cancel:
                Intent c = new Intent(NavigationActivity.this, CancelActivity.class);
                startActivity(c);
                break;
            case R.id.contact:
                Intent co = new Intent(NavigationActivity.this, ContactActivity.class);
                startActivity(co);
                break;
            case R.id.logout:
//                Intent lo = new Intent(NavigationActivity.this, LogoutActivity.class);
//                startActivity(lo);
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
        return false;
    }

}

