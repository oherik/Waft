package com.alive_n_clickin.commutity.domain.flag;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class FlagTest {

    private IFlagType mockFlagType = mock(IFlagType.class);
    private IFlagType mockFlagType2 = mock(IFlagType.class);

    @Test
    public void testConstructorIllegalArguments() throws Exception {
        boolean exception;

        exception = false;
        try {
            new Flag(null, "", new Date());
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);

        exception = false;
        try {
            new Flag(null, "");
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);

        exception = false;
        try {
            new Flag(null);
        } catch (IllegalArgumentException e) {
            exception = true;
        }
        assertTrue(exception);
    }

    @Test
    public void testImmutability() throws Exception {
        Date initialDate = new Date(2015, 9, 22);
        Flag flag = new Flag(mockFlagType, "", initialDate);

        initialDate.setTime(1);
        assertFalse(flag.getCreatedTime().equals(initialDate));

        Date returnedDate = flag.getCreatedTime();
        returnedDate.setTime(2);
        assertFalse(flag.getCreatedTime().equals(returnedDate));
    }

    @Test
    public void testGetComment() throws Exception {
        Flag flag;

        flag = new Flag(mockFlagType, "foo", new Date());
        assertEquals(flag.getComment(), "foo");

        flag = new Flag(mockFlagType, null, new Date());
        assertEquals(flag.getComment(), "");

        flag = new Flag(mockFlagType, "bar");
        assertEquals(flag.getComment(), "bar");

        flag = new Flag(mockFlagType, null);
        assertEquals(flag.getComment(), "");

        flag = new Flag(mockFlagType);
        assertEquals(flag.getComment(), "");
    }

    @Test
    public void testGetCreatedTime() throws Exception {
        Flag flag;
        Date date = new Date();

        flag = new Flag(mockFlagType, "", date);
        assertEquals(flag.getCreatedTime(), date);

        flag = new Flag(mockFlagType, "", null);
        assertTrue(flag.getCreatedTime().getTime() >= date.getTime());

        flag = new Flag(mockFlagType, "");
        assertTrue(flag.getCreatedTime().getTime() >= date.getTime());

        flag = new Flag(mockFlagType);
        assertTrue(flag.getCreatedTime().getTime() >= date.getTime());
    }

    @Test
    public void testEquals() throws Exception {
        Flag flag1;
        Flag flag2;
        Date date1 = new Date(1934, 8, 21);
        Date date2 = new Date(1966, 9, 12);

        flag1 = new Flag(mockFlagType, "foo", date1);
        flag2 = new Flag(mockFlagType, "foo", date1);
        assertTrue(flag1.equals(flag2));
        assertTrue(flag2.equals(flag1));

        flag1 = new Flag(mockFlagType, "foo", date1);
        flag2 = new Flag(mockFlagType2, "foo", date1);
        assertFalse(flag1.equals(flag2));
        assertFalse(flag2.equals(flag1));

        flag1 = new Flag(mockFlagType, "foo", date1);
        flag2 = new Flag(mockFlagType, "bar", date1);
        assertFalse(flag1.equals(flag2));
        assertFalse(flag2.equals(flag1));

        flag1 = new Flag(mockFlagType, "foo", date1);
        flag2 = new Flag(mockFlagType, "foo", date2);
        assertFalse(flag1.equals(flag2));
        assertFalse(flag2.equals(flag1));

        flag1 = new Flag(mockFlagType, "foo", new Date());
        assertTrue(flag1.equals(flag1));
        assertFalse(flag1.equals(null));
        assertFalse(flag1.equals("abc"));
        assertFalse(flag1.equals(123));
    }

    @Test
    public void testGetType() throws Exception {
        Flag flag;

        flag = new Flag(mockFlagType, "", new Date());
        assertEquals(flag.getType(), mockFlagType);

        flag = new Flag(mockFlagType, "");
        assertEquals(flag.getType(), mockFlagType);

        flag = new Flag(mockFlagType);
        assertEquals(flag.getType(), mockFlagType);
    }
}