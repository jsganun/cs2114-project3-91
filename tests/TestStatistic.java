package tests;

import student.TestCase;
import classes.Statistic;

public class TestStatistic extends TestCase {
    private Statistic stat1;

    public void setUp(){
        stat1 = new Statistic("Wait Time", 30.0f);
    }
    @Test
    public void testConstructor(){
        Statistic stat2 = new Statistic("Queue Length", 45.0f);
        assertEquals("Wait Time", stat.getName());
        assertEquals(30.0f, stat.getValue());
        assertEquals("Queue Length", stat2.getName());
        assertEquals(45.0f, stat.getValue());
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
        stat.setValue(Float.POSITIVE_INFINITY);
        assertEquals(0.0f, stat1.getValue());
    }
}