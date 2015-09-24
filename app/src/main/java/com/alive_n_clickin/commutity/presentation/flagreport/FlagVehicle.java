package com.alive_n_clickin.commutity.presentation.flagreport;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.alive_n_clickin.commutity.R;
/**
 * The main activity for the flag setting tool
 */
public class FlagVehicle extends FragmentActivity {
    private final String LOG_TAG = FragmentActivity.class.getSimpleName();

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_vehicle);
            if (findViewById(R.id.content_frame) != null) {
                if (savedInstanceState != null) {
                    return;
                }
                FlagVehicleFragment flagFragment = new FlagVehicleFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.content_frame, flagFragment).commit();
        }
    }


        @Override
        public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flag_vehicle, menu);
        return true;
    }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
