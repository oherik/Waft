package com.alive_n_clickin.commutity.domain;

import android.graphics.Color;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ElectriCityBusTest {
    private IFlagType mockFlagType = mock(IFlagType.class);


    @Test
    public void testConstructorIllegalArguments() throws Exception {
        boolean exception;


        String destination = "SomeStop";
        String journeyID = "some123";
        String dgw = "abc123";
        List<IFlag> flags = new ArrayList<>();
        flags.add(new Flag(mockFlagType, "this flag", new Date()));

        //test that creating an electricity with destination as null returns exception
        exception = false;
        try {
            new ElectriCityBus(null,journeyID,dgw,flags);
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);


    }

    @Test
    public void testEquals() throws Exception {

    }
}