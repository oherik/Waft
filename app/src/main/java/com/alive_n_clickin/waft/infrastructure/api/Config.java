package com.alive_n_clickin.waft.infrastructure.api;

/**
 * A class for storing configuration. This class holds information like URL:s and API keys that
 * may want to be changed depending on who the developer is.
 *
 * @since 1.0
 */
class Config {
    /**
     * The URL to the Waft API.
     */
    public static final String WAFT_URL = "http://95.85.21.47";

    /**
     * Authorisation for the ElectriCity API. Username and password encoded with Base64.
     */
    public static final String ELECTRICITY_AUTHORIZATION = "<YOUR API KEY>";

    /**
     * API key for Västtrafik's API.
     */
    public static final String VASTTRAFIK_API_KEY = "<YOUR API KEY>";
}
