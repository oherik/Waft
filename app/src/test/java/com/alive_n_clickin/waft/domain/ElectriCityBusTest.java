package com.alive_n_clickin.waft.domain;

import android.graphics.Color;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ElectriCityBusTest {
    private IFlagType mockFlagType = mock(IFlagType.class);
    private String destination = "SomeStop";
    private String journeyID = "some123";
    private String dgw = "abc123";
    private List<IFlag> flags = new ArrayList<>();



    @Test
    public void testConstructorIllegalArguments() throws Exception {
        boolean exception;
        flags.add(new Flag(mockFlagType, "this flag", new Date()));



        //test that creating an electricity with destination as null returns exception
        exception = false;
        try {
            new ElectriCityBus(null,journeyID,dgw,flags);
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);

        //test that creating an electricity with journeyID as null returns exception
        exception = false;
        try {
            new ElectriCityBus(destination,null,dgw,flags);
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);

        //test that creating an electricity with dgw as null returns exception
        exception = false;
        try {
            new ElectriCityBus(destination,journeyID,null,flags);
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);

        //test that creating an electricity with list as null returns exception
        exception = false;
        try {
            new ElectriCityBus(destination,journeyID,dgw,null);
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);

        //test that creating an electricity with no null arguments does not return exception
        exception = false;
        try {
            new ElectriCityBus(destination,journeyID,dgw,flags);
        } catch (NullPointerException e) {
            exception = true;
        }
        assertFalse(exception);

    }

    @Test
    public void testGetFlags(){
        boolean exception;
        flags.add(new Flag(mockFlagType, "this flag", new Date()));
        ElectriCityBus bus = new ElectriCityBus(destination,journeyID,dgw,flags);
        assertEquals(flags, bus.getFlags());
    }

}