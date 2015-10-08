package com.alive_n_clickin.commutity.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.infrastructure.api.Stop;
import com.alive_n_clickin.commutity.presentation.flagreport.FlagVehicle;
import com.alive_n_clickin.commutity.presentation.search.SearchFragment;

import lombok.Getter;
import lombok.Setter;

/**
 * This class handles the first view presented to the user. It has a content frame to hold different
 * fragments.
 * @since 0.1
 */
public class MainActivity extends AppCompatActivity {
    @Getter @Setter private Stop currentStop;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_activity);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.xml.search);
        }


        //Set the main fragment as the first fragment presented to the user
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content_frame, mainFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_search, menu);

        return true;
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
