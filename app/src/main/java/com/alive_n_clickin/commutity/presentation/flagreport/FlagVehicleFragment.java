package com.alive_n_clickin.commutity.presentation.flagreport;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.alive_n_clickin.commutity.MyApplication;
import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.application.IManager;
import com.alive_n_clickin.commutity.domain.Flag;
import com.alive_n_clickin.commutity.domain.IVehicle;
import com.alive_n_clickin.commutity.event.CurrentBusChangeEvent;
import com.alive_n_clickin.commutity.event.WifiStateChangeEvent;
import com.alive_n_clickin.commutity.infrastructure.WifiBroadcastReceiver;
import com.alive_n_clickin.commutity.infrastructure.WifiHelper;
import com.alive_n_clickin.commutity.util.event.IEvent;
import com.alive_n_clickin.commutity.util.event.IObserver;

import java.util.ArrayList;

/**
 * The view with several different flags available for the user to flag vehicles. Launches a detailed
 * view when the user clicks on a flag
 * @since 0.1
 */
public class FlagVehicleFragment extends Fragment implements IObserver {
    private final static String ARG_POSITION = "position";
    private int mCurrentPosition = -1;

    private ActionBar actionBar;
    private WifiHelper wifiHelper;
    private MenuItem wifiDisabledIcon;
    private MenuItem refreshIcon;

    private IManager busManager;
    private WifiBroadcastReceiver wifiBroadcastReceiver;

    private FlagViewAdapter flagAdapter;
    private ArrayList<FlagButton> flagButtons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        actionBar = getActivity().getActionBar();
        wifiHelper = new WifiHelper(getActivity().getApplication().getApplicationContext());
        this.wifiDisabledIcon = (MenuItem) getActivity().findViewById(R.id.wifi_disabled_icon);
        this.refreshIcon = (MenuItem) getActivity().findViewById(R.id.refresh_icon);
        updateWifiState();
        addTestButtons();
    }

    public

    private void updateWifiState() {
        if (wifiHelper.isWifiEnabled()) {
            wifiHelper.initiateWifiScan();
            setRefreshIconEnabled(true);
        } else {
            setRefreshIconEnabled(false);
            actionBar.setTitle(R.string.you_must_activate_wifi);
        }
    }

    private void setRefreshIconEnabled(boolean value) {
        if (value) {
            wifiDisabledIcon.setVisible(false);
            refreshIcon.setVisible(true);
        } else {
            wifiDisabledIcon.setVisible(true);
            refreshIcon.setVisible(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Register observers
        MyApplication application = (MyApplication) this.getActivity().getApplicationContext();
        this.busManager = application.getManager();
        this.busManager.addObserver(this);
        this.wifiBroadcastReceiver = application.getWifiBroadcastReceiver();
        this.wifiBroadcastReceiver.addObserver(this);

        final View rootView = inflater.inflate(R.layout.fragment_flag_vehicle, container, false);
        flagAdapter = new FlagViewAdapter(getActivity(), flagButtons);

        this.actionBar.setTitle(R.string.loading_looking_for_vehicle);



        GridView flagGrid = (GridView) rootView.findViewById(R.id.flagGridView);
        flagGrid.setAdapter(flagAdapter);
        flagGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO Move all this to a background service that posts when it finds a bus.
                FlagButton button = flagAdapter.getItem(i);

                //Prepare arguments
                FlagVehicleDetailFragment detailFragment = new FlagVehicleDetailFragment();
                Bundle args = new Bundle();
                args.putInt(FlagVehicleDetailFragment.getARG_POSITION(), mCurrentPosition);

                //Add flag data
                args.putInt("flag_image_ID", button.getImageID());
                args.putString("flag_description", button.getDescription());
//                args.putString("bus_data", currentBus.getDGW());
                args.putInt("flag_type_ID", button.getType().getId());
                detailFragment.setArguments(args);

                //Switch view
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().
                        beginTransaction();
                transaction.replace(R.id.content_frame, detailFragment);
                transaction.addToBackStack(null);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        this.busManager.removeObserver(this);
        this.wifiBroadcastReceiver.removeObserver(this);
    }

    private void updateBusText() {
        if (this.busManager.isOnBus()) {
            IVehicle bus = this.busManager.getCurrentBus();
            String newText = getCurrentBusAsString(bus);
            actionBar.setTitle(newText);
            setRefreshIconEnabled(true);
        } else if (!wifiHelper.isWifiEnabled()) {
            actionBar.setTitle(R.string.you_must_activate_wifi);
            setRefreshIconEnabled(false);
        } else {
            actionBar.setTitle(R.string.no_buses_near);
            setRefreshIconEnabled(true);
        }
    }

    private String getCurrentBusAsString(IVehicle bus) {
        StringBuilder newText = new StringBuilder();
        newText.append(bus.getShortRouteName());
        newText.append(" ");
        newText.append(bus.getDestination());
        return newText.toString();
    }


    /**
     * Adds the different buttons. At the moment it's all hard coded for testing purposes
     */
    private void addTestButtons(){
        //TODO hard coded flag data for testing purposes
        if(flagButtons==null){
            flagButtons = new ArrayList<>();
        }
        flagButtons.add(new FlagButton(R.drawable.flag_full_300px, getString(R.string.flag_overcrowded), Flag.Type.OVERCROWDED));
        flagButtons.add(new FlagButton(R.drawable.flag_rowdy_300px, getString(R.string.flag_disturbance), Flag.Type.DISTURBANCES));
        flagButtons.add(new FlagButton(R.drawable.flag_delayed_300px, getString(R.string.flag_delayed), Flag.Type.DELAYED));
        flagButtons.add(new FlagButton(R.drawable.flag_dirty_alt2_300px, getString(R.string.flag_messy), Flag.Type.MESSY));
        flagButtons.add(new FlagButton(R.drawable.flag_pram_300px, getString(R.string.flag_pram), Flag.Type.NO_PRAMS));
        flagButtons.add(new FlagButton(R.drawable.flag_other_black_300px, getString(R.string.flag_other), Flag.Type.OTHER));
    }

    @Override
    public void onEvent(IEvent event) {
        if (event instanceof CurrentBusChangeEvent) {
            handleCurrentBusChangeEvent((CurrentBusChangeEvent) event);
        } else if (event instanceof WifiStateChangeEvent) {
            handleWifiStateChangeEvent((WifiStateChangeEvent) event);
        }
    }

    private void handleCurrentBusChangeEvent(CurrentBusChangeEvent event) {
        this.updateBusText();
    }

    private void handleWifiStateChangeEvent(WifiStateChangeEvent event) {
        this.updateBusText();
    }
}
