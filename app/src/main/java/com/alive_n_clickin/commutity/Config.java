package com.alive_n_clickin.commutity;

/**
 * A class for storing configuration. This class holds information like URL:s and API keys that
 * may want to be changed depending on who the developer is.
 */
public class Config {
    /**
     * The URL to the Waft API.
     */
    public static final String WAFT_URL = "http://95.85.21.47";

    /**
     * Authorisation for the ElectriCity API. Username and password encoded with Base64.
     */
    public static final String ELECTRICITY_AUTHORIZATION = "Basic Z3JwOToza1B2MmlTU3Nu";

    /**
     * API key for Västtrafik's API.
     */
    public static final String VASTTRAFIK_API_KEY = "69b13ace-0bc1-4203-8a56-cc95648f4dca";
}
