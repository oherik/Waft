package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;
import android.util.Log;

import com.alive_n_clickin.commutity.infrastructure.api.response.Response;
import com.alive_n_clickin.commutity.util.LogUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class creates a valid http connection for the Vasttrafik API, which is then passed along to {@link ApiConnection}.
 *
 * @since 0.1
 */
class VasttrafikApiConnection {
    private static final String LOG_TAG = LogUtils.getLogTag(VasttrafikApiConnection.class);

    private static final String BASE_URL_VASTTRAFIK = "http://api.vasttrafik.se/bin/rest.exe/v1/";
    private static final String API_KEY = "69b13ace-0bc1-4203-8a56-cc95648f4dca";

    /**
     * Send a get request to Vasttrafik. The query will be appended to the base url and sent using
     * the credentials of the team.
     *
     * @param path the path to be appended to the base url.
     * @param query the query to be appended to the url given by the base url + the specified path.
     *              Leave out the '?' at the beginning
     * @return a response object containing the response from the server.
     * @throws IOException if the query parameter is invalid, or if anything goes wrong with the
     * request.
     */
    public Response get(String path, String query) {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_VASTTRAFIK).buildUpon();
        uriBuilder.appendPath(path);
        uriBuilder.encodedQuery(query);
        uriBuilder.appendQueryParameter("authKey", API_KEY);
        uriBuilder.appendQueryParameter("format", "json");
        Uri uri = uriBuilder.build();

        URL url;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed URL", e);
            return null;
        }

        return ApiConnection.get(url);
    }
}
