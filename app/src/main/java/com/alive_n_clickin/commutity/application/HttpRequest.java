package com.alive_n_clickin.commutity.application;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.alive_n_clickin.commutity.presentation.flagreport.FlagVehicleDetailFragment;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


/**
 * An async task handling the network connection, since this cannot be done on the main activity
 * thread. Accepts an URL and a query string.
 */
public class HttpRequest {
    private final String LOG_TAG        = FlagVehicleDetailFragment.class.getSimpleName();
    private int serverResponseCode;
    private final String baseIP = "http://95.85.21.47";

    /**
     * Posts a new flag to the server
     * @param flagTypeID    The type of flag
     * @param comment       The flag comment
     */
    public void postFlag(int flagTypeID, String comment){
        String ipAddress = baseIP + "/flags";
        String query = String.format("flagType=%s&comment=%s", flagTypeID, comment);
        post(ipAddress, query);
    }

    /**
     * Posts a http request to the server
     * @param ipAddress The ip address, including /flags or similar
     * @param query The query
     */
    public void post(String ipAddress, String query) {
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
            Log.v(LOG_TAG, "Response " + status);

            this.serverResponseCode = status;

        } catch(MalformedURLException e){
            Log.e(LOG_TAG, "Invalid URL. Current URL input was " + ipAddress +
                    ". Error message: " + e);
        } catch(IOException e){
            Log.e(LOG_TAG, "Could not connect to server. Error message: " +e);
        }
    }

    public int getServerResponseCode(){
        if(serverResponseCode==0){
            Log.e(LOG_TAG, "No server code stored, have you made a request?");
        }
        return serverResponseCode;
    }

}
