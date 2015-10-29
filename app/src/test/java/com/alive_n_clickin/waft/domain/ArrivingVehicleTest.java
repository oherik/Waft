package com.alive_n_clickin.waft.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.mock;


public class ArrivingVehicleTest {
    private List<IFlag> flagList = new ArrayList<>();
    private IFlag flag = mock(IFlag.class);
    private ArrivingVehicle arrVehic1;
    private ArrivingVehicle arrVehic2;
    private Date dateVehic1;
    private Date dateVehic2;

    @Before
    public void initialize(){
        flagList.add(flag);
        String dest = "destination";
        String shortRoute = "shortRoute";
        String journeyID = "journey";
        dateVehic1 = new Date();
        dateVehic2 = new Date(2015, 10,29,7,57);
        int lineColor = 2;
        arrVehic1 = new ArrivingVehicle(dest, shortRoute, journeyID, dateVehic1, flagList, lineColor);
        arrVehic2 = new ArrivingVehicle(dest, shortRoute, journeyID, dateVehic2, flagList, lineColor);
    }

    @Test
    public void testCompareTo() throws Exception {

        //Vehicle one arrives later than vehicle two and should return -1
        assertEquals("Should return -1", -1, arrVehic1.compareTo(arrVehic2));

        //Vehicle two arrives before  vehicle one and should return 1
        assertEquals("Should return 1", 1, arrVehic2.compareTo(arrVehic1));

        //Same arriving time should return 0
        assertEquals("should return 0", 0, arrVehic1.compareTo(arrVehic1));
    }

    @Test
    public void testGetArrival() throws Exception {
        assertEquals("Should return the time of arrival", dateVehic1, arrVehic1.getArrivalTime());

        //Should be false due to dateVehic2 is not the same as the arrival time in arrVehic1
        assertFalse(dateVehic2.equals(arrVehic1.getArrivalTime()));
    }

    @Test
    public void testGetFlags() throws Exception {
        assertEquals(flagList, arrVehic1.getFlags());
    }
}