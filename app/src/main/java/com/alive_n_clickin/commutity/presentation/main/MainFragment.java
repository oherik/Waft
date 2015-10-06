package com.alive_n_clickin.commutity.presentation.main;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.infrastructure.api.ArrivingVehicle;
import com.alive_n_clickin.commutity.infrastructure.api.Stop;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

/**
 * This class handles the first view presented to the user. It holds the stop the user has selected,
 * as well as handling the switching to the search fragment when the user presses the stop text view.
 * @since 0.1
 */

public class MainFragment extends Fragment {
    private int maxNumberOfBusesInList = 10;
    private TextView stopTextView;
    private ListView busListView;
    private ListAdapter adapter;

    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        stopTextView = (TextView) rootView.findViewById(R.id.currentStop);
        busListView = (ListView) rootView.findViewById(R.id.busListView);
        MainActivity mainActivity = (MainActivity) getActivity();
        Stop currentStop = mainActivity.getCurrentStop();
        //TODO add custom adapter
        List<ArrivingVehicle> adapterData = new ArrayList<>();

        //HARD CODE


        adapter = new ListAdapter(getActivity(),adapterData);
        busListView.setAdapter(adapter);

        setStopName(currentStop);
        populateBusList(currentStop, rootView);

        return rootView;
    }

    /**
     * Sets the buses in the bus list view, given the current stop
     * @param currentStop The stop the displayed buses are headed to
     * @param view The view that's currently focused
     */
    private void populateBusList(Stop currentStop, @NonNull View view){
        if(currentStop==null){
            busListView.setVisibility(view.INVISIBLE);
        } else {
            List<ArrivingVehicle> arrivingVehicles = getBuses(currentStop, maxNumberOfBusesInList);
            if(arrivingVehicles!=null && !arrivingVehicles.isEmpty()) {
                busListView.setVisibility(view.VISIBLE);
                adapter.clear();
                adapter.addAll(arrivingVehicles);
            }
        }
    }


        //TODO return type should be ArrayList<Bus>, but that class doesn't exist yet
    /**
     * Returns the different buses arriving to this stop
     * @param currentStop This bus stop
     * @param numberOfBuses The number of buses to return
     * @throw IllegalArgumentException if the number of buses is less
     * than one
     * @return The buses on the way to the desired stop, null if no buses are found
     */
    private List<ArrivingVehicle> getBuses(@NonNull Stop currentStop, int numberOfBuses)
            throws IllegalArgumentException{
        if(numberOfBuses<1){
            throw new IllegalArgumentException("Number of buses must be greater than or equal to " +
                    "one");
        }

        //TODO get data from bus manager
        List<ArrivingVehicle> testBuses = new ArrayList<>();
        testBuses.add(new ArrivingVehicle());
        /*testBuses.add("Tvåan");
        testBuses.add("Trean");
        testBuses.add("Fyran");
        testBuses.add("Femman");
        if(currentStop.getName().equals("GÖTEBORG")) {  //TODO test to see if update works
            testBuses.add("Sexan");
            testBuses.add("Sjuan");
            testBuses.add("Nian");
            testBuses.add("Tian");
            testBuses.add("Elvan");
            testBuses.add("Tolvan");
        }

        if(!testBuses.isEmpty()) {
            int maxIndex = Math.min(numberOfBuses, testBuses.size()) - 1;
            return testBuses.subList(0, maxIndex);
        }*/
        return testBuses;


     }

    /**
     * Sets the stop name, as well as formatting the text
     * @param currentStop The stop to be displayed
     */
    private void setStopName(Stop currentStop){
        if(currentStop!=null && !("".equals(currentStop.getName()))) {
            stopTextView.setText(currentStop.getName());
        }
        stopTextView.setPaintFlags(stopTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}
