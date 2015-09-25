package com.alive_n_clickin.commutity.application;

import android.util.Log;

import com.alive_n_clickin.commutity.presentation.flagreport.FlagVehicleDetailFragment;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * An class handling the network connection, since this cannot be done on the main activity
 * thread. Accepts an URL and a query string. The IP address of the server is hard coded into
 * this class
 */
public class HttpRequest {
    private final String LOG_TAG        = FlagVehicleDetailFragment.class.getSimpleName();

    //Set up server handling strings
    private final String baseIP         = "http://95.85.21.47/";
    private final String flags          = "flags";
    private final String charset        = "UTF-8";
    private final String contentType    = "application/x-www-form-urlencoded";

    /**
     * Posts a new flag to the server
     * @param flagTypeID    The type of flag
     * @param comment       The flag comment
     * @return The response code from the server, or -1 if the flag couldn't be sent
     */
    public int postFlag(int flagTypeID, String comment){
        String ipAddress = baseIP + flags;
        String query = String.format("flagType=%s&comment=%s", flagTypeID, comment);
        return post(ipAddress, query);
    }

    /**
     * Posts a http request to the server
     * @param serverAddress The full address for the location where to send the request
     * @param query The query to post in the body of the request
     * @return The response code from the server, or -1 if the request couldn't be sent
     */
    public int post(String serverAddress, String query) {
        try {
            //Get the url from the address
            URL url = new URL(serverAddress);

            //Convert the parameters to UTF-8
            byte[] bodyPostData     = query.getBytes(StandardCharsets.UTF_8);
            int bodyPostDataLength  = bodyPostData.length;

            //Set up server request
            HttpURLConnection serverConnection = (HttpURLConnection) url.openConnection();
            serverConnection.setDoOutput(true);
            serverConnection.setInstanceFollowRedirects(false);
            serverConnection.setRequestMethod("POST");
            serverConnection.setRequestProperty("Content-Type", contentType);
            serverConnection.setRequestProperty("charset", charset);
            serverConnection.setRequestProperty("Content-Length", Integer.toString(bodyPostDataLength));
            serverConnection.setUseCaches(false);

            //Send data
            DataOutputStream serverOutput = new DataOutputStream(serverConnection.getOutputStream());
            serverOutput.write(bodyPostData);

            //Log response code
            int status = serverConnection.getResponseCode();
            Log.v(LOG_TAG, "Response " + status);

            return status;
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Invalid URL. Current URL input was " + serverAddress +
                    ". Error message: " + e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Server connection error. Error message: " + e);
        }
        return -1; //Could not send request
    }
}
