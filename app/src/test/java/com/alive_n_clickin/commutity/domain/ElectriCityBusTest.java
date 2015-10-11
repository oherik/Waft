package com.alive_n_clickin.commutity.domain;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ElectriCityBusTest {

    @Test
    public void testConstructorIllegalArguments() throws Exception {
        boolean exception;

        // assert that a bus with null DGW can't be created
        exception = false;
        try {
            new ElectriCityBus("", "", "0", null);
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);

        // assert that a bus with null VIN can't be created
        exception = false;
        try {
            new ElectriCityBus(null, "", "0","");
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);

        // assert that a bus with null licenseNumber can't be created
        exception = false;
        try {
            new ElectriCityBus("", null, "0", "");
        } catch (NullPointerException e) {
            exception = true;
        }
        assertTrue(exception);
    }

    @Test
    public void testEquals() throws Exception {
        IElectriCityBus bus1;
        IElectriCityBus bus2;

        // assert that two buses with the same attributes are equal
        bus1 = new ElectriCityBus("vin", "abc123", "123", "dgw");
        bus2 = new ElectriCityBus ("vin", "abc123", "123", "dgw");
        assertTrue(bus1.equals(bus2));
        assertTrue(bus2.equals(bus1));

        // assert that two buses with different DGW values aren't equal
        bus1 = new ElectriCityBus("vin", "abc123", "123", "a");
        bus2 = new ElectriCityBus ("vin", "abc123", "123", "b");
        assertFalse(bus1.equals(bus2));
        assertFalse(bus2.equals(bus1));

        // assert that two buses with different VIN values aren't equal
        bus1 = new ElectriCityBus("a", "abc123", "123", "dgw");
        bus2 = new ElectriCityBus ("b", "abc123", "123", "dgw");
        assertFalse(bus1.equals(bus2));
        assertFalse(bus2.equals(bus1));

        // assert that two buses with different license numbers aren't equal
        bus1 = new ElectriCityBus("vin", "a", "123", "dgw");
        bus2 = new ElectriCityBus ("vin", "b", "123", "dgw");
        assertFalse(bus1.equals(bus2));
        assertFalse(bus2.equals(bus1));

        // assert that two buses with different BSSID values aren't equal
        bus1 = new ElectriCityBus("vin", "abc123", "123", "dgw");
        bus2 = new ElectriCityBus ("vin", "abc123", "456", "dgw");
        assertFalse(bus1.equals(bus2));
        assertFalse(bus2.equals(bus1));

        bus1 = new ElectriCityBus("", "", "0", "");
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