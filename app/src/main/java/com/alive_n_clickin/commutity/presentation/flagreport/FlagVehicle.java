package com.alive_n_clickin.commutity.presentation.flagreport;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.alive_n_clickin.commutity.MyApplication;
import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.application.IManager;
import com.alive_n_clickin.commutity.domain.IVehicle;
import com.alive_n_clickin.commutity.event.CurrentBusChangeEvent;
import com.alive_n_clickin.commutity.event.WifiStateChangeEvent;
import com.alive_n_clickin.commutity.infrastructure.WifiBroadcastReceiver;
import com.alive_n_clickin.commutity.infrastructure.WifiHelper;
import com.alive_n_clickin.commutity.util.event.IEvent;
import com.alive_n_clickin.commutity.util.event.IObserver;

/**
 * The main activity for the flag setting tool. The activity doesn't have any visual elements itself,
 * besides a frame which contains different fragments. The activity extends FragmentActivity to ensure
 * that switching fragments is pain free.
 * @since 0.1
 */
public class FlagVehicle extends FragmentActivity implements IObserver {
    private final static String ARG_POSITION = "position";
    private int mCurrentPosition = -1;

    private ActionBar actionBar;
    private WifiHelper wifiHelper;

    private IManager busManager;
    private WifiBroadcastReceiver wifiBroadcastReceiver;

    private MenuItem wifiDisabledIcon;
    private MenuItem refreshIcon;


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_vehicle);
        // Register observers
        MyApplication application = (MyApplication) this.getApplicationContext();
        this.busManager = application.getManager();
        this.busManager.addObserver(this);
        this.wifiBroadcastReceiver = application.getWifiBroadcastReceiver();
        this.wifiBroadcastReceiver.addObserver(this);


        this.actionBar = this.getActionBar();
        this.wifiHelper = new WifiHelper(this);
        this.actionBar.setTitle(R.string.loading_looking_for_vehicle);
        updateWifiState();

        if (findViewById(R.id.content_frame) != null) {
            if (savedInstanceState != null) {
                return;
            }
            switchToDefaultFragment();
        }
    }

    private void updateBusText() {
        if (this.busManager.isOnBus()) {
            IVehicle bus = this.busManager.getCurrentBus();
            String newText = getCurrentBusAsString(bus);
            actionBar.setTitle(newText);
            displayWifiEnabledIcon(true);
        } else if (!wifiHelper.isWifiEnabled()) {
            actionBar.setTitle(R.string.you_must_activate_wifi);
            displayWifiEnabledIcon(false);
        } else {
            actionBar.setTitle(R.string.no_buses_near);
            displayWifiEnabledIcon(true);
        }
    }


    private String getCurrentBusAsString(IVehicle bus) {
        StringBuilder newText = new StringBuilder();
        newText.append(bus.getShortRouteName());
        newText.append(" ");
        newText.append(bus.getDestination());
        return newText.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_flag_vehicle, menu);
        this.wifiDisabledIcon = menu.findItem(R.id.wifi_disabled_icon);
        this.refreshIcon = menu.findItem(R.id.refresh_icon);
        refreshIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d("Testing","omaisneqwioneq");
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void updateWifiState() {
        if (wifiHelper.isWifiEnabled()) {
            wifiHelper.initiateWifiScan();
            if(wifiDisabledIcon != null && refreshIcon != null) {
                displayWifiEnabledIcon(true);
            }
        } else {
            actionBar.setTitle(R.string.you_must_activate_wifi);
        }
    }

    private void displayWifiEnabledIcon(boolean value) {
        if (value) {
            wifiDisabledIcon.setVisible(false);
            refreshIcon.setVisible(true);
        } else {
            wifiDisabledIcon.setVisible(true);
            refreshIcon.setVisible(false);
        }
    }

    /**
     * Switches the view to a new flag view fragment. It creates a new FlagVehicleFragment and
     * stores it in the fragment content frame.
     */
    private void switchToDefaultFragment(){
        FlagVehicleFragment flagFragment = new FlagVehicleFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, flagFragment).commit() ;
    }

    @Override
     public boolean onOptionsItemSelected (MenuItem item){
        //Default methods. Handles action bar clicks.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
