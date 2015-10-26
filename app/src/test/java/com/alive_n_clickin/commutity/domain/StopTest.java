package com.alive_n_clickin.commutity.domain;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class StopTest {

    private long id = 10;
    private String name = "MockStop";

    @Test
    public void testConstructorIllegalArguments() throws Exception {

        boolean exception;
        //test that creating a stop with no name returns exception
        exception = false;
        try {
            new Stop(null, id);
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);

        exception = false;
        //test that including stop name will not result in exception
        try {
            new Stop(name, id);
        } catch (NullPointerException e) {
            exception = true;
        }
        assertFalse(exception);


    }

    @Test
    public void testGetName() throws Exception {

        Stop stop = new Stop(name,id);
        assertEquals(name, stop.getName());
    }

    @Test
    public void testGetId() throws Exception {
        Stop stop = new Stop(name, id);
        assertEquals(id, stop.getId());
    }
}
