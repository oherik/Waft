package com.alive_n_clickin.commutity.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.mock;

/**
 * Created by mats on 09/10/15.
 */
public class ArrivingVehicleTest {

    @Test
    public void testCompareTo() throws Exception {
        ArrivingVehicle arrivingVehicle1 = new ArrivingVehicle("destination", "shortRouteName", 123,
                new Date(2000, 1, 1), new ArrayList<IFlag>());
        ArrivingVehicle arrivingVehicle2 = new ArrivingVehicle("destination", "shortRouteName", 123,
                new Date(2000, 1, 2), new ArrayList<IFlag>());

        assertEquals(arrivingVehicle1.compareTo(arrivingVehicle2), -1);
        assertEquals(arrivingVehicle2.compareTo(arrivingVehicle1), 1);
        assertEquals(arrivingVehicle1.compareTo(arrivingVehicle1), 0);
        assertEquals(arrivingVehicle2.compareTo(arrivingVehicle2), 0);
    }

    @Test
    public void testGetArrival() throws Exception {
        // An arriving vehicle should be immutable and shouldn't leak it's implementation
        // of arrivalTime.
        Date date = new Date(2000, 1, 1);
        ArrivingVehicle arrivingVehicle = new ArrivingVehicle("destination", "shortRouteName", 123,
                date, new ArrayList<IFlag>());

        Date returnedDate = arrivingVehicle.getArrivalTime();
        assertEquals(returnedDate, date);
        date.setTime(1);
        assertFalse(returnedDate.equals(date));
        returnedDate.setTime(2);
        assertFalse(arrivingVehicle.getArrivalTime().equals(returnedDate));
    }

    @Test
    public void testGetFlags() throws Exception {
        // An arriving vehicle should be immutable and shouldn't leak it's implementation of flags.
        List<IFlag> flags = new ArrayList<>();
        ArrivingVehicle arrivingVehicle = new ArrivingVehicle("destination", "shortRouteName", 123,
                new Date(), flags);

        List<IFlag> returnedFlags = arrivingVehicle.getFlags();
        assertEquals(returnedFlags, flags);
        flags.add(mock(IFlag.class));
        assertFalse(returnedFlags.equals(flags));
        returnedFlags.add(mock(IFlag.class));
        assertFalse(arrivingVehicle.getFlags().equals(returnedFlags));

    }
}