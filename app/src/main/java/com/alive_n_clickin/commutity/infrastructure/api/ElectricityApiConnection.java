package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;
import android.util.Log;

import com.alive_n_clickin.commutity.util.LogUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class creates a valid http connection for the Electricity API, which is then passed along to {@link ApiConnection}.
 *
 * ElectricityApiConnection is package private, no need for higher layers to use it directly.
 */
class ElectricityApiConnection {

    private static final String BASE_URL_ELECTRICITY = "https://ece01.ericsson.net:4443/ecity";
    //Username and password, base64 encoded
    private static final String AUTHORIZATION = "Basic Z3JwOToza1B2MmlTU3Nu";

    /**
     * Send a query to electricity. It will be appended to the base url and sent using the credentials of the team
     * The query must be well formed
     * @param query the query to be appended. Leave out the '?' at the beginning
     * @return null if the connection didn't work
     * @throws MalformedURLException if the query parameter is invalid.
     */
    protected String sendGetToElectricity(String query) {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_ELECTRICITY).buildUpon();
        uriBuilder.encodedQuery(query);
        Uri uri = uriBuilder.build();

        Log.d(LogUtils.getLogTag(this), uri.toString());

        HttpURLConnection connection = null;
        try {
            URL url = new URL(uri.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", AUTHORIZATION);
            return ApiConnection.getResponseFromHttpConnection(connection);
        } catch (IOException e) {
            String errorMessage = "Error connection to Electricity API";
            if (connection != null) {
                errorMessage = ApiConnection.readStream(connection.getErrorStream());
            }
            Log.e(LogUtils.getLogTag(this), errorMessage, e);
        }
        return null;
    }
}
