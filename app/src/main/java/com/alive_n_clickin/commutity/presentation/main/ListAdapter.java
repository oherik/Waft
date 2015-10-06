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

import java.util.Calendar;
import java.util.List;

/**
 * Created by OscarEvertsson on 06/10/15.
 */
public class ListAdapter extends ArrayAdapter<ArrivingVehicle> {
    private Context currentContext;
    private List<ArrivingVehicle> busList;

    public ListAdapter(Context currentContext,List<ArrivingVehicle> arrivingVehicleList) {
        super(currentContext,0,arrivingVehicleList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ArrivingVehicle arrivingVehicle = getItem(position);
        IBus bus = arrivingVehicle.getBus();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.vehicle_list, parent, false);
        }

        TextView busNumber = (TextView) convertView.findViewById(R.id.busNumber);
        busNumber.setText(bus.getRouteNumber());

        TextView targetDestination = (TextView) convertView.findViewById(R.id.targetDestination);
        targetDestination.setText(bus.getDestination());


        TextView timeUntilArrival = (TextView) convertView.findViewById(R.id.timeUntilArrival);
        //TODO fetch the actual arrival time
        String formatedTime = arrivingVehicle.getTimeToArrival().get(Calendar.HOUR) +
                " : " +
                arrivingVehicle.getTimeToArrival().get(Calendar.MINUTE);
        timeUntilArrival.setText(formatedTime);

        GridView flagGridView = (GridView) convertView.findViewById(R.id.flagGridView);
        //TODO Add all flags.

        return convertView;
    }
}
