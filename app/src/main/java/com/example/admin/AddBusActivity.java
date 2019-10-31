package com.example.admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class AddBusActivity extends AppCompatActivity implements View.OnClickListener {
    //implements View.OnClickListener
    private EditText travelsName;
    private EditText busNumber;
    private EditText dateBus;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Button addBus;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDatesetListener;

//    public static final String BUSID="busId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);


        getSupportActionBar().setTitle("Add Buses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDisplayDate = (TextView) findViewById(R.id.journeyDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddBusActivity.this
                        , android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , mDatesetListener
                        , year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDatesetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                Log.d(TAG, "OnDateSet:date :" + day + "/" + (month + 1) + "/" + year);
                String date = day + "-" + (month + 1) + "-" + year;
                // String status="Journey Date";
                // mDisplayDate.setText(status+"\n"+date);
                mDisplayDate.setText(date);
            }
        };

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        travelsName = (EditText) findViewById(R.id.travelsName);
        busNumber = (EditText) findViewById(R.id.busNumber);
        dateBus = (EditText) findViewById(R.id.journeyDate);
        addBus = (Button) findViewById(R.id.addBus);

        spinner1 = (Spinner) findViewById(R.id.busFrom);
        spinner2 = (Spinner) findViewById(R.id.busTo);
        spinner3 = (Spinner) findViewById(R.id.busCondition);


        //From
        Spinner spinner1 = findViewById(R.id.busFrom);
        String[] items1 = new String[]{"From", "Jaffna", "Vavuniya", "Mannar", "Colombo", "Kandy", "Batticola", "Ampara"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        spinner1.setAdapter(adapter1);

        //To
        Spinner spinner2 = findViewById(R.id.busTo);
        String[] items2 = new String[]{"To", "Jaffna", "Vavuniya", "Mannar", "Colombo", "Kandy", "Batticola", "Ampara"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        spinner2.setAdapter(adapter2);

        //Condition
        Spinner spinner3 = findViewById(R.id.busCondition);
        String[] items3 = new String[]{"Bus Condition", "A/C Sleepers", "A/C Non_Sleepers", "Non_A/C Sleepers", "Non_A/C Non_Sleepers"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        spinner3.setAdapter(adapter3);

        progressDialog = new ProgressDialog(this);
        addBus.setOnClickListener(this);

    }


    private void addBuses() {
        String travelsNameI = travelsName.getText().toString().trim();
        String busNumberI = busNumber.getText().toString().trim();
        String date = dateBus.getText().toString().trim();
        String from = spinner1.getSelectedItem().toString().trim();
        String to = spinner2.getSelectedItem().toString().trim();
        String busCondition = spinner3.getSelectedItem().toString().trim();

        String busId = databaseReference.push().getKey();

        if (TextUtils.isEmpty(travelsNameI)) {
            //email is empty
            Toast.makeText(this, "Please Enter Travels Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(busNumberI)) {
            //password is empty
            Toast.makeText(this, "Please Enter Bus Number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(date)) {
            //password is empty
            Toast.makeText(this, "Please Enter Journey Date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.equals(from,"From")) {
            //email is empty
            Toast.makeText(this, "Please Select Departure Place", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.equals(to,"To")) {
            //password is empty
            Toast.makeText(this, "Please Select Destination Place", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.equals(busCondition,"Bus Condition")) {
            //password is empty
            Toast.makeText(this, "Please Select Bus Condition", Toast.LENGTH_SHORT).show();
            return;
        }

        Bus bus = new Bus(busId, travelsNameI, busNumberI, date, from, to, busCondition);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child("BusDetails").child(busId).setValue(bus);
        progressDialog.setMessage("Adding Buses Please Wait...");
        progressDialog.show();

        Intent intent=new Intent(getApplicationContext(),ViewBusActivity.class);

        startActivity(intent);
        progressDialog.dismiss();

    }


    @Override
    public void onClick(View view) {
        if (view == addBus) {
            addBuses();
        }
    }

}
