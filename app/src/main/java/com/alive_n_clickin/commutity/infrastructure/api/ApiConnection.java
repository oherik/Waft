package com.alive_n_clickin.commutity.infrastructure.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Scanner;

/**
 * @author hjorthjort
 *         Created 22/09/15
 *
 * For connecting to different API:s. Allows abstraction by letting us send only the query parameters to the api we want, not bothering about authentication and the likes.
 * ApiConnection is package private: no need for layers higher up to use it directly!
 * @since 0.1
 */
class ApiConnection {


    /**
     * Returns the response of a connection. Handles parsing and exceptions
     * @param connection the HttpURLConnection that the response should be read from
     * @return the response if there is one, null otherwise. Check log messages if null is returned
     */
    static String getResponseFromHttpConnection(HttpURLConnection connection) {
        try {
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            return readStream(inputStream);

        } catch (IOException e) {
            String errors = readStream(connection.getErrorStream());
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
    static String readStream(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        //By setting delimiter \A (which marks the beginning of the file) the Scanner read the whole file.
        return sc.useDelimiter("\\A").next();
    }
}
