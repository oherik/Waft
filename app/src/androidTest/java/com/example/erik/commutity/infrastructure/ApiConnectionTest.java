package com.example.erik.commutity.infrastructure;

import android.test.AndroidTestCase;

/**
 * @author hjorthjort
 *         Created 22/09/15
 */
public class ApiConnectionTest extends AndroidTestCase {

    private com.example.erik.commutity.infrastructure.ApiConnection connection;

    @Override
    protected void setUp() {
        connection = new com.example.erik.commutity.infrastructure.ApiConnection();
    }

    public void testGetRequest() {
        String response = connection.sendGetToElectricity("kjjsadf");
    }
}
