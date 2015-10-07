package com.alive_n_clickin.commutity.presentation.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.ArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.infrastructure.api.ApiArrival;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class fills the ListView within {@link MainFragment} with list items. It takes help from the {@link LittleFlagAdapter} to set the flags.
 */
public class ListAdapter extends ArrayAdapter<ApiArrival> {

    public ListAdapter(Context currentContext,List<ApiArrival> apiArrivalList) {
        super(currentContext,0, apiArrivalList); //The second parameter is the resource ID for a layout file containing a layout to use when instantiating views. Making it 0 means we are not sending any resource file to the super class.
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
        timeUntilArrival.setText(realDiffInMinutes + "");


        LinearLayout flagContainer = (LinearLayout) convertView.findViewById(R.id.flagImageContainer);
        List<IFlag> flags = vehicle.getFlags();
        for(int i=0; i<flags.size();i++){
            if (i<5){
                new ImageView(getContext());
            } else if (i == 5){
                flagContainer.addView(new ImageView(getContext()));
            }
        }
        return convertView;
    }
}