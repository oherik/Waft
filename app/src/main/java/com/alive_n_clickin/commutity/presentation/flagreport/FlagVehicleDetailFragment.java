package com.alive_n_clickin.commutity.presentation.flagreport;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erik.commutity.R;

/**
 * A class for showing the detailed view when flagging a vehicle
 */

public class FlagVehicleDetailFragment extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    private final String LOG_TAG = FlagVehicleDetailFragment.class.getSimpleName(); //TODO for error printing

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        // Layout inflation
        View view =  inflater.inflate(R.layout.fragment_flag_vehicle_detail, container, false);
        Button sendButton = (Button) view.findViewById(R.id.flagDetailSendButton);
        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                View rootView = getActivity().findViewById(android.R.id.content);
                TextView description = (TextView) rootView.findViewById(R.id.flagDetailDescription);
                String toastText = description.getText().toString();

                Toast.makeText(getActivity().getApplicationContext(), "Flag sent: " + toastText,
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
        //Get arguments sent by the starting class
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
        }
        View rootView = getActivity().findViewById(android.R.id.content);
        //Set data

        TextView description = (TextView) rootView.findViewById(R.id.flagDetailDescription);
        description.setText(args.getString("flag_description"));
        ImageView flagImageView = (ImageView) rootView.findViewById(R.id.flagDetailImage);
        int flagImageID = args.getInt("flag_image_ID");
        Log.e(LOG_TAG, "FFASDASDASD " + flagImageID);
        Drawable flagImage = getActivity().getResources().getDrawable(flagImageID);
        flagImageView.setImageDrawable(flagImage);
        //TODO add more data

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}
