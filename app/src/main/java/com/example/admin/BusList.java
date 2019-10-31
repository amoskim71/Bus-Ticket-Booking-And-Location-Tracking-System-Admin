package com.example.admin;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;
import java.util.List;


public class BusList extends ArrayAdapter<Bus> {

    private Activity context;
    private List<Bus> busList;

    public BusList(Activity context, List<Bus> busList) {
        super(context, R.layout.list_bus, busList);
        this.context = context;
        this.busList = busList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_bus, null, true);


        TextView textViewTravelsName = (TextView) listViewItem.findViewById(R.id.text_view_busName);
        TextView textViewBusNumber = (TextView) listViewItem.findViewById(R.id.text_view_busNumber);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.text_view_date);
        TextView textViewFrom = (TextView) listViewItem.findViewById(R.id.text_view_from);
        TextView textViewTo = (TextView) listViewItem.findViewById(R.id.text_view_to);
        TextView textViewCondition = (TextView) listViewItem.findViewById(R.id.text_view_condition);


        Bus bus = busList.get(position);

        textViewTravelsName.setText(bus.getTravelsName());
        textViewBusNumber.setText("Bus Number       : "+bus.getBusNumber());
        textViewDate.setText("Journey Date      : "+bus.getDate());
        textViewFrom.setText("Bus From            : "+bus.getFrom());
        textViewTo.setText("Bus To                : "+bus.getTo());
        textViewCondition.setText("Bus Condition    : "+bus.getBusCondition());

        return listViewItem;
    }
}
