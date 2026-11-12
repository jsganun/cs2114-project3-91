package tests;

import classes.Attraction;
import classes.Visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class TestAttraction {

    private Attraction attraction;
    private Visitor visitor;
    private Visitor firstVisitor;

    @BeforeEach
    public void setUp() {
        attraction = new Attraction("Test Attraction", 10, 5.0f);
        attraction.setActive(true);

        for (int i = 1; i <= 5; i++) {
            Visitor temp = new Visitor(String.format("%d", i), 3.25f);
            if (i == 1) {
                firstVisitor = temp;
            }
            assertTrue(attraction.addVisitor(temp));
        }

        visitor = new Visitor("Test Visitor", 10.0f);
    }

    @Test
    public void testGetId() {
        Attraction attraction2 = new Attraction("Another", 10, 5.0f);
        assertEquals(1, attraction2.getId() - attraction.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Test Attraction", attraction.getName());
    }

    @Test
    public void testGetActive() {
        assertTrue(attraction.getActive());
        attraction.setActive(false);
        assertFalse(attraction.getActive());
    }

    @Test
    public void testGetCapacity() {
        assertEquals(10, attraction.getCapacity());
    }

    @Test
    public void testGetDuration() {
        assertEquals(5.0f, attraction.getDuration());
    }

    @Test
    public void testGetCurrDuration() {
        assertEquals(0.0f, attraction.getCurrDuration());
    }

    @Test
    public void testGetVisitors() {
        assertNotNull(attraction.getVisitors());
        assertEquals(5, attraction.getVisitors().size());
        assertSame(firstVisitor, attraction.getVisitors().peek());
    }

    @Test
    public void testSetActive() {
        assertTrue(attraction.getActive());
        attraction.setActive(false);
        assertFalse(attraction.getActive());
        assertTrue(attraction.getVisitors().isEmpty());
        assertEquals(0.0f, attraction.getCurrDuration());
    }

    @Test
    public void testAddVisitor() {
        assertTrue(attraction.addVisitor(visitor));

        attraction.setActive(false);
        assertFalse(attraction.addVisitor(new Visitor("Inactive visitor", 20.0f)));
        assertThrows(IllegalArgumentException.class, () -> attraction.addVisitor(null));
    }

    @Test
    public void testRemoveVisitor() {
        assertSame(firstVisitor, attraction.removeVisitor());
        assertEquals(4, attraction.getVisitors().size());

        attraction.getVisitors().clear();
        assertThrows(NoSuchElementException.class, attraction::removeVisitor);
    }

    @Test
    public void testRemainingRounds() {
        assertEquals(0, attraction.remainingRounds());
        assertEquals(0, attraction.remainingRounds(firstVisitor));
        assertThrows(NoSuchElementException.class,
            () -> attraction.remainingRounds(visitor));
    }

    @Test
    public void testRemainingTime() {
        attraction.tick(0.0f);
        assertEquals(5.0f, attraction.getCurrDuration());
        assertEquals(5.0f, attraction.remainingTime());

        attraction.tick(5.0f);
        assertEquals(0.0f, attraction.remainingTime());
        assertThrows(NoSuchElementException.class,
            () -> attraction.remainingTime(visitor));
    }

    @Test
    public void testRemainingTimeForVisitor() {
        Attraction smallAttraction = new Attraction("Small", 2, 5.0f);
        smallAttraction.setActive(true);
        assertTrue(smallAttraction.addVisitor(new Visitor("Rider", 20.0f)));
        smallAttraction.tick(0.0f);

        Visitor firstInLine = new Visitor("First", 20.0f);
        Visitor secondInLine = new Visitor("Second", 20.0f);
        Visitor thirdInLine = new Visitor("Third", 20.0f);
        assertTrue(smallAttraction.addVisitor(firstInLine));
        assertTrue(smallAttraction.addVisitor(secondInLine));
        assertTrue(smallAttraction.addVisitor(thirdInLine));

        assertEquals(5.0f, smallAttraction.remainingTime(firstInLine));
        assertEquals(5.0f, smallAttraction.remainingTime(secondInLine));
        assertEquals(10.0f, smallAttraction.remainingTime(thirdInLine));
        assertEquals(10.0f, smallAttraction.remainingTime());
    }

    @Test
    public void testTick() {
        
        Exception exception = null;
        try {
            attraction.tick(-1f);
        } 
        catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
        assertTrue(exception instanceof IllegalArgumentException);

        attraction.tick(1.0f);
        assertEquals(5.0f, attraction.getCurrDuration());
        assertTrue(attraction.getVisitors().isEmpty());

        attraction.tick(2.0f);
        assertEquals(3.0f, attraction.getCurrDuration());

        attraction.tick(10.0f);
        assertEquals(0.0f, attraction.getCurrDuration());
    }

    @Test
    public void testTickWaitsForMinimumVisitors() {
        attraction.removeVisitor();
        attraction.tick(1.0f);

        assertEquals(0.0f, attraction.getCurrDuration());
        assertEquals(4, attraction.getVisitors().size());
    }

    @Test
    public void testTickWhenInactive() {
        Attraction inactiveAttraction = new Attraction("Inactive", 10, 5.0f);

        inactiveAttraction.tick(2.0f);

        assertFalse(inactiveAttraction.getActive());
        assertEquals(0.0f, inactiveAttraction.getCurrDuration());
        assertTrue(inactiveAttraction.getVisitors().isEmpty());
    }
}
