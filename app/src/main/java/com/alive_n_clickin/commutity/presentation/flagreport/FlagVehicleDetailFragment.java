package com.alive_n_clickin.commutity.presentation.flagreport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alive_n_clickin.commutity.MyApplication;
import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.application.IManager;
import com.alive_n_clickin.commutity.domain.Flag;
import com.alive_n_clickin.commutity.domain.IFlag;
import com.alive_n_clickin.commutity.domain.IFlagType;
import com.alive_n_clickin.commutity.infrastructure.WifiHelper;

import lombok.AccessLevel;
import lombok.Getter;


/**
 * A class for showing the detailed view when flagging a vehicle. The view contains a flag, its name,
 * a comment field and a button for sending the flag. This fragment class handles then visual elements
 * of this.
 * @since 0.1
 */

public class FlagVehicleDetailFragment extends Fragment {
    @Getter(AccessLevel.PROTECTED) private final static String ARG_POSITION = "position";
    private int mCurrentPosition = -1;
    private IFlagType flagType;
    private IManager busManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        busManager = ((MyApplication) getActivity().getApplicationContext()).getManager();

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        // Layout inflation
        View view =  inflater.inflate(R.layout.fragment_flag_vehicle_detail,
                container, false);
        Button sendButton = (Button) view.findViewById(R.id.flagDetailSendButton);

        // Check if wifi is enabled, if not display a message, if it is, try sending the flag
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isWifiEnabled = new WifiHelper(getActivity()).isWifiEnabled();
                if (!isWifiEnabled) {
                    showEnableWifiAlert();
                } else {
                    trySendingFlag();
                }
            }
        });
        return view;
    }

    /**
     * Tries sending the flag data by calling on the http request class. If it's successful it
     * switches back to the previous view, otherwise it displays a message why it couldn't be sent.
     * At the moment the only possible error is that the comment is too short when submitting an
     * "other" flag, so a toast about this is displayed.
     */
    private void trySendingFlag() {
        View rootView = getActivity().findViewById(android.R.id.content);
        TextView commentView = (TextView) rootView.findViewById(R.id.flagDetailCommentField);
        String comment = commentView.getText().toString();

        IFlag flag;
        try {
            flag = new Flag(flagType, comment);
            FlagBusTask flagBusTask = new FlagBusTask();
            flagBusTask.execute(flag);
            switchToFlagFragment();
        } catch (IllegalArgumentException e) {
            // flag couldn't be created
            //TODO make the comment field blink or make some other non-intrusive indicator.
            Toast.makeText(getActivity().getApplicationContext(),
                    getString(R.string.flag_longer_comment_needed), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Alerts the user that  wifi need to be enabled.
     */
    private void showEnableWifiAlert() {
        TextView busInfo = (TextView) getView().findViewById(R.id.textViewBusInformation);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.enable_wifi_alert_title)
                .setMessage(R.string.enable_wifi_alert_message)
                .setPositiveButton(R.string.enable_wifi_alert_yesbutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new WifiHelper(getContext()).enableWifi();
                    }

                }).setNegativeButton(R.string.enable_wifi_alert_nobutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing
                    }
                })
                .show();
    }
    /**
     * Switches the view back to the main flag fragment
     */
    private void switchToFlagFragment(){
        //Hide keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        FragmentManager fm = getFragmentManager();
        fm.popBackStack();
    }

    @Override
    public void onStart() {
        super.onStart();

        //Get arguments sent by the starting class
        Bundle args = getArguments();

        //Set text
        View rootView           = getActivity().findViewById(android.R.id.content);
        TextView description    = (TextView) rootView.findViewById(R.id.flagDetailDescription);
        description.setText(args.getString("flag_description"));

        //Set images
        ImageView flagImageView = (ImageView) rootView.findViewById(R.id.flagDetailImage);
        int flagImageID         = args.getInt("flag_image_ID");
        Drawable flagImage      = getActivity().getResources().getDrawable(flagImageID);
        flagImageView.setImageDrawable(flagImage);


        //Set additional data
        flagType = Flag.Type.getByID(args.getInt("flag_type_ID"));
        //TODO add more data

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    private class FlagBusTask extends AsyncTask<IFlag, Void, Boolean> {

        @Override
        protected Boolean doInBackground(IFlag... params) {
            return busManager.addFlagToCurrentBus(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getContext(), R.string.flag_sent, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), R.string.flag_not_sent, Toast.LENGTH_LONG).show();
            }
        }
    }
}
