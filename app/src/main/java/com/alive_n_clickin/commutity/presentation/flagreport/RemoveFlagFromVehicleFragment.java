package com.alive_n_clickin.commutity.presentation.flagreport;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alive_n_clickin.commutity.MyApplication;
import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.application.IManager;
import com.alive_n_clickin.commutity.domain.IElectriCityBus;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.util.event.CurrentBusChangeEvent;
import com.alive_n_clickin.commutity.util.event.IEvent;
import com.alive_n_clickin.commutity.util.event.IObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent the view where you can remove flags on the current vehicle you're on.
 */
public class RemoveFlagFromVehicleFragment extends Fragment implements IObserver{
    private List<IFlag> flagList;
    private IManager manager;

    private FlagsOnBusAdapter flagsOnBusAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register observers
        MyApplication application = (MyApplication) getActivity().getApplicationContext();
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
        View rootView = inflater.inflate(R.layout.remove_flag_fragment, container, false);

        FloatingActionButton showPostFlagViewButton = (FloatingActionButton) rootView.
                findViewById(R.id.showPostFlagViewButton);
        showPostFlagViewButton.setOnClickListener(new ShowPostFlagViewButtonListener());

        this.flagsOnBusAdapter = new FlagsOnBusAdapter(getContext(), flagList);
        ListView flagListView = (ListView) rootView.findViewById(R.id.remove_flag_listView);
        flagListView.setAdapter(this.flagsOnBusAdapter);

        return rootView;
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
        }
    }

    @Override
    public void onEvent(IEvent event) {
        if (event instanceof CurrentBusChangeEvent) {
            handleCurrentBusChangeEvent((CurrentBusChangeEvent) event);
        }
    }

    private void handleCurrentBusChangeEvent(CurrentBusChangeEvent event) {
        IElectriCityBus bus = event.getBus();
        if (bus != null) {
            updateFlags(manager.getCurrentBus().getFlags());
        } else {
            updateFlags(new ArrayList<IFlag>());
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
            fragmentTransaction.addToBackStack(null).commit();
        }
    }
}
