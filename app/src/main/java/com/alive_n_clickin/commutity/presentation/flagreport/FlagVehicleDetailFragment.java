package com.alive_n_clickin.commutity.presentation.flagreport;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erik.commutity.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 *
 */
/*
 Use the {@link FlagVehicleDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlagVehicleDetailFragment extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    private final String LOG_TAG = FlagVehicleDetailFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_flag_vehicle_detail, container, false);
        Button sendButton = (Button) view.findViewById(R.id.flagDetailSendButton);
        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getActivity().getApplicationContext(), "Test ok",
                        Toast.LENGTH_SHORT).show();
                //TODO send request here

                //Change back to the previous view
                FlagVehicleFragment flagFragment = new FlagVehicleFragment();
                Bundle args = new Bundle();
                args.putInt(FlagVehicleDetailFragment.ARG_POSITION, mCurrentPosition);
                flagFragment.setArguments(args);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, flagFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state define
        }
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    public void setFlagData(FlagButton button, String busData){

    }

}
