package com.alive_n_clickin.commutity.presentation.arrival_list;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alive_n_clickin.commutity.application.CentralApplication;
import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.application.IManager;
import com.alive_n_clickin.commutity.domain.IArrivingVehicle;
import com.alive_n_clickin.commutity.domain.IStop;
import com.alive_n_clickin.commutity.presentation.flagreport.FlagVehicleActivity;
import com.alive_n_clickin.commutity.util.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.NonNull;

/**
 * This class handles the first view presented to the user. It holds the stop the user has selected,
 * as well as handling the switching to the search fragment when the user presses the stop text view.
 *
 * @since 0.1
 */

public class ArrivalListFragment extends Fragment {
    private int maxNumberOfBusesInList = 10;
    private TextView stopTextView;
    private ListView busListView;
    private VehicleListAdapter adapter;
    private List<IArrivingVehicle> arrivingVehicles;
    private IManager manager;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        arrivingVehicles = new ArrayList<>();
        manager = ((CentralApplication) getActivity().getApplicationContext()).getManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.arrival_list_fragment, container, false);
        stopTextView = (TextView) rootView.findViewById(R.id.currentStop);

        final ArrivalListActivity arrivalListActivity = (ArrivalListActivity) getActivity();
        IStop currentStop = arrivalListActivity.getCurrentStop();

        busListView = (ListView) rootView.findViewById(R.id.busListView);
        adapter = new VehicleListAdapter(getActivity(), arrivingVehicles);
        busListView.setAdapter(adapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener(){
           @Override
            public void onRefresh(){
               refreshBusList(arrivalListActivity, rootView);
           }
        });

        ImageView showPostFlagViewButton = (ImageView) rootView.findViewById(R.id.showPostFlagViewButton);
        showPostFlagViewButton.setOnClickListener(new ShowPostFlagViewButtonListener());

        setStopName(currentStop);
        populateBusList(currentStop, rootView);

        return rootView;
    }

    /**
     * Makes a new search on the current stop when the list is pulled down.
     */
    private void refreshBusList(ArrivalListActivity activity, View view){
        mSwipeRefreshLayout.setRefreshing(true);
        final IStop currentStop = activity.getCurrentStop();
        final View rootView = view;
        populateBusList(currentStop, rootView);

    }
    /**
     * This class purposes is to handle onClick events from the showPostFlagViewButton
     */
    private class ShowPostFlagViewButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), FlagVehicleActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Sets the list view to either visible or invisible, and then starts the class for adding
     * vehicles
     * @param currentStop The stop the displayed buses are headed to
     * @param view The view that's currently focused
     */
    private void populateBusList(IStop currentStop, @NonNull View view) {
        if (currentStop==null) {
            busListView.setVisibility(view.INVISIBLE);
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            busListView.setVisibility(view.VISIBLE);
            AddVehiclesFromAPI addVehicles = new AddVehiclesFromAPI();
            addVehicles.execute(currentStop);
        }
    }

    /**
     * Collects the vehicles from the adapter, based on which stop is currently selected.
     * After the class has fetched the results it adds the new data to the list adapter.
     */
    private class AddVehiclesFromAPI extends AsyncTask<IStop, Void, List<IArrivingVehicle>> {

        @Override
        protected List<IArrivingVehicle> doInBackground(IStop... params) {
            return manager.getVehicles(params[0]);
        }

        @Override
        protected void onPostExecute(List<IArrivingVehicle> result) {
            try {
                arrivingVehicles.clear();
            } catch(NullPointerException e) {
                //Arriving vehicles list has been deleted, create a new one
                Log.e(LogUtils.getLogTag(this), "The list of vehicles has been deleted. This list" +
                        " should always be present. Creating an empty one. \n" +
                        e.getStackTrace().toString());
                arrivingVehicles = new ArrayList<>();
            }
            if (result != null) {
                /*
                The recieved list is sorted based on the scheduled arrival time. We wish to sort
                based on the actual arrival time.
                 */
                Collections.sort(result);
                /*
                While making the API call it's not possible to select how many vehicles the list
                should contain. This has to be done manually.
                 */
                result = trimmedList(result, maxNumberOfBusesInList);
                adapter.clear();
                adapter.addAll(result);
                //Finish loading animation
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    /**
     * Set the list to a given maximum size
     * @param list
     * @param maxSize
     * @return The trimmed list, or an empty list if it was empty to begin with
     */
    private List trimmedList(@NonNull List list, int maxSize) {
        if (list.isEmpty()) {
            //List was empty, no need to trim
            return list;
        }
        //The toIndex is exclusive
        int toIndex = Math.min(maxSize, list.size());
        return list.subList(0, toIndex);
    }

    /**
     * Sets the stop name, as well as formatting the text
     * @param currentStop The stop to be displayed
     */
    private void setStopName(IStop currentStop) {
        if (currentStop!=null && !("".equals(currentStop.getName()))) {
            stopTextView.setText(currentStop.getName());
        }
        //stopTextView.setPaintFlags(stopTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}
