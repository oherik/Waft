package com.alive_n_clickin.commutity.presentation.main;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lombok.Getter;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.infrastructure.api.Stop;

/**
 * This class handles the first view presented to the user. It holds the stop the user has selected,
 * as well as handling the switching to the search fragment when the user presses the stop text view.
 */

public class MainFragment extends Fragment {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        TextView stopTextView = (TextView) rootView.findViewById(R.id.currentStop);

        //Set stop name
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity.getCurrentStop()!=null && !("".equals(mainActivity.getCurrentStop().getName())))
        {
            stopTextView.setText(mainActivity.getCurrentStop().getName());
        }

        //Underline the text
        stopTextView.setPaintFlags(stopTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        return rootView;
    }
}
