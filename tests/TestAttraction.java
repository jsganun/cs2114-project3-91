package tests;

import classes.Attraction;
import classes.Dequeue;
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
    private Attraction emptyAttraction;
    private Visitor normalVisitor;
    private Visitor fastVisitor;
    private Visitor firstNormalVisitor;
    private Visitor firstFastVisitor;

    @BeforeEach
    public void setUp() {
        attraction = new Attraction("Test Attraction", 10, 5.0f);
        attraction.setActive(true);
        emptyAttraction = new Attraction("Test Empty Attraction", 10, 5.0f);
        emptyAttraction.setActive(true);

        for (int i = 1; i <= 5; i++) {
            Visitor normTemp = new Visitor(String.format("%d", i), 5.0f, false);
            Visitor fastTemp = new Visitor(String.format("%d", i), 5.0f, true);

            if (i == 1) {
                firstNormalVisitor = normTemp;
                firstFastVisitor = fastTemp;
            }
            
            assertTrue(attraction.addVisitor(normTemp));
            assertTrue(attraction.addVisitor(fastTemp));
        }

        normalVisitor = new Visitor("Test Visitor", 10.0f, false);
        fastVisitor = new Visitor("Test Fast Visitor", 10.00f, true);
    }

    @Test
    public void testGetId() {
        Attraction attraction1 = new Attraction("Another", 10, 5.0f);
        Attraction attraction2 = new Attraction("Another", 10, 5.0f);
        assertEquals(1, attraction2.getId() - attraction1.getId());
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

        // Test Regular Visitors
        Dequeue<Visitor> normal = attraction.getVisitors(false);
        assertNotNull(normal);
        assertEquals(5, normal.size());
        assertSame(firstNormalVisitor, normal.peek());

        // Test Fast Visitors
        Dequeue<Visitor> fast = attraction.getVisitors(true);
        assertNotNull(fast);
        assertEquals(5, fast.size());
        assertSame(firstFastVisitor, fast.peek());
    }

    @Test
    public void testGetLineSize() {

        // Test Regular Visitors
        assertEquals(5, attraction.getLineSize(false));
        attraction.removeVisitor(false);
        assertEquals(4, attraction.getLineSize(false));

        // Test Fast Visitors
        assertEquals(5, attraction.getLineSize(true));
        attraction.removeVisitor(true);
        assertEquals(4, attraction.getLineSize(true));
        
        // Test Empty Lines
        assertEquals(0, emptyAttraction.getLineSize(false));
        assertEquals(0, emptyAttraction.getLineSize(true));
    }

    @Test
    public void testSetActive() {
        assertTrue(attraction.getActive());
        attraction.setActive(false);
        assertFalse(attraction.getActive());
        assertTrue(attraction.getVisitors(false).isEmpty());
        assertTrue(attraction.getVisitors(true).isEmpty());
        assertEquals(0.0f, attraction.getCurrDuration());
    }

    @Test
    public void testAddVisitor() {

        // Test Regular Visitor
        assertTrue(attraction.addVisitor(normalVisitor));

        // Test Fast Visitor
        assertTrue(attraction.addVisitor(fastVisitor));

        // Test Failures (Inactive & Null)
        attraction.setActive(false);
        assertFalse(attraction.addVisitor(new Visitor("Inactive visitor", 20.0f)));
        
        attraction.setActive(true);
        assertThrows(IllegalArgumentException.class, () -> attraction.addVisitor(null));
    }

    @Test
    public void testRemoveVisitor() {

        // Test Regular Visitor
        assertSame(firstNormalVisitor, attraction.removeVisitor(false));
        assertEquals(4, attraction.getVisitors(false).size());

        // Test Fast Visitor
        assertSame(firstFastVisitor, attraction.removeVisitor(true));
        assertEquals(4, attraction.getVisitors(true).size());

        // Test Empty Removals
        attraction.getVisitors(false).clear();
        attraction.getVisitors(true).clear();
        assertThrows(NoSuchElementException.class, () -> attraction.removeVisitor(false));
        assertThrows(NoSuchElementException.class, () -> attraction.removeVisitor(true));
    }

    @Test
    public void testRemainingRounds() {

        // Test First Normal Visitor & Hypothetical when Idle
        assertEquals(0, attraction.remainingRounds(firstNormalVisitor));
        assertEquals(1, attraction.remainingRounds(false));

        // Test First Fast Visitor & Hypothetical when Idle
        assertEquals(0, attraction.remainingRounds(firstFastVisitor));
        assertEquals(0, attraction.remainingRounds(true));

        // Setup for next tests
        attraction.tick(2.0f);
        attraction.addVisitor(firstNormalVisitor);
        attraction.addVisitor(firstFastVisitor);
        for (int i = 1; i < 5; i++) {
            Visitor reg = new Visitor(String.format("%d", i), 50.0f, false);
            Visitor fast = new Visitor(String.format("%d", i), 50.0f, false);
            attraction.addVisitor(reg);
            attraction.addVisitor(fast);
        }

        // Test First Normal Visitor & Hypothetical when not Idle
        assertEquals(1, attraction.remainingRounds(firstNormalVisitor));
        assertEquals(2, attraction.remainingRounds(false));

        // Test First Fast Visitor & Hypothetical when not Idle
        assertEquals(1, attraction.remainingRounds(firstFastVisitor));
        assertEquals(1, attraction.remainingRounds(true));

        // Setup for next tests
        attraction.getVisitors(false).clear();
        attraction.getVisitors(true).clear();
        attraction.tick(3.0f);

        // Test Fast Visitor Ratio Overflow & Normal Ratio
        Visitor[] regulars = new Visitor[5];
        Visitor[] fasts = new Visitor[25];
        for (int i = 0; i < 5; i++) {
            Visitor reg = new Visitor(String.format("%d", i), 50.0f, false);
            attraction.addVisitor(reg);
            regulars[i] = reg;
        }
        for (int i = 0; i < 25; i++) {
            Visitor fast = new Visitor(String.format("%d", i), 50.0f, true);
            attraction.addVisitor(fast);
            fasts[i] = fast;
        }
        
        assertEquals(0, attraction.remainingRounds(regulars[1]));
        assertEquals(0, attraction.remainingRounds(fasts[7]));
        assertEquals(1, attraction.remainingRounds(regulars[3]));
        assertEquals(1, attraction.remainingRounds(fasts[15]));
        assertEquals(2, attraction.remainingRounds(regulars[4]));
        assertEquals(2, attraction.remainingRounds(fasts[24]));
        
        // Setup for next tests
        attraction.getVisitors(false).clear();
        attraction.getVisitors(true).clear();

        // Test Normal Visitor Ratio Overflow & Normal Ratio
        regulars = new Visitor[9];
        fasts = new Visitor[21];
        for (int i = 0; i < 9; i++) {
            Visitor reg = new Visitor(String.format("%d", i), 50.0f, false);
            attraction.addVisitor(reg);
            regulars[i] = reg;
        }
        for (int i = 0; i < 21; i++) {
            Visitor fast = new Visitor(String.format("%d", i), 50.0f, true);
            attraction.addVisitor(fast);
            fasts[i] = fast;
        }

        assertEquals(0, attraction.remainingRounds(regulars[1]));
        assertEquals(0, attraction.remainingRounds(fasts[7]));
        assertEquals(1, attraction.remainingRounds(regulars[3]));
        assertEquals(1, attraction.remainingRounds(fasts[15]));
        assertEquals(2, attraction.remainingRounds(regulars[8]));
        assertEquals(2, attraction.remainingRounds(fasts[20]));

        // Test Null Visitor
        assertThrows(IllegalArgumentException.class, () -> attraction.remainingRounds(null));

        // Test Not in Line Visitor
        assertThrows(NoSuchElementException.class, () -> attraction.remainingRounds(normalVisitor));
    }

    @Test
    public void testRemainingTime() {

        // Test First Normal Visitor & Hypothetical when Idle
        assertEquals(0f, attraction.remainingTime(firstNormalVisitor));
        assertEquals(attraction.getDuration(), attraction.remainingTime(false));

        // Test First Fast Visitor & Hypothetical when Idle
        assertEquals(0f, attraction.remainingTime(firstFastVisitor));
        assertEquals(0f, attraction.remainingTime(true));

        // Setup for next tests
        attraction.tick(2.0f);
        attraction.addVisitor(firstNormalVisitor);
        attraction.addVisitor(firstFastVisitor);
        for (int i = 1; i < 5; i++) {
            Visitor reg = new Visitor(String.format("%d", i), 50.0f, false);
            Visitor fast = new Visitor(String.format("%d", i), 50.0f, false);
            attraction.addVisitor(reg);
            attraction.addVisitor(fast);
        }

        // Test First Normal Visitor & Hypothetical when not Idle
        assertEquals(attraction.getCurrDuration(), attraction.remainingTime(firstNormalVisitor));
        assertEquals(attraction.getCurrDuration() + attraction.getDuration(), attraction.remainingTime(false));

        // Test First Fast Visitor & Hypothetical when not Idle
        assertEquals(attraction.getCurrDuration(), attraction.remainingTime(firstFastVisitor));
        assertEquals(attraction.getCurrDuration(), attraction.remainingTime(true));

        // Setup for next tests
        attraction.getVisitors(false).clear();
        attraction.getVisitors(true).clear();
        attraction.tick(3.0f);

        // Test Fast Visitor Ratio Overflow & Normal Ratio
        Visitor[] regulars = new Visitor[5];
        Visitor[] fasts = new Visitor[255];
        for (int i = 0; i < 5; i++) {
            Visitor reg = new Visitor(String.format("%d", i), 50.0f, false);
            attraction.addVisitor(reg);
            regulars[i] = reg;
        }
        for (int i = 0; i < 25; i++) {
            Visitor fast = new Visitor(String.format("%d", i), 50.0f, true);
            attraction.addVisitor(fast);
            fasts[i] = fast;
        }
        
        assertEquals(0f, attraction.remainingTime(regulars[1]));
        assertEquals(0f, attraction.remainingTime(fasts[7]));
        assertEquals(attraction.getDuration(), attraction.remainingTime(regulars[3]));
        assertEquals(attraction.getDuration(), attraction.remainingTime(fasts[15]));
        assertEquals(attraction.getDuration() * 2f, attraction.remainingTime(regulars[4]));
        assertEquals(attraction.getDuration() * 2f, attraction.remainingTime(fasts[24]));
        
        // Setup for next tests
        attraction.getVisitors(false).clear();
        attraction.getVisitors(true).clear();

        // Test Normal Visitor Ratio Overflow & Normal Ratio
        regulars = new Visitor[9];
        fasts = new Visitor[21];
        for (int i = 0; i < 9; i++) {
            Visitor reg = new Visitor(String.format("%d", i), 50.0f, false);
            attraction.addVisitor(reg);
            regulars[i] = reg;

        }
        for (int i = 0; i < 21; i++) {
            Visitor fast = new Visitor(String.format("%d", i), 50.0f, true);
            attraction.addVisitor(fast);
            fasts[i] = fast;
        }

        assertEquals(0, attraction.remainingTime(regulars[1]));
        assertEquals(0, attraction.remainingTime(fasts[7]));
        assertEquals(attraction.getDuration(), attraction.remainingTime(regulars[3]));
        assertEquals(attraction.getDuration(), attraction.remainingTime(fasts[15]));
        assertEquals(2f * attraction.getDuration(), attraction.remainingTime(regulars[8]));
        assertEquals(2f * attraction.getDuration(), attraction.remainingTime(fasts[20]));

        // Test Null Visitor
        assertThrows(IllegalArgumentException.class, () -> attraction.remainingTime(null));

        // Test Not in Line Visitor
        assertThrows(NoSuchElementException.class, () -> attraction.remainingTime(normalVisitor));
    }

    @Test
    public void testTick() {
        
        // Test negative tick
        assertThrows(IllegalArgumentException.class, () -> attraction.tick(-1f));

        // Test clearing logic
        attraction.tick(1.0f);
        assertEquals(4.0f, attraction.getCurrDuration());
        assertTrue(attraction.getVisitors(false).isEmpty());
        assertTrue(attraction.getVisitors(true).isEmpty());

        // Test time progression logic
        attraction.tick(2.0f);
        assertEquals(2.0f, attraction.getCurrDuration());

        // Test time progression limiting logic
        attraction.tick(10.0f);
        assertEquals(0.0f, attraction.getCurrDuration());

        // Setup for next test
        attraction.getVisitors(false).clear();
        attraction.getVisitors(true).clear();
        for (int i = 0; i < 4; i++) {
            Visitor temp = new Visitor(String.format("%d", i), 10f, false);
            attraction.addVisitor(temp);
        }

        // Test tick waits for minimum visitors
        attraction.tick(1.0f);
        assertEquals(0f, attraction.getCurrDuration());
    }
}
