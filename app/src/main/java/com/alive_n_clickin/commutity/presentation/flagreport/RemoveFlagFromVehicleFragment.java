package com.alive_n_clickin.commutity.presentation.flagreport;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.IArrivingVehicle;

import java.util.ArrayList;
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
        FloatingActionButton showPostFlagViewButton = (FloatingActionButton) rootView.
                findViewById(R.id.showPostFlagViewButton);

        showPostFlagViewButton.setOnClickListener(new ShowPostFlagViewButtonListener());

        this.removeFlagAdapter = new RemoveFlagAdapter(getContext(), vehicleList);
        this.vehicleList = new ArrayList<>();
       // removeFlagListView.setAdapter(this.removeFlagAdapter);


        return rootView;
    }

    /**
     * This class purposes is to handle onClick events from the showPostFlagViewButton
     */
    private class ShowPostFlagViewButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            FlagVehicleFragment flagVehicleFragment = new FlagVehicleFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, flagVehicleFragment);
            fragmentTransaction.addToBackStack(null).commit();
        }
    }
}
