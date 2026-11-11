package tests;

import classes.Park;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPark {

    private Park park;

    @BeforeEach 
    public void setUp(){
        park = new Park();
    }

    @Test 
    public void testConstructor(){
        assertFalse(park.getActive());
        assertEquals(10, park.getAttractions().length);
        assertEquals(500, park.getVisitors().length);
        assertNull(park.getAttractions()[0]);
        assertNull(park.getVisitors()[0]);
    }

    @Test 
    public void testSetActive(){ 
        park.setActive(true);
        assertTrue(park.getActive());
        park.setActive(false);
        assertFalse(park.getActive());
    }
    
    @Test 
    public void testAddAttraction(){
        assertTrue(park.addAttraction("Farris Wheel", 20, 10.0f));
        assertNotNull(park.getAttractions()[0]);
        assertFalse(park.addAttraction(null, 20, 10.0f));
        assertFalse(park.addAttraction("", 20, 10.0f));
        assertFalse(park.addAttraction("  ", 20, 10.0f));
        assertFalse(park.addAttraction("Farris Wheel", 0, 10.0f));
        assertFalse(park.addAttraction("Farris Wheel", -10, 10.0f));
        assertFalse(park.addAttraction("Farris Wheel", 1000000, 10.0f));
        assertFalse(park.addAttraction("Farris Wheel", 20, 0.0f));
        assertFalse(park.addAttraction("Farris Wheel", 20, -10.0f));
        assertFalse(park.addAttraction("Farris Wheel", 20, Float.NaN));
        assertFalse(park.addAttraction("Farris Wheel", 20, Float.POSITIVE_INFINITY));
    }

    @Test
    public void testAddAttractionMax(){
        for (int i = 0; i < Park.MAX_ATTRACTIONS; i++){
            assertTrue(park.addAttraction("Farris Wheel" + i, 20, 10.0f));
        }
        assertFalse(park.addAttraction("One More Ride", 20, 10.0f));
    }

    @Test
    public void testRemoveAttraction(){
        park.addAttraction("Farris Wheel", 20, 10.0f);
        park.addAttraction("Tea Cups", 10, 5.0f);
        assertEquals("Farris Wheel", park.getAttractions()[0].getName());
        assertTrue(park.removeAttraction(park.getAttractions()[0].getId()));
        assertEquals("Tea Cups", park.getAttractions()[0].getName());
        assertNull(park.getAttractions()[1]);
    }

    @Test
    public void testRemoveAttractionInvalid(){
        assertFalse(park.removeAttraction(-1));
        assertFalse(park.removeAttraction(1000000));
    }

    @Test
    public void testAddVisitor(){
        assertTrue(park.addVisitor("Mike", 30.0f));
        assertNotNull(park.getVisitors()[0]);
        assertEquals("Mike", park.getVisitors()[0].getName());
        assertFalse(park.addVisitor(null, 30.0f));
        assertFalse(park.addVisitor("", 30.0f));
        assertFalse(park.addVisitor(" ", 30.0f));
        assertFalse(park.addVisitor("Mike", 0.0f));
        assertFalse(park.addVisitor("Mike", -10.0f));
        assertFalse(park.addVisitor("Mike", Float.NaN));
        assertFalse(park.addVisitor("Mike", Float.POSITIVE_INFINITY));
    }

    @Test
    public void testAddVisitorMax(){
        for (int i = 0; i < Park.MAX_VISITORS; i++){
            assertTrue(park.addVisitor("Gary "+ i, 10.0f));
        }
        assertFalse(park.addVisitor("Just One More", 10.0f));
    }

    @Test
    public void testRemoveVisitor() {
        park.addVisitor("Gimmly", 10.0f);
        park.addVisitor("Sam", 20.0f);
        assertEquals("Gimmly", park.getVisitors()[0].getName());
        assertTrue(park.removeVisitor(park.getVisitors()[0].getId()));
        assertEquals("Sam", park.getVisitors()[0].getName());
        assertNull(park.getVisitors()[1]);
    }

    @Test
    public void testRemoveVisitorInvalid(){
        assertFalse(park.removeVisitor(-1));
        assertFalse(park.removeVisitor(1000000));
    }
}