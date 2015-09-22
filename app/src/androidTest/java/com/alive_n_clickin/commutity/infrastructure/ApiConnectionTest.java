package com.alive_n_clickin.commutity.infrastructure;

import android.test.AndroidTestCase;

/**
 * @author hjorthjort
 *         Created 22/09/15
 */
public class ApiConnectionTest extends AndroidTestCase {

    private com.alive_n_clickin.commutity.infrastructure.ApiConnection connection;

    @Override
    protected void setUp() {
        connection = new com.alive_n_clickin.commutity.infrastructure.ApiConnection();
    }

    public void testGetRequest() {
        String response = connection.sendGetToElectricity("kjjsadf");
    }
}
