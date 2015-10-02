package com.alive_n_clickin.commutity.presentation.main;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.infrastructure.WifiBroadcastReceiver;
import com.alive_n_clickin.commutity.infrastructure.WifiHelper;
import com.alive_n_clickin.commutity.infrastructure.api.Stop;
import com.alive_n_clickin.commutity.presentation.flagreport.FlagVehicle;
import com.alive_n_clickin.commutity.presentation.search.SearchFragment;

/**
 * This class handles the first view presented to the user.
 */
//TODO Make the UI a fragment to ease switching
public class MainFragment extends Fragment {
    private final String LOG_TAG = FragmentActivity.class.getSimpleName();
    private Stop currentStop;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        TextView stopTextView = (TextView) rootView.findViewById(R.id.currentStop);

        Bundle args = getArguments();

        //If a stop is received, set it as the current stop
        if(args!=null) {
            currentStop = (Stop) args.getSerializable(Intent.EXTRA_RETURN_RESULT);
            stopTextView.setText(currentStop.getName());
        }

        //Underline the text
        stopTextView.setPaintFlags(stopTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        return rootView;
    }
}
