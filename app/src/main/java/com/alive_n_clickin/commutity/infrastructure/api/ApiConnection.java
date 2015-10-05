package com.alive_n_clickin.commutity.infrastructure.api;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author hjorthjort
 *         Created 22/09/15
 *
 * For connecting to different API:s. Allows abstraction by letting us send only the query parameters to the api we want, not bothering about authentication and the likes.
 * ApiConnection is package private: no need for layers higher up to use it directly!
 * @since 0.1
 */
class ApiConnection {
    private final static String charset = "UTF-8";
    private final static String contentType = "application/x-www-form-urlencoded";
    private final static String LOG_TAG = "ApiConnection";
    /**
     * Returns the response of a connection. Handles parsing and exceptions
     * @param url takes the url to make a connection to
     * @return the response if there is one, null otherwise. Check log messages if null is returned
     */
    static String getResponseFromHttpConnection(URL url) {
        HttpURLConnection connection = establishGetConnection(url);

        if(connection != null){
            try {
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                if (inputStream == null) {
                    return null;
                }
                return readStream(inputStream);

            } catch (IOException e) {
                String errors = readStream(connection.getErrorStream());
            } finally {
                connection.disconnect();
            }
        }
        //If we reached this statement, we have had an exception in the try block.
        return null;
    }

    private static HttpURLConnection establishGetConnection(URL url){
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            return connection;
        } catch (IOException e) {
            String errorMessage = "Error establishing a connection";
            if (connection != null) {
                errorMessage = ApiConnection.readStream(connection.getErrorStream());
            }
            Log.e(LOG_TAG, errorMessage, e);
        }
        return null;
    }

    /**
     * Posts a http request to the server
     * @param url The full address for the location where to send the request
     * @param query The query to post in the body of the request
     * @return The response code from the server, or -1 if the request couldn't be sent
     */
    static int post(URL url,String query) {
        try {
            //Get the url from the address


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
            Log.e(LOG_TAG, "Invalid URL. Current URL input was " + url.toString() +
                    ". Error message: " + e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Server connection error. Error message: " + e);
        }
        return -1; //Could not send request
    }



    /**
     * Reads the content of any InputStream and returns it as a String.
     * @param inputStream
     * @return
     */
    static String readStream(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        //By setting delimiter \A (which marks the beginning of the file) the Scanner read the whole file.
        return sc.useDelimiter("\\A").next();
    }
}
