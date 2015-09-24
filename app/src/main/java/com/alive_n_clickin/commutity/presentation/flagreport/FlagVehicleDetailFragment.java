package com.alive_n_clickin.commutity.presentation.flagreport;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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

import com.alive_n_clickin.commutity.R;
import com.alive_n_clickin.commutity.application.HttpPostRequest;


/**
 * A class for showing the detailed view when flagging a vehicle
 */

public class FlagVehicleDetailFragment extends Fragment {
    final static String ARG_POSITION    = "position";
    int mCurrentPosition                = -1;
    private final String LOG_TAG        = FlagVehicleDetailFragment.class.getSimpleName();
    int flagTypeID;


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
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View rootView = getActivity().findViewById(android.R.id.content);
                TextView commentView = (TextView) rootView.findViewById(R.id.flagDetailCommentField);

                //Send request
                setUpHttpRequest(commentView.getText().toString(), flagTypeID);

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

        //Set images, text etc
        View rootView           = getActivity().findViewById(android.R.id.content);
        TextView description    = (TextView) rootView.findViewById(R.id.flagDetailDescription);
        description.setText(args.getString("flag_description"));
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
     * @param comment   The user submitted extra information
     * @param flagTypeID  The type of flag
     */
    private void setUpHttpRequest(String comment, int flagTypeID){
        //If the query doesn't accept null
        if(comment == null){
            comment="";
        }

        //Set up http client
        String ipAddress        = "http://95.85.21.47/flags";    //TODO store somewhere else?
        String query            = String.format("flagType=%s&comment=%s", flagTypeID, comment);

        //Call for a request
        AsyncHttpStarter sendRequest = new AsyncHttpStarter();
        sendRequest.execute(ipAddress, query);
    }

    /**
     * An async task handling the network connection, since this cannot be done on the main activity
     * thread. Accepts an URL and a query string. Calls for a controller to do the main work.
     */
    public class AsyncHttpStarter extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            HttpPostRequest request = new HttpPostRequest();
            request.sendRequest(params);
            return new Integer(request.getServerResponseCode());
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
                    Log.e(LOG_TAG, "Server error " + responseCode);
                    toastText = "Server error " + responseCode + ". Could not send flag.";
                    break;
                default:
                    toastText = "Flag sent.";
                    break;
            }
            //Make toast to alert the user of this
            Toast.makeText(getActivity().getApplicationContext(), toastText,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
