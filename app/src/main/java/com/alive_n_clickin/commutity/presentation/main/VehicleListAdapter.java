package com.alive_n_clickin.commutity.presentation.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.IArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.presentation.FlagImageView;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class fills the ListView within {@link MainFragment} with list items.
 *
 * @since 0.2
 */
public class VehicleListAdapter extends ArrayAdapter<IArrivingVehicle> {
    private int maxWidth;

    public VehicleListAdapter(Context currentContext, List<IArrivingVehicle> arrivingVehicleList) {
        super(currentContext,0, arrivingVehicleList); //The second parameter is the resource ID for a layout file containing a layout to use when instantiating views. Making it 0 means we are not sending any resource file to the super class.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            //false specifies to not attach to root ViewGroup.
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.vehicle_list_item, parent, false);
        }

        IArrivingVehicle vehicle = getItem(position);

        TextView busNumber = (TextView) convertView.findViewById(R.id.busNumber);
        busNumber.setText(vehicle.getShortRouteName());

        TextView targetDestination = (TextView) convertView.findViewById(R.id.targetDestination);
        targetDestination.setText(vehicle.getDestination());

        long realTime = vehicle.getTimeToArrival();
        long realDiffInMinutes = TimeUnit.MILLISECONDS.toMinutes(realTime);

        TextView timeUntilArrival = (TextView) convertView.findViewById(R.id.timeUntilArrival);
        timeUntilArrival.setText(realDiffInMinutes + " min");

        setFlags(convertView, maxWidth, vehicle);
        return convertView;
    }

    /*
    Helper method: take the view that should be created, and add flags to the gui element holding the flags.
     */
    private void setFlags(View convertView, int maxWidth, IArrivingVehicle vehicle) {

        GridLayout flagListView = (GridLayout) convertView.findViewById(R.id.flagListView);
        // Clear the flaglist, otherwise Androids reuse mechanism may preserve some flags, and we
        // will get duplicates
        flagListView.removeAllViews();

        int totalWidth = 0;
        //For every flag in our vehicle, add the flag image and keep track of the size
        //When te size reaches max, replace it with an ellipsis symbol
        for (IFlag flag : vehicle.getFlags()) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            final View flagContainer = inflater.inflate(R.layout.little_flag, null); //Container for the image
            FlagImageView flagImage = (FlagImageView) flagContainer.findViewById(R.id.littleFlagImageView); //The actual image
            flagImage.setFlag(flag);
            flagListView.addView(flagContainer);
        }
    }
}