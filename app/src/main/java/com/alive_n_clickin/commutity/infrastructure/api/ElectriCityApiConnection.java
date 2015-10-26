package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;

import com.alive_n_clickin.commutity.infrastructure.api.response.Response;

import java.io.IOException;
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
    private static final String BASE_URL_ELECTRICITY = "https://ece01.ericsson.net:4443/ecity";

    // Username and password, base64 encoded
    private static final String AUTHORIZATION = "Basic Z3JwOToza1B2MmlTU3Nu";

    /**
     * Send a get request to the ElectriCity API. The query will be appended to the base url and
     * sent using the credentials of the team. The query must be well formed.
     *
     * @param query the query to be appended to the base url. Leave out the '?' at the beginning.
     * @return a response object containing the response from the server.
     * @throws IOException if the query parameter is invalid, or if anything goes wrong with the
     * request.
     */
    public Response get(String query) throws IOException {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_ELECTRICITY).buildUpon();
        uriBuilder.encodedQuery(query);
        Uri uri = uriBuilder.build();

        URL url = new URL(uri.toString());

        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("Authorization", AUTHORIZATION));

        return ApiConnection.get(url, parameters);
    }
}
