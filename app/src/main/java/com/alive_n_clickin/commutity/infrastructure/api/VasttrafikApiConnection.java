package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;
import android.util.Log;

import com.alive_n_clickin.commutity.util.LogUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class creates a valid http connection for the Vasttrafik API, which is then passed along to {@link ApiConnection}.
 *
 * The class is not meant to be instantiated directly. It's only used within {@link VasttrafikAdapter} to remove coupling.
 * @since 0.1
 */
class VasttrafikApiConnection {
    private static final String BASE_URL_VASTTRAFIK =
            "http://api.vasttrafik.se/bin/rest.exe/v1/";
    private static final String API_KEY = "<YOUR API KEY>";

    /**
     * Send a query to Vasttrafik. It will be appended to the base url and sent using the credentials of the team
     * The query must be well formed.
     * @param query the query to be appended. Leave out the '?' at the beginning
     * @return null if the connection didn't work
     * @throws MalformedURLException if the query parameter is invalid.
     */
    public String sendGetToVasttrafik(String path,String query) {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_VASTTRAFIK).buildUpon();
        uriBuilder.appendPath(path);
        uriBuilder.encodedQuery(query);
        uriBuilder.appendQueryParameter("authKey", API_KEY);
        uriBuilder.appendQueryParameter("format","json");

        Uri uri = uriBuilder.build();


        HttpURLConnection connection = null;
        try {
            URL url = new URL(uri.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            return ApiConnection.getResponseFromHttpConnection(connection);
        } catch (IOException e) {
            String errorMessage = "Error connection to Vasttrafik API";
            if (connection != null) {
                errorMessage = ApiConnection.readStream(connection.getErrorStream());
            }
            Log.e(LogUtils.getLogTag(this), errorMessage, e);
        }
        return null;
    }
}
