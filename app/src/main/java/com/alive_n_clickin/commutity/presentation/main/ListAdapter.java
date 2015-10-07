package com.alive_n_clickin.commutity.presentation.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.IBus;
import com.alive_n_clickin.commutity.infrastructure.api.ArrivingVehicle;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class fills the ListView within {@link MainFragment} with list items. It takes help from the {@link LittleFlagAdapter} to set the flags.
 */
public class ListAdapter extends ArrayAdapter<ArrivingVehicle> {

    public ListAdapter(Context currentContext,List<ArrivingVehicle> arrivingVehicleList) {
        super(currentContext,0,arrivingVehicleList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.vehicle_list, parent, false);
        }

        ArrivingVehicle arrivingVehicle = getItem(position);
        IBus bus = arrivingVehicle.getBus();


        TextView busNumber = (TextView) convertView.findViewById(R.id.busNumber);
        busNumber.setText(bus.getRouteNumber());

        TextView targetDestination = (TextView) convertView.findViewById(R.id.targetDestination);
        targetDestination.setText(bus.getDestination());


        long time = arrivingVehicle.getTimeToArrival();

        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(time);

        TextView timeUntilArrival = (TextView) convertView.findViewById(R.id.timeUntilArrival);
        timeUntilArrival.setText(diffInMinutes+"");


        GridView flagGridView = (GridView) convertView.findViewById(R.id.flagGridView);
        // Use the adapter for setting all the flags to the list item.
        flagGridView.setAdapter(new LittleFlagAdapter(getContext(), bus.getFlags()));

        return convertView;
    }
}