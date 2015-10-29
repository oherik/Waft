package com.alive_n_clickin.commutity.presentation.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.domain.IStop;
import com.alive_n_clickin.commutity.presentation.search.SearchFragment;

import lombok.Getter;
import lombok.Setter;

/**
 * This class handles the first view presented to the user. It has a content frame to hold different
 * fragments.
 *
 * @since 0.1
 */
public class MainActivity extends FragmentActivity {
    @Getter @Setter private IStop currentStop;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_activity);

        //Set the main fragment as the first fragment presented to the user
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content_frame, mainFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }
}
