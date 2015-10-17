package com.alive_n_clickin.commutity.presentation.flagreport;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.alive_n_clickin.commutity.domain.IArrivingVehicle;

import java.util.List;

/**
 * Created by OscarEvertsson on 17/10/15.
 */
public class RemoveFlagAdapter extends ArrayAdapter<IArrivingVehicle>{
    private List<IArrivingVehicle> vehicleList;

    public RemoveFlagAdapter(Context currentContext, List<IArrivingVehicle> arrivingVehicleList) {
        super(currentContext,0, arrivingVehicleList); //The second parameter is the resource ID for a layout file containing a layout to use when instantiating views. Making it 0 means we are not sending any resource file to the super class.
    }




}
