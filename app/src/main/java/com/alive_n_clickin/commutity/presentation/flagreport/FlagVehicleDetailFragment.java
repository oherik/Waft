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

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


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

                //Make toast to alert the user of this
                Toast.makeText(getActivity().getApplicationContext(), "Flag sent",
                        Toast.LENGTH_SHORT).show();

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
        String query            = String.format("flagType=%s&comment=%s",flagTypeID,comment);
        new SendHttpRequest().execute(ipAddress, query);
    }

    /**
     * An async task handling the network connection, since this cannot be done on the main activity
     * thread. Accepts an URL and a query string.
     */
    private class SendHttpRequest extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            //Get parameters
            String ipAddress        = urls[0];
            String query            = urls[1];

            //Define variables
            String charset          = "UTF-8";
            String contentType      = "application/x-www-form-urlencoded";
            try {
                //Get the url from the address
                URL url = new URL(ipAddress);

                //Convert the parameters to UTF-8
                byte[] bodyData       = query.getBytes(StandardCharsets.UTF_8);
                int    bodyDataLength = bodyData.length;

                //Set up server request
                HttpURLConnection serverConnection= (HttpURLConnection) url.openConnection();
                serverConnection.setDoOutput(true);
                serverConnection.setInstanceFollowRedirects(false);
                serverConnection.setRequestMethod("POST");
                serverConnection.setRequestProperty("Content-Type", contentType);
                serverConnection.setRequestProperty("charset", charset);
                serverConnection.setRequestProperty("Content-Length", Integer.toString(bodyDataLength));
                serverConnection.setUseCaches(false);

                //Send data
                DataOutputStream serverOutput = new DataOutputStream(serverConnection.getOutputStream());
                serverOutput.write(bodyData);

                //Log response code
                int status = serverConnection.getResponseCode();
                Log.v(LOG_TAG,"Response " + status);

                return String.valueOf(status);

            } catch(MalformedURLException e){
                Log.e(LOG_TAG, "Invalid URL. Current URL input was " + ipAddress +
                        ". Error message: " + e);
            } catch(IOException e){
                Log.e(LOG_TAG, "Could not connect to server. Error message: " +e);
            }
            return null;
        }
    }
}
