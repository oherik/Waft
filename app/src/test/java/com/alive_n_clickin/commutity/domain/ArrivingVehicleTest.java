package com.alive_n_clickin.commutity.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by mats on 09/10/15.
 *
 */

public class ArrivingVehicleTest {
    private List<IFlag> flags = new ArrayList<>();
    private IFlag flag = mock(IFlag.class);
    private ArrivingVehicle arrVehic1;
    private ArrivingVehicle arrVehic2;
    private Date dateVehic1;
    private Date dateVehic2;

    @Before
    public void initialize(){
        flags.add(flag);
        String dest = "destination";
        String shortRoute = "shortRoute";
        String journeyID = "journey";
        dateVehic1 = new Date();
        dateVehic2 = new Date(2015, 10,29,7,57);
        int lineColor = 2;
        arrVehic1 = new ArrivingVehicle(dest,shortRoute,journeyID,dateVehic1, flags, lineColor);
        arrVehic2 = new ArrivingVehicle(dest, shortRoute,journeyID,dateVehic2, flags, lineColor);
    }

    @Test
    public void testCompareTo() throws Exception {

        //Vehicle one arrives later than vehicle 2 and should return -1
        assertEquals("Should return -1", -1, arrVehic1.compareTo(arrVehic2));

        //Vehicle one arrives later than vehicle 2 and should return 1
        assertEquals("Should return 1", 1, arrVehic2.compareTo(arrVehic1));

        //Same vehicle should return 0
        assertEquals("should return 0", 0, arrVehic1.compareTo(arrVehic1));
    }

    @Test
    public void testGetArrival() throws Exception {
        assertEquals("Should return the time of arrival", dateVehic1, arrVehic1.getArrivalTime());

        assertFalse(dateVehic2.equals(arrVehic1.getArrivalTime()));
    }

    @Test
    public void testGetFlags() throws Exception {

    }
}