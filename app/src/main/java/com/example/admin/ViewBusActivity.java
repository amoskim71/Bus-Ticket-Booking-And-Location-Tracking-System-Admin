package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBusActivity extends AppCompatActivity {
    private ListView listViewBuses;
    private DatabaseReference databaseBus;
    private List<Bus> busList;
//    private Spinner spinner1;
//    private Spinner spinner2;
//    private Spinner spinner3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bus);

        getSupportActionBar().setTitle("All Bus Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        final String busId = getIntent().getStringExtra("BUSID");
        busList = new ArrayList<>();

//        spinner1 = (Spinner) findViewById(R.id.editTextbusFrom);
//        spinner2 = (Spinner) findViewById(R.id.editTextbusTo);
//        spinner3 = (Spinner) findViewById(R.id.editTextbusCondition);

//        //From
//        Spinner spinner1 = findViewById(R.id.editTextbusFrom);
//        String[] items1 = new String[]{"From", "Jaffna", "Vavuniya", "Mannar", "Colombo", "Kandy", "Batticola", "Ampara"};
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
//        spinner1.setAdapter(adapter1);
//
//        //To
//        Spinner spinner2 = findViewById(R.id.editTextbusTo);
//        String[] items2 = new String[]{"To", "Jaffna", "Vavuniya", "Mannar", "Colombo", "Kandy", "Batticola", "Ampara"};
//        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
//        spinner2.setAdapter(adapter2);
//
//        //Condition
//        Spinner spinner3 = findViewById(R.id.editTextbusCondition);
//        String[] items3 = new String[]{"Bus Condition", "A/C Sleepers", "A/C Non_Sleepers", "Non_A/C Sleepers", "Non_A/C Non_Sleepers"};
//        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
//        spinner3.setAdapter(adapter3);

        listViewBuses = (ListView) findViewById(R.id.listViewBusDetails);
        databaseBus = FirebaseDatabase.getInstance().getReference();
//         = FirebaseDatabase.getInstance().getReference("BusDetails");
        FirebaseDatabase.getInstance().getReference("BusDetails")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        busList.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Bus bus = snapshot.getValue(Bus.class);
                                busList.add(bus);
                            }
                            BusList adapter = new BusList(ViewBusActivity.this, busList);
                            listViewBuses.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



        listViewBuses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bus bus =busList.get(i);
                showUpdateDeleteDialog(bus.getBusId(),bus.getTravelsName(),bus.getBusNumber(),bus.getDate(),bus.getFrom(),bus.getTo(),bus.getBusCondition());
            }
        });
//        listViewBuses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Bus bus =busList.get(i);
//                showUpdateDeleteDialog(bus.getBusId(),bus.getTravelsName(),bus.getBusNumber(),bus.getDate(),bus.getFrom(),bus.getTo(),bus.getBusCondition());
//                return false;
//            }
//        });
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

        // update ticket fees details
    private void showUpdateDeleteDialog(final String busId,String travelsName,String busNumber,String date,String from, String to,String busCondition){
        AlertDialog.Builder dialogBuilder =new AlertDialog.Builder(this);

        LayoutInflater inflater =getLayoutInflater();

        final View dialogView =inflater.inflate(R.layout.update_dialog,null);

        dialogBuilder.setView(dialogView);

        final EditText editTravelsName  =(EditText)dialogView.findViewById(R.id.editTexttravelsName);
        final EditText editBusNumber =(EditText)dialogView.findViewById(R.id.editTextbusNumber);
        final EditText editDate       =(EditText)dialogView.findViewById(R.id.editTextjourneyDate);

        final EditText editFromBus=(EditText) findViewById(R.id.editTextbusFrom);
        final EditText editToBus=(EditText) findViewById(R.id.editTextbusTo);
        final EditText editConditionBus=(EditText) findViewById(R.id.editTextbusCondition);

        final Button buttonUpdate   =(Button)dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete   =(Button)dialogView.findViewById(R.id.buttonDelete);

//        editTravelsName.setText(travelsName);
//        editBusNumber.setText(busNumber);
//        editDate.setText(date);
//        editFromBus.setText(from);
//        editToBus.setText(to);
//        editConditionBus.setText(busCondition);

        dialogBuilder.setTitle("Updating "+travelsName);

        final AlertDialog alertDialog =dialogBuilder.create();
        alertDialog.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bus_1 = editTravelsName.getText().toString().trim();
                String bus_2 = editBusNumber.getText().toString().trim();
                String bus_3 = editDate.getText().toString().trim();
                String bus_4 = editFromBus.getText().toString();
                String bus_5 = editToBus.getText().toString();
                String bus_6 = editConditionBus.getText().toString();


                updateBusDetail(busId,bus_1,bus_2,bus_3,bus_4,bus_5,bus_6);
                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBus(busId);
            }
        });



    }
    private void deleteBus(String busId){
        DatabaseReference drTravellingPath =FirebaseDatabase.getInstance().getReference("BusDetails").child(busId);

        drTravellingPath.removeValue();

        Toast.makeText(this, " Bus Detail Deleted Successfully", Toast.LENGTH_LONG).show();

    }
    private boolean updateBusDetail(String busId,String travelsNameI,String busNumberI,String date,String from,String to,String busCondition){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BusDetails").child(busId);

        Bus bus = new Bus(busId, travelsNameI, busNumberI, date, from, to, busCondition);
        databaseReference.setValue(bus);

        Toast.makeText(this, "Bus Detail Updated Successfully ", Toast.LENGTH_LONG).show();
        return true;
    }

}

