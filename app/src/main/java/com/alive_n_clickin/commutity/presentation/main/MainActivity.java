package com.alive_n_clickin.commutity.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import lombok.Setter;
import lombok.Getter;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.infrastructure.api.Stop;
import com.alive_n_clickin.commutity.presentation.flagreport.FlagVehicle;
import com.alive_n_clickin.commutity.presentation.search.SearchFragment;

/**
 * This class handles the first view presented to the user. It has a content frame to hold different
 * fragments.
 */
public class MainActivity extends FragmentActivity {
    @Getter @Setter private Stop currentStop;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_activity);

        //Set the main fragment as the first fragment presented to the user
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content_frame, mainFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Starts the flag report activity by sending an intent to the flag vehicle class
     * @param view The current view
     */
    public void plusButtonOnClick(View view){
        Intent intent = new Intent(this, FlagVehicle.class);
        startActivity(intent);
    }

    /**
     * Switches to another view containing the search window and results
     * @param view The current view
     */
    public void switchToSearchFragment(View view){
        SearchFragment searchFragment = new SearchFragment();

        //Start transaction. Replace the current view with the fragment.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_frame, searchFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
