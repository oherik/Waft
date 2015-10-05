package com.alive_n_clickin.commutity.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class BusTest {

    @Test
    public void testConstructorIllegalArguments() throws Exception {
        boolean exception;

        // assert that a bus with null DGW can't be created
        exception = false;
        try {
            new Bus(null, "", "", "", new ArrayList<IFlag>());
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);

        // assert that a bus with null VIN can't be created
        exception = false;
        try {
            new Bus("", null, "", "", new ArrayList<IFlag>());
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);

        // assert that a bus with null licenseNumber can't be created
        exception = false;
        try {
            new Bus("", "", null, "", new ArrayList<IFlag>());
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);

        // assert that a bus with null wifiBSSID can't be created
        exception = false;
        try {
            new Bus("", "", "", null, new ArrayList<IFlag>());
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);

        // assert that a bus with null flags can't be created
        exception = false;
        try {
            new Bus("", "", "", "", null);
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
    }

//    @Test
//    public void testGetDGW() throws Exception {
//        Bus bus;
//
//        // assert that DGW gets initated to the same value that is returned by getDGW()
//        bus = new Bus("dgw", "vin", "abc123", "bssid");
//        assertEquals(bus.getDGW(), "dgw");
//        bus = new Bus("dgw", "vin", "abc123", "bssid", new ArrayList<IFlag>());
//        assertEquals(bus.getDGW(), "dgw");
//    }
//
//    @Test
//    public void testGetVIN() throws Exception {
//        Bus bus;
//
//        // assert that VIN gets initated to the same value that is returned by getVIN()
//        bus = new Bus("dgw", "vin", "abc123", "bssid");
//        assertEquals(bus.getVIN(), "vin");
//        bus = new Bus("dgw", "vin", "abc123", "bssid", new ArrayList<IFlag>());
//        assertEquals(bus.getVIN(), "vin");
//    }
//
//    @Test
//    public void testGetLicenseNumber() throws Exception {
//        Bus bus;
//
//        // assert that licenseNumber gets initated to the same value that is returned by getLicenseNumber()
//        bus = new Bus("dgw", "vin", "abc123", "bssid");
//        assertEquals(bus.getLicenseNumber(), "abc123");
//        bus = new Bus("dgw", "vin", "abc123", "bssid", new ArrayList<IFlag>());
//        assertEquals(bus.getLicenseNumber(), "abc123");
//    }
//
//    @Test
//    public void testGetWifiBSSID() throws Exception {
//        Bus bus;
//
//        // assert that wifiBSSID gets initated to the same value that is returned by getBSSID()
//        bus = new Bus("dgw", "vin", "abc123", "bssid");
//        assertEquals(bus.getWifiBSSID(), "bssid");
//        bus = new Bus("dgw", "vin", "abc123", "bssid", new ArrayList<IFlag>());
//        assertEquals(bus.getWifiBSSID(), "bssid");
//    }

    @Test
    public void testGetFlags() throws Exception {
        Bus bus;

        IFlag mockFlag = mock(IFlag.class);
        ArrayList<IFlag> initialFlags = new ArrayList<>();
        initialFlags.add(mockFlag);

        // assert that the result of getFlags() is an empty list when a bus is created without any flags
        bus = new Bus("dgw", "vin", "abc123", "bssid", new ArrayList<IFlag>());
        assertEquals(bus.getFlags(), new ArrayList<IFlag>());

        // assert that the list returned by getFlags() is equal to the one sent to the constructor
        initialFlags = new ArrayList<>();
        initialFlags.add(mockFlag);
        bus = new Bus("dgw", "vin", "abc123", "bssid", initialFlags);
        assertEquals(bus.getFlags(), initialFlags);

        // assert that adding flags to the list sent to the constructor after instantiation doesn't
        // affect the result of getFlags()
        initialFlags.add(mockFlag);
        assertEquals(bus.getFlags().size(), 1);

        // assert that adding flags to the list returned by getFlags() doesn't affect the result
        // returned by subsequent calls to getFlags()
        List<IFlag> returnedFlags = bus.getFlags();
        returnedFlags.add(mockFlag);
        assertEquals(bus.getFlags().size(), 1);
    }

    @Test
    public void testEquals() throws Exception {
        Bus bus1;
        Bus bus2;
        List<IFlag> flags1 = new ArrayList<>();
        List<IFlag> flags2 = new ArrayList<>();
        flags2.add(mock(IFlag.class));

        // assert that two buses with the same attributes are equal
        bus1 = new Bus("dgw", "vin", "abc123", "bssid", flags1);
        bus2 = new Bus("dgw", "vin", "abc123", "bssid", flags1);
        assertTrue(bus1.equals(bus2));
        assertTrue(bus2.equals(bus1));

        // assert that two buses with different DGW values aren't equal
        bus1 = new Bus("a", "", "", "", flags1);
        bus2 = new Bus("", "", "", "", flags1);
        assertFalse(bus1.equals(bus2));
        assertFalse(bus2.equals(bus1));

        // assert that two buses with different VIN values aren't equal
        bus1 = new Bus("", "a", "", "", flags1);
        bus2 = new Bus("", "", "", "", flags1);
        assertFalse(bus1.equals(bus2));
        assertFalse(bus2.equals(bus1));

        // assert that two buses with different license numbers aren't equal
        bus1 = new Bus("", "", "a", "", flags1);
        bus2 = new Bus("", "", "", "", flags1);
        assertFalse(bus1.equals(bus2));
        assertFalse(bus2.equals(bus1));

        // assert that two buses with different BSSID values aren't equal
        bus1 = new Bus("", "", "", "a", flags1);
        bus2 = new Bus("", "", "", "", flags1);
        assertFalse(bus1.equals(bus2));
        assertFalse(bus2.equals(bus1));

        bus1 = new Bus("", "", "", "", flags1);
        // assert that a bus equals itself
        assertTrue(bus1.equals(bus1));
        // assert that a bus is not equal to null
        assertFalse(bus1.equals(null));
        // assert that a bus is not equal to an object of another type
        assertFalse(bus1.equals("abc"));
        // assert that a bus is not equal to a primitive value
        assertFalse(bus1.equals(123));
    }
}