package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;
import android.util.Log;

import com.alive_n_clickin.commutity.infrastructure.api.response.Response;
import com.alive_n_clickin.commutity.util.LogUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a valid http connection for the Electricity API, which is then passed along to {@link ApiConnection}.
 *
 * ElectriCityApiConnection is package private, no need for higher layers to use it directly.
 *
 * @since 0.2
 */
class ElectriCityApiConnection {
    private static final String LOG_TAG = LogUtils.getLogTag(ElectriCityApiConnection.class);

    private static final String BASE_URL_ELECTRICITY = "https://ece01.ericsson.net:4443/ecity";

    // Username and password, base64 encoded
    private static final String AUTHORIZATION = "Basic Z3JwOToza1B2MmlTU3Nu";

    /**
     * Send a get request to the ElectriCity API. The query will be appended to the base url and
     * sent using the credentials of the team. The query must be well formed.
     *
     * @param query the query to be appended to the base url. Leave out the '?' at the beginning.
     * @return a response object containing the response from the server. Null if anything goes
     * wrong when fetching the response.
     */
    public Response get(String query) {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_ELECTRICITY).buildUpon();
        uriBuilder.encodedQuery(query);
        Uri uri = uriBuilder.build();

        URL url;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed URL", e);
            return null;
        }

        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("Authorization", AUTHORIZATION));

        return ApiConnection.get(url, parameters);
    }
}
