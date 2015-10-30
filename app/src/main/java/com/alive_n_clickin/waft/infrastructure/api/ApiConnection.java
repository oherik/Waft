package com.alive_n_clickin.waft.infrastructure.api;

import android.util.Log;

import com.alive_n_clickin.waft.infrastructure.api.response.ConnectionException;
import com.alive_n_clickin.waft.infrastructure.api.response.Response;
import com.alive_n_clickin.waft.util.LogUtils;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * For connecting to different API:s. Allows abstraction by letting us send only the query parameters
 * to the API we want, not bothering about authentication and the likes. ApiConnection is package
 * private: no need for layers higher up to use it directly!
 *
 * @since 0.1
 */
class ApiConnection {
    private final static String LOG_TAG = LogUtils.getLogTag(ApiConnection.class);

    private final static String CHARSET = "UTF-8";
    private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final int CONNECTION_TIMEOUT_LIMIT = 5000;

    /**
     * Returns the response of a get request to an url.
     *
     * @param url the url to send a get request to.
     * @return the response of the query.
     * @throws ConnectionException if anything goes wrong when fetching the response, or if the
     * server takes more than 5 seconds to respond.
     */
    static Response get(String url) throws ConnectionException {
        return get(url, new ArrayList<Parameter>());
    }

    /**
     * Returns the response of a get request to an url with the specified parameters.
     *
     * @param url the url to send a get request to.
     * @param headers a list of header parameters to append to the query.
     * @return the response of the query.
     * @throws ConnectionException if anything goes wrong when fetching the response, or if the
     * server takes more than 5 seconds to respond.
     */
    static Response get(String url, List<Parameter> headers) throws ConnectionException {
        URL httpUrl = buildUrlFromString(url);

        int status;
        String body;

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(CONNECTION_TIMEOUT_LIMIT);

            if (headers != null) {
                for (Parameter parameter : headers) {
                    connection.setRequestProperty(parameter.getKey(), parameter.getValue());
                }
            }

            connection.connect();


            inputStream = connection.getInputStream();

            status = connection.getResponseCode();
            body = readStream(inputStream);
        } catch (IOException e) {
            throw new ConnectionException("Error connecting to API", e);
        } finally {
            closeStream(inputStream);

            if (connection != null) {
                connection.disconnect();
            }
        }

        return new Response(status, body);
    }

    /**
     * Returns the response of a post request to an url with the specified parameters.
     *
     * @param url the full address for the location where to send the request.
     * @param parameters the post parameters to send along with the request.
     * @return the response of the query. Null if anything goes wrong when fetching the response.
     * @throws ConnectionException if anything goes wrong when fetching the response, or if the
     * server takes more than 5 seconds to respond.
     */
    static Response post(String url, List<Parameter> parameters) throws ConnectionException {
        URL httpUrl = buildUrlFromString(url);
        String query = buildQueryString(parameters);

        // Convert the parameters to a UTF-8 byte array. A byte array is required to be able to
        // write to serverOutputStream
        byte[] bodyPostData = query.getBytes(StandardCharsets.UTF_8);

        int status;
        String body;

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            connection = (HttpURLConnection) httpUrl.openConnection();

            // Tells the connection that we want to send post parameters to the server
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");
            connection.setConnectTimeout(CONNECTION_TIMEOUT_LIMIT);

            // The setRequest methods specify which kind of message that's being sent to the server. In
            // this case it's a POST request, using the default internet media type
            // x-www-form-urlencoded (which uses key-value pairs, with ampersands to separate the pairs).
            // The request should be encoded using UTF-8, although both this and the media type can be
            // changed if needed (i.e. the server changes how it handles the requests). The length of the
            // request is also set here.
            connection.setRequestProperty("Content-Type", CONTENT_TYPE);
            connection.setRequestProperty("charset", CHARSET);
            connection.setRequestProperty("Content-Length", bodyPostData.length + "");

            // Forces Java to reload the file
            connection.setUseCaches(false);

            connection.connect();

            // Send data
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.write(bodyPostData);

            inputStream = connection.getInputStream();

            status = connection.getResponseCode();
            body = readStream(inputStream);
        } catch (IOException e) {
            throw new ConnectionException("Error connecting to API", e);
        } finally {
            closeStream(inputStream);
            closeStream(outputStream);

            if (connection != null) {
                connection.disconnect();
            }
        }

        return new Response(status, body);
    }

    /**
     * Returns the response of a delete request to an url with the specified parameters.
     *
     * @param url The full address for the location where to send the request
     * @return The response code from the server, or -1 if the request couldn't be sent
     * @throws ConnectionException if anything goes wrong when fetching the response, or if the
     * server takes more than 5 seconds to respond.
     */
    static Response delete(String url) throws ConnectionException {
        URL httpUrl = buildUrlFromString(url);

        int status;
        String body;

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) httpUrl.openConnection();

            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", CONTENT_TYPE);
            connection.setRequestMethod("DELETE");
            connection.setConnectTimeout(CONNECTION_TIMEOUT_LIMIT);

            connection.connect();

            inputStream = connection.getInputStream();

            status = connection.getResponseCode();
            body = readStream(inputStream);
        } catch (IOException e) {
            throw new ConnectionException("Error connecting to API", e);
        } finally {
            closeStream(inputStream);

            if (connection != null) {
                connection.disconnect();
            }
        }

        return new Response(status, body);
    }

    /**
     * Reads the content of any InputStream and returns it as a String.
     *
     * @param inputStream the InputStream to read from.
     * @return the content of an InputStream. Returns an empty string if the stream doesn't have
     * a next value.
     */
    static String readStream(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream, "UTF-8");
        // By setting delimiter \A (which marks the beginning of the file) the Scanner read the whole file.
        sc.useDelimiter("\\A");
        return sc.hasNext() ? sc.next() : "";
    }

    /**
     * Attempts to close a closable stream.
     *
     * @param stream the stream to close.
     */
    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error closing stream", e);
            }
        }
    }


    /**
     * Builds a URL object from a string.
     *
     * @param url the string to build and URL from.
     * @return the created URL
     * @throws IllegalArgumentException if the url is malformed.
     */
    private static URL buildUrlFromString(String url) {
        URL httpUrl;
        try {
            httpUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Malformed URL", e);
        }

        return httpUrl;
    }

    /**
     * Builds a query string from a list of parameters. The string will look something like this:
     * foo=bar&lorem=ipsum.
     *
     * @param parameters the parameters to build a query string from.
     * @return a query string.
     */
    private static String buildQueryString(List<Parameter> parameters) {
        StringBuilder queryString = new StringBuilder("");

        boolean first = true;
        for (Parameter parameter : parameters) {
            String parameterString = "";

            // If the parameter is the first one in the list, don't prepend it with a & sign
            if (first) {
                first = false;
            } else {
                parameterString = "&";
            }

            parameterString+=parameter.getKey() + "=" + parameter.getValue();

            queryString.append(parameterString.toString());
        }

        return queryString.toString();
    }
}
