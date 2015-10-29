package com.alive_n_clickin.waft.presentation.flagreport;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alive_n_clickin.waft.R;
import com.alive_n_clickin.waft.application.CantSearchForVehiclesEvent;
import com.alive_n_clickin.waft.application.CentralApplication;
import com.alive_n_clickin.waft.application.CurrentBusChangeEvent;
import com.alive_n_clickin.waft.application.IManager;
import com.alive_n_clickin.waft.domain.IElectriCityBus;
import com.alive_n_clickin.waft.util.event.IEvent;
import com.alive_n_clickin.waft.util.event.IObserver;

/**
 * The main activity for the flag setting tool. The activity doesn't have any visual elements itself,
 * besides a frame which contains different fragments. The activity extends FragmentActivity to ensure
 * that switching fragments is pain free.
 *
 * @since 0.1
 */
public class FlagVehicleActivity extends AppCompatActivity implements IObserver {
    private IManager manager;

    private ActionBar actionBar;
    private MenuItem wifiDisabledIcon;
    private MenuItem refreshIcon;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_vehicle);

        // Register observers
        CentralApplication application = (CentralApplication) this.getApplicationContext();
        this.manager = application.getManager();
        this.manager.addObserver(this);

        this.actionBar = this.getActionBar();

        if (findViewById(R.id.content_frame) != null) {
            if (savedInstanceState != null) {
                return;
            }
            switchToDefaultFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_flag_vehicle, menu);

        this.wifiDisabledIcon = menu.findItem(R.id.wifi_disabled_icon);
        this.refreshIcon = menu.findItem(R.id.refresh_icon);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if (!manager.canSearch()) {
            setActionBarTextCantSearch();
        } else if (!manager.isOnBus()) {
            manager.searchForVehicles();
            setActionBarTextSearching();
        } else {
            setActionBarTextToBus(manager.getCurrentBus());
        }

        return true;
    }

    /**
     * Sets the action bar text to R.string.loading_looking_for_vehicle.
     */
    private void setActionBarTextSearching() {
        actionBar.setTitle(R.string.loading_looking_for_vehicle);
        displayRefreshIcon();
    }

    /**
     * Sets the action bar text to the sent in bus.
     *
     * @param currentBus the bus to set the action bar text to.
     */
    private void setActionBarTextToBus(IElectriCityBus currentBus) {
        String busString = currentBus.getShortRouteName() + " " + currentBus.getDestination();
        actionBar.setTitle(busString);
        displayRefreshIcon();
    }

    /**
     * Sets the action bar text to R.string.no_buses_near.
     */
    private void setActionBarTextNotOnBus() {
        actionBar.setTitle(R.string.no_buses_near);
        displayRefreshIcon();
    }

    /**
     * Sets the action bar text to R.string.cant_search.
     */
    private void setActionBarTextCantSearch() {
        actionBar.setTitle(R.string.enable_wifi_alert_title);
        displayEnableWifiIcon();
    }

    /**
     * Helper method to easily display the "wifi disabled" icon.
     */
    private void displayEnableWifiIcon() {
        wifiDisabledIcon.setVisible(true);
        refreshIcon.setVisible(false);
    }

    /**
     * Helper method to easily display the "refresh" icon.
     */
    private void displayRefreshIcon() {
        wifiDisabledIcon.setVisible(false);
        refreshIcon.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.wifi_disabled_icon:
                manager.searchForVehicles();
                setActionBarTextSearching();
                return true;
            case R.id.refresh_icon:
                manager.searchForVehicles();
                setActionBarTextSearching();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Switches the view to a new flag view fragment. It creates a new FlagsOnBusFragment and
     * stores it in the fragment content frame.
     */
    private void switchToDefaultFragment(){
        FlagsOnBusFragment flagFragment = new FlagsOnBusFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, flagFragment).commit();
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
     * Handles a CurrentBusChangeEvent. If event.getBus() returns null, setActionBarTextNotOnBus()
     * is called, otherwise setActionBarTextToBus(bus) is called with the bus from the event.
     *
     * @param event the event to handle.
     */
    private void handleCurrentBusChangeEvent(CurrentBusChangeEvent event) {
        IElectriCityBus bus = event.getBus();
        if (bus == null) {
            setActionBarTextNotOnBus();
        } else {
            setActionBarTextToBus(bus);
        }
    }

    /**
     * Handles a CantSearchForVehiclesEvent. Calls setActionBarTextCantSearch().
     *
     * @param event the event to handle.
     */
    private void handleCantSearchForVehiclesEvent(CantSearchForVehiclesEvent event) {
        setActionBarTextCantSearch();
    }

    @Override
    public void onBackPressed() {
        /*
        The system back button doesn't automatically pop the fragment stack, causing the
        activity to close if the desired behaviour is not specified.
         */
        //Hide the keyboard if it is currently shown
        View currentView = getCurrentFocus();
        if (currentView != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
        }
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
