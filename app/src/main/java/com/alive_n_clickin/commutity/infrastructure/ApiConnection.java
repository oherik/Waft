package com.alive_n_clickin.commutity.infrastructure;

import android.net.Uri;
import android.util.Log;

import com.alive_n_clickin.commutity.util.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * @author hjorthjort
 *         Created 22/09/15
 *
 * For connecting to different API:s. Allows abstraction by letting us send only the query parameters to the api we want, not bothering about authentication and the likes.
 */
public class ApiConnection {

    private static final String BASE_URL_ELECTRICITY = "https://ece01.ericsson.net:4443/ecity";
    //Username and password, base64 encoded
    private static final String AUTHORIZATION = "<YOUR API KEY>";

    /**
     * Send a query to electricity. It will be appended to the base url and sent using the credentials of the team
     * The query must be well formed
     * @param query the query to be appended. Leave out the '?' at the beginning
     * @return null if the connection didn't work
     * @throws MalformedURLException if the query parameter is invalid.
     */
    protected String sendGetToElectricity(String query) throws MalformedURLException {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_ELECTRICITY).buildUpon();
        uriBuilder.encodedQuery(query);
        Uri uri = uriBuilder.build();

        Log.e(LogUtils.getLogTag(this), uri.toString());

        HttpURLConnection connection = null;
        try {
            URL url = new URL(uri.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", AUTHORIZATION);
            return getResponseFromHttpConnection(connection);
        } catch (IOException e) {
            String errorMessage = "Error connection to Electricity API";
            if (connection != null) {
                errorMessage = readStream(connection.getErrorStream());
            }
            Log.e(LogUtils.getLogTag(this), errorMessage, e);
        }
        return null;
    }

    /**
     * Returns the response of a connection. Handles parsing and excpetions
     * @param connection the HttpURLConnection that the response should be read from
     * @return the response if there is one, null otherwise. Check log messages if null is returned
     */
    private String getResponseFromHttpConnection(HttpURLConnection connection) {
        try {
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            return readStream(inputStream);

        } catch (IOException e) {
            String errors = readStream(connection.getErrorStream());
            Log.e(LogUtils.getLogTag(this), errors, e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        //If we reached this statement, we have had an exception in the try block.
        return null;
    }

    /**
     * Reads the content of any InputStream and returns it as a String.
     * @param inputStream
     * @return
     */
    private String readStream(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        //By setting delimiter \A (which marks the beginning of the file) the Scanner read the whole file.
        return sc.useDelimiter("\\A").next();
    }
}
