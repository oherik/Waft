package com.alive_n_clickin.commutity.presentation.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.ArrivingVehicle;
import com.alive_n_clickin.commutity.infrastructure.api.ApiArrival;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class fills the ListView within {@link MainFragment} with list items. It takes help from the {@link LittleFlagAdapter} to set the flags.
 */
public class ListAdapter extends ArrayAdapter<ApiArrival> {

    public ListAdapter(Context currentContext,List<ApiArrival> apiArrivalList) {
        super(currentContext,0, apiArrivalList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.vehicle_list, parent, false);
        }

        ApiArrival apiArrival = getItem(position);
        ArrivingVehicle vehicle = apiArrival.getVehicle();


        TextView busNumber = (TextView) convertView.findViewById(R.id.busNumber);
        busNumber.setText(vehicle.getShortName());

        TextView targetDestination = (TextView) convertView.findViewById(R.id.targetDestination);
        targetDestination.setText(vehicle.getDestination());

        long realTime = vehicle.getTimeToArrival();
        long realDiffInMinutes = TimeUnit.MILLISECONDS.toMinutes(realTime);

        TextView timeUntilArrival = (TextView) convertView.findViewById(R.id.timeUntilArrival);
        timeUntilArrival.setText(realDiffInMinutes+"");


        GridView flagGridView = (GridView) convertView.findViewById(R.id.flagGridView);
        // Use the adapter for setting all the flags to the list item.
        flagGridView.setAdapter(new LittleFlagAdapter(getContext(), vehicle.getFlags()));

        return convertView;
    }
}
