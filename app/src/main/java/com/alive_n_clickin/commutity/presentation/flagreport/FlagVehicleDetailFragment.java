package com.alive_n_clickin.commutity.presentation.flagreport;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.application.HttpRequest;
import com.alive_n_clickin.commutity.infrastructure.WifiHelper;


/**
 * A class for showing the detailed view when flagging a vehicle. The view contains a flag, its name,
 * a comment field and a button for sending the flag. This fragment class handles then visual elements
 * of this.
 */

public class FlagVehicleDetailFragment extends Fragment {
    final static String ARG_POSITION    = "position";
    int mCurrentPosition                = -1;
    private int flagTypeID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState  != null) {
            mCurrentPosition    = savedInstanceState.getInt(ARG_POSITION);
        }

        // Layout inflation
        View view               =  inflater.inflate(R.layout.fragment_flag_vehicle_detail,
                container, false);
        Button sendButton       = (Button) view.findViewById(R.id.flagDetailSendButton);

        //Check if wifi is enabled, if not display a message, if it is, try sending the flag
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isWifiEnabled = WifiHelper.getInstance().isWifiEnabled(getContext());
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

        //TODO change in the future so the http request can send an error code back
        //Send request
        if (setUpHttpRequest(flagTypeID, comment)) {
            //Request made, change back to the previous view
            switchToFlagFragment();
        } else {
            //Request couldn't be made due to the comment length not being satisfactory
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
                        WifiHelper.getInstance().enableWifi(getContext());
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
        FlagVehicleFragment flagFragment = new FlagVehicleFragment();
        Bundle args = new Bundle();
        args.putInt(FlagVehicleDetailFragment.ARG_POSITION, mCurrentPosition);
        flagFragment.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, flagFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
        flagTypeID = args.getInt("flag_type_ID");
        //TODO add more data

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    /**
     * Send the flag info to the server
     * @param flagTypeID  The type of flag
     * @param comment   The user submitted extra information
     * @return True if the flag could be sent, false otherwise (i.e. the comment was too short)
     */
    private boolean setUpHttpRequest(int flagTypeID, String comment){
        //If the query doesn't accept null
        if(comment == null){
            comment="";
        }

        //See if the flag can be sent
        if(HttpRequest.assertCommentLength(flagTypeID, comment)){
            //Call for a request
            AsyncHttpStarter sendRequest = new AsyncHttpStarter();
            sendRequest.execute(String.valueOf(flagTypeID), comment);
            return true;
        }
        return false;
    }

    /**
     * An async task handling the network connection, since this cannot be done on the main activity
     * thread. Accepts an URL and a query string. Calls for a controller to do the main work.
     */
    public class AsyncHttpStarter extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            HttpRequest request = new HttpRequest();
            return new Integer(request.postFlag(Integer.valueOf(params[0]), params[1]));
        }
        @Override
        protected void onPostExecute(Integer result){
            displayServerToast(result.intValue());
        }

        /**
         * What to do after the request, i.e. show a message if it has failed or not
         * @param serverResponseCode    The result from the background task
         */
        private void displayServerToast(Integer serverResponseCode) {
            int responseCode = serverResponseCode.intValue();
            String toastText;
            switch (responseCode){
                case 400:
                    toastText = getString(R.string.server_error)+ ": " + responseCode +
                            getString(R.string.flag_not_sent);
                    break;
                default:
                    toastText = getString(R.string.flag_sent);
                    break;
            }
            //Make toast to alert the user of this
            Toast.makeText(getActivity().getApplicationContext(), toastText,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
