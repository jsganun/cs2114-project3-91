package tests;

import classes.Statistic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestStatistic {
    private Statistic stat1;

    @BeforeEach 
    public void setUp(){
        stat1 = new Statistic("Wait Time", 30.0f);
    }

    @Test
    public void testConstructor(){
        Statistic stat2 = new Statistic("Queue Length", 45.0f);
        assertEquals("Wait Time", stat1.getName());
        assertEquals(30.0f, stat1.getValue());
        assertEquals("Queue Length", stat2.getName());
        assertEquals(45.0f, stat2.getValue());
    }
    @Test
    public void testSetValue(){
        assertEquals(30.0f, stat1.getValue());
        stat1.setValue(45.0f);
        assertEquals(45.0f, stat1.getValue());
        stat1.setValue(-5.0f);
        assertEquals(45.0f, stat1.getValue());
        stat1.setValue(Float.NaN);
        assertEquals(45.0f, stat1.getValue());
        stat1.setValue(0.0f);
        assertEquals(0.0f, stat1.getValue());
        stat1.setValue(Float.POSITIVE_INFINITY);
        assertEquals(0.0f, stat1.getValue());
    }
}