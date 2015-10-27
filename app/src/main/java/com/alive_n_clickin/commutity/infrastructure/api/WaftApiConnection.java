package com.alive_n_clickin.commutity.infrastructure.api;

import android.net.Uri;
import android.util.Log;

import com.alive_n_clickin.commutity.infrastructure.api.response.Response;
import com.alive_n_clickin.commutity.util.LogUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class creates a valid http connection for our backend, which is then passed along to {@link ApiConnection}.
 *
 * @since 0.2
 */
class WaftApiConnection {
    private static final String LOG_TAG = LogUtils.getLogTag(WaftApiConnection.class);

    private static final String BASE_URL_WAFT = "http://95.85.21.47/";
    private static final String FLAGS_PATH = "flags";
    private static final String DELETE_PATH = "delete";

    /**
     * Send a get request to the Waft API. The query will be appended to the base url.
     *
     * @param path the path to be appended to the base url.
     * @param query the query to be appended to the url given by the base url + the specified path.
     *              Leave out the '?' at the beginning
     * @return a response object containing the response from the server.
     * @throws IOException if the query parameter is invalid, or if anything goes wrong with the
     * request.
     */
    public Response get(String path, String query) throws IOException {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_WAFT).buildUpon();
        uriBuilder.appendPath(path);
        uriBuilder.appendPath(query);
        Uri uri = uriBuilder.build();

        URL url = new URL(uri.toString());

        return ApiConnection.get(url);
    }

    /**
     * The query must be well formed.
     * @param path the path to be appended to the base url, you should not add the '/' sign in front of your path.
     * @param postQuery the query to append, leave out ? in the beginning
     * @return the status code from the response or -1 if unsuccessful
     */
    public int sendPostToWaft(String path,String postQuery) {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_WAFT).buildUpon();
        uriBuilder.appendPath(path);
        Uri uri = uriBuilder.build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Couldn't create url in: sendPostToWaft with the final uri: " + uri.toString());
        }

        if (url != null) {
            return ApiConnection.post(url,postQuery);
        }
        return -1;
    }

    /**
     * This method builds a URL and sends that to the ApiConnection class. This requires a valid flagId
     * @param flagId the flagId for the flag to delete
     * @return the status code or -1 if unsuccessful.
     */
    public int sendDeleteToWaft(String flagId){
        Uri.Builder uriBuilder = Uri.parse(BASE_URL_WAFT).buildUpon();
        uriBuilder.appendPath(FLAGS_PATH);
        uriBuilder.appendPath(DELETE_PATH);
        uriBuilder.appendPath(flagId);
        Uri uri = uriBuilder.build();
        try {
            String test = uri.toString();
            URL url = new URL(test);
            return ApiConnection.delete(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Couldn't create url in: sendDeleteToWaft with the final uri: " + uri.toString());
        }
        return -1;

    }
}
