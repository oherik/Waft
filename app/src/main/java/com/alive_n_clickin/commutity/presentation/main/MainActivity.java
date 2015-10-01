package com.alive_n_clickin.commutity.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.infrastructure.VasttrafikAdapter;
import com.alive_n_clickin.commutity.presentation.flagreport.FlagVehicle;
import com.alive_n_clickin.commutity.presentation.search.SearchFragment;

/**
 * This class handles the first view presented to the user.
 */
public class MainActivity extends FragmentActivity {
    private final String LOG_TAG = FragmentActivity.class.getSimpleName();
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_activity);
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content_frame, mainFragment).commit();

        new Thread(new Runnable() {
            public void run() {
                VasttrafikAdapter a = new VasttrafikAdapter();
                Log.d("ASD", "" + a.getNearbyStations(11.978722,57.689061).toString());
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Starts the flag report activity
     * @param view THe current view
     */
    public void plusButtonOnClick(View view){
        Intent intent = new Intent(this,FlagVehicle.class);
        startActivity(intent);
    }

    /**
     * Switches to another view containing the search window and results
     * @param view The current view
     */
    public void switchToSearchFragment(View view){

        SearchFragment searchFragment = new SearchFragment();

        //Set arguments
        String currentStop = ((TextView) view.findViewById(R.id.currentStop)).getText().toString();
        Bundle args = new Bundle();
        args.putString(Intent.EXTRA_TEXT, currentStop);
        searchFragment.setArguments(args);

        //Start transaction. Replace the current view with the fragment.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_frame, searchFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
