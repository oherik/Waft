package com.alive_n_clickin.commutity.presentation.main;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.ArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.infrastructure.api.ApiArrival;
import com.alive_n_clickin.commutity.presentation.FlagImageView;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class fills the ListView within {@link MainFragment} with list items.
 */
public class VehicleListAdapter extends ArrayAdapter<ApiArrival> {

    public VehicleListAdapter(Context currentContext, List<ApiArrival> apiArrivalList) {
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

        GridLayout flagListView = (GridLayout) convertView.findViewById(R.id.flagListView);
        flagListView.removeAllViews();
        // Use the adapter for setting all the flags to the list item.

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point displaySize = new Point();
        wm.getDefaultDisplay().getSize(displaySize);
        int maxWidth = parent.getMeasuredWidth();
        int currentWidth = 0;
        for (IFlag flag : vehicle.getFlags()) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            final View flagsView = inflater.inflate(R.layout.little_flag, null);
            FlagImageView flagImage = (FlagImageView) flagsView.findViewById(R.id.littleFlagImageView);
            flagImage.setFlag(flag);
            flagsView.measure(0, 0);
            int flagWidth = flagsView.getMeasuredWidth();
            currentWidth += flagsView.getMeasuredWidth();
            if (currentWidth <= maxWidth - flagWidth) {
                flagListView.addView(flagsView);
            } else {
                flagListView.removeView(flagsView);
                Drawable ellipsis = flagImage.getResources().getDrawable(R.drawable.ellipsis);
                flagImage.setImageDrawable(ellipsis);
                flagListView.addView(flagsView);
                break;
            }
        }

        return convertView;
    }
}