package com.alive_n_clickin.commutity.domain.flaggable;

import com.alive_n_clickin.commutity.domain.flag.IFlag;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class BusTest {

    @Test
    public void testConstructorIllegalArguments() throws Exception {
        boolean exception;

        exception = false;
        try {
            new Bus(null, "", "", "");
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);

        exception = false;
        try {
            new Bus("", null, "", "");
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);

        exception = false;
        try {
            new Bus("", "", null, "");
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);

        exception = false;
        try {
            new Bus("", "", "", null);
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
    }

    @Test
    public void testGetDGW() throws Exception {
        Bus bus = new Bus("dgw", "vin", "abc123", "bssid");
        assertEquals(bus.getDGW(), "dgw");
    }

    @Test
    public void testGetVIN() throws Exception {
        Bus bus = new Bus("dgw", "vin", "abc123", "bssid");
        assertEquals(bus.getVIN(), "vin");
    }

    @Test
    public void testGetLicenseNumber() throws Exception {
        Bus bus = new Bus("dgw", "vin", "abc123", "bssid");
        assertEquals(bus.getLicenseNumber(), "abc123");
    }

    @Test
    public void testGetWifiBSSID() throws Exception {
        Bus bus = new Bus("dgw", "vin", "abc123", "bssid");
        assertEquals(bus.getWifiBSSID(), "bssid");
    }

    @Test
    public void testEquals() throws Exception {
        Bus bus1;
        Bus bus2;

        bus1 = new Bus("dgw", "vin", "abc123", "bssid");
        bus2 = new Bus("dgw", "vin", "abc123", "bssid");
        assertTrue(bus1.equals(bus2));
        assertTrue(bus2.equals(bus1));

        bus1 = new Bus("a", "", "", "");
        bus2 = new Bus("", "", "", "");
        assertFalse(bus1.equals(bus2));
        assertFalse(bus2.equals(bus1));

        bus1 = new Bus("", "a", "", "");
        bus2 = new Bus("", "", "", "");
        assertFalse(bus1.equals(bus2));
        assertFalse(bus2.equals(bus1));

        bus1 = new Bus("", "", "a", "");
        bus2 = new Bus("", "", "", "");
        assertFalse(bus1.equals(bus2));
        assertFalse(bus2.equals(bus1));

        bus1 = new Bus("", "", "", "a");
        bus2 = new Bus("", "", "", "");
        assertFalse(bus1.equals(bus2));
        assertFalse(bus2.equals(bus1));

        bus1 = new Bus("", "", "", "");
        assertTrue(bus1.equals(bus1));
        assertFalse(bus1.equals(null));
        assertFalse(bus1.equals("abc"));
        assertFalse(bus1.equals(123));
    }

    @Test
    public void testAddFlagAndRemoveFlag() throws Exception {
        Bus bus;
        IFlag flag = mock(IFlag.class);
        boolean exception;

        bus = new Bus("", "", "", "");
        assertTrue(bus.getFlags().size() == 0);
        bus.removeFlag(flag);
        assertTrue(bus.getFlags().size() == 0);
        bus.addFlag(flag);
        assertTrue(bus.getFlags().size() == 1);
        bus.addFlag(flag);
        assertTrue(bus.getFlags().size() == 2);
        bus.removeFlag(flag);
        assertTrue(bus.getFlags().size() == 1);
        bus.removeFlag(flag);
        assertTrue(bus.getFlags().size() == 0);
        bus.removeFlag(flag);
        assertTrue(bus.getFlags().size() == 0);

        bus = new Bus("", "", "", "");
        exception = false;
        try {
            bus.addFlag(null);
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
        assertTrue(bus.getFlags().size() == 0);

        bus = new Bus("", "", "", "");
        exception = false;
        try {
            bus.removeFlag(null);
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
        assertTrue(bus.getFlags().size() == 0);
    }
}