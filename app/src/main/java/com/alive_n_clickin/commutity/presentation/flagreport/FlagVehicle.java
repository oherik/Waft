package com.alive_n_clickin.commutity.presentation.flagreport;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.alive_n_clickin.commutity.R;
/**
 * The main activity for the flag setting tool. The activity doesn't have any visual elements itself,
 * besides a frame which contains different fragments. The activity extends FragmentActivity to ensure
 * that switching fragments is pain free.
 */
public class FlagVehicle extends FragmentActivity {

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_vehicle);
            if (findViewById(R.id.content_frame) != null) {
                if (savedInstanceState != null) {
                    return;
                }
                switchToDefaultFragment();
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
    public boolean onCreateOptionsMenu (Menu menu){
        //Default method. Creates a menu, if present
        getMenuInflater().inflate(R.menu.menu_flag_vehicle, menu);
        return true;
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
}
