package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;

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
    private static final String AUTHORIZATION = "<YOUR API KEY>";

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

        try {
            URL url = new URL(uri.toString());
            return ApiConnection.getResponseFromHttpConnection(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
