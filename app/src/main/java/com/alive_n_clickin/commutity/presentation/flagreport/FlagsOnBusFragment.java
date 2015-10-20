package com.alive_n_clickin.commutity.presentation.flagreport;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alive_n_clickin.commutity.MyApplication;
import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.application.IManager;
import com.alive_n_clickin.commutity.domain.IElectriCityBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.util.event.CantSearchForVehiclesEvent;
import com.alive_n_clickin.commutity.util.event.CurrentBusChangeEvent;
import com.alive_n_clickin.commutity.util.event.IEvent;
import com.alive_n_clickin.commutity.util.event.IObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent the view where you can see the current flags for the vehicle you're on.
 */
public class FlagsOnBusFragment extends Fragment implements IObserver{
    private List<IFlag> flagList;
    private IManager manager;
    private MyApplication application;
    private FlagsOnBusAdapter flagsOnBusAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register as an observer
        this.application = (MyApplication) getActivity().getApplicationContext();
        this.manager = application.getManager();
        this.manager.addObserver(this);
        if (this.manager.isOnBus()) {
            this.flagList = new ArrayList<>(this.manager.getCurrentBus().getFlags());
        } else {
            this.flagList = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.flags_on_bus_fragment, container, false);

        FloatingActionButton showPostFlagViewButton = (FloatingActionButton) rootView.
                findViewById(R.id.showPostFlagViewButton);
        showPostFlagViewButton.setOnClickListener(new ShowPostFlagViewButtonListener());

        this.flagsOnBusAdapter = new FlagsOnBusAdapter(getContext(), flagList, this);
        ListView flagListView = (ListView) rootView.findViewById(R.id.remove_flag_listView);
        flagListView.setAdapter(this.flagsOnBusAdapter);


        return rootView;
    }

    /**
     * This method creates a new DeleteFlag object and executes the delete request with the provided flag object.
     * @param flag the flag to delete
     */
    protected void deleteFlag(IFlag flag){
        new DeleteFlag().execute(flag);
    }

    /**
     * This class handles sending a delete flag to the manager asynchronously,
     * and updates the current list of flags depending on successful or not.
     */
    private class DeleteFlag extends AsyncTask<IFlag, Void, Boolean> {
        private IFlag flag;

        @Override
        protected Boolean doInBackground(IFlag... params) {
            this.flag = params[0];
            return manager.deleteFlag(this.flag);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                flagList.remove(flag);
                flagsOnBusAdapter.notifyDataSetChanged();
            } else {
                Toast toast = Toast.makeText(application, R.string.could_not_delete, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    /**
     * This method handles updating the current adapter with a new list of flags.
     * @param flagList the new list to be displayed.
     */
    protected void updateFlags(List<IFlag> flagList) {
        if(this.flagList == null) {
            this.flagList = new ArrayList<>();
        }
        this.flagList.clear();
        this.flagList.addAll(flagList);

        if(flagsOnBusAdapter != null) {
            flagsOnBusAdapter.clear();
            flagsOnBusAdapter.addAll(flagList);
            flagsOnBusAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onEvent(IEvent event) {
        if (event instanceof CurrentBusChangeEvent) {
            handleCurrentBusChangeEvent((CurrentBusChangeEvent) event);
        } else if (event instanceof CantSearchForVehiclesEvent) {
            handleCantSearchForVehiclesEvent((CantSearchForVehiclesEvent) event);
        }
    }

    /**
     * Handler for the CantSearchForVehicles, this method clears the list of flags since the manager doesn't know if you're on a bus or not.
     */
    private void handleCantSearchForVehiclesEvent(CantSearchForVehiclesEvent event) {
        this.flagsOnBusAdapter.clear();
    }

    /**
     * Handles the busChangeEvent. If an bus exist the updateFlags method is called with the flags for the bus,
     * otherwise updateFlags is called with an empty list of flags.
     * @param event the event to handle.
     */
    private void handleCurrentBusChangeEvent(CurrentBusChangeEvent event) {
        IElectriCityBus bus = event.getBus();
        if (bus != null) {
            updateFlags(manager.getCurrentBus().getFlags());
        } else {
            this.flagsOnBusAdapter.clear();
        }
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
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(null).commit();
        }
    }
}
