package com.example.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddCustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        getSupportActionBar().setTitle("Add Customer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
