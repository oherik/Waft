package com.alive_n_clickin.commutity.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.infrastructure.WifiBroadcastReceiver;
import com.alive_n_clickin.commutity.infrastructure.WifiHelper;
import com.alive_n_clickin.commutity.presentation.flagreport.FlagVehicle;
import com.alive_n_clickin.commutity.presentation.search.SearchFragment;

/**
 * This class handles the first view presented to the user.
 */
//TODO Make the UI a fragment to ease switching
public class MainFragment extends Fragment {
    private final String LOG_TAG = FragmentActivity.class.getSimpleName();
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        /*
        MainActivity mainActivity = (MainActivity) getActivity();
        TextView stopTextView = (TextView) container.findViewById(R.id.currentStop);
        if(mainActivity.getCurrentStop()!=null) {
            stopTextView.setText(mainActivity.getCurrentStop().getName());
        }
         */
        Bundle args = getArguments();
        if(args!=null) {
            long result = args.getLong(Intent.EXTRA_RETURN_RESULT);
            TextView stopTextView = (TextView) rootView.findViewById(R.id.currentStop);
            stopTextView.setText(String.valueOf(result));
        }
        return rootView;
    }
}
