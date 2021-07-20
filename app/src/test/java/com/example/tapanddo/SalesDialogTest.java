package com.example.tapanddo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SalesDialogTest {

    double val1, val2, val3;
    int expectedSum, actualSum;

    @Before
    public void setUp() throws Exception {
        val1 = 321.3;
        val2 = 234.5;
        val3 = 10;
        expectedSum = (int) (val1 + val2 + val3);
        actualSum = new SalesDialog().calculateTotalAmount(val1, val2, val3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSumOfAllPayAmounts() {
        assertEquals(expectedSum, actualSum);
    }
}