package com.alive_n_clickin.commutity.presentation.flagreport;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.IArrivingVehicle;

import java.util.List;


public class RemoveFlagFromVehicleFragment extends Fragment {
    private List<IArrivingVehicle> vehicleList;

    private RemoveFlagAdapter removeFlagAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.remove_flag_fragment, container, false);
        ListView removeFlagListView = (ListView) rootView.findViewById(R.id.remove_flag_listView);

        this.removeFlagAdapter = new RemoveFlagAdapter(getContext(),vehicleList);
        removeFlagListView.setAdapter(this.removeFlagAdapter);


        return rootView;
    }
}
