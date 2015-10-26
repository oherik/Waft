package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * This class creates a valid http connection for the Electricity API, which is then passed along to {@link ApiConnection}.
 *
 * ElectriCityApiConnection is package private, no need for higher layers to use it directly.
 *
 * @since 0.2
 */
class ElectriCityApiConnection {
    private static final String BASE_URL_ELECTRICITY = "https://ece01.ericsson.net:4443/ecity";
    // Username and password, base64 encoded
    private static final String AUTHORIZATION = "<YOUR API KEY>";

    /**
     * Send a query to electricity. It will be appended to the base url and sent using the credentials of the team
     * The query must be well formed
     * @param query the query to be appended. Leave out the '?' at the beginning
     * @return null if the connection didn't work
     * @throws MalformedURLException if the query parameter is invalid.
     */
    public String sendGetToElectricity(String query) {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_ELECTRICITY).buildUpon();
        uriBuilder.encodedQuery(query);
        Uri uri = uriBuilder.build();

        try {
            URL url = new URL(uri.toString());
            //Send the authorization to the Api connection
            Map.Entry<String, String> authorizationProperty = new Map.Entry<String, String>() {
                @Override
                public String getKey() {
                    return "Authorization";
                }

                @Override
                public String getValue() {
                    return AUTHORIZATION;
                }

                @Override
                public String setValue(String object) {
                    return null;
                }
            };
            return ApiConnection.getResponseFromHttpConnection(url, authorizationProperty);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
