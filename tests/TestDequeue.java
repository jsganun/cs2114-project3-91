package tests;

import classes.Dequeue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the Dequeue class
 * 
 * @author Jonathan Ganun (jsganun)
 * @version 11/5/2026
 */
public class TestDequeue {

    private Dequeue<String> blank;
    private Dequeue<String> dequeue;

    @BeforeEach
    public void setUp() {

        blank = new Dequeue<>();
        dequeue = new Dequeue<>();

        dequeue.add("A");
        dequeue.add("B");
        dequeue.add("C");
        dequeue.add("D");
        dequeue.add("E");
    }

    @Test
    public void testAdd() {
        blank.add("first");
        blank.add("second");

        assertEquals(2, blank.size());
        assertEquals("first", blank.peek());
        assertEquals("{first, second}", blank.toString());
        assertThrows(IllegalArgumentException.class, () -> blank.add(null));
    }

    @Test
    public void testRemove() {
        assertEquals("A", dequeue.remove());
        assertEquals("B", dequeue.remove());
        assertEquals("C", dequeue.remove());
        assertEquals("D", dequeue.remove());
        assertEquals("E", dequeue.remove());
        assertTrue(dequeue.isEmpty());
        assertThrows(NoSuchElementException.class, dequeue::remove);
    }

    @Test
    public void testPeek() {
        assertEquals("A", dequeue.peek());
        assertEquals(5, dequeue.size());
        assertThrows(NoSuchElementException.class, blank::peek);
    }

    @Test
    public void testSize() {
        assertEquals(0, blank.size());
        assertEquals(5, dequeue.size());

        dequeue.remove();
        assertEquals(4, dequeue.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(blank.isEmpty());
        assertFalse(dequeue.isEmpty());

        dequeue.clear();
        assertTrue(dequeue.isEmpty());
    }

    @Test
    public void testClear() {
        dequeue.clear();

        assertEquals(0, dequeue.size());
        assertTrue(dequeue.isEmpty());
        assertThrows(NoSuchElementException.class, dequeue::peek);

        dequeue.add("after clear");
        assertEquals("after clear", dequeue.peek());
        assertEquals(1, dequeue.size());
    }

    @Test
    public void testFind() {
        assertEquals(0, dequeue.find("A"));
        assertEquals(2, dequeue.find("C"));
        assertEquals(4, dequeue.find("E"));
        assertEquals(-1, dequeue.find("missing"));
        assertEquals(-1, blank.find("missing"));
    }

    @Test
    public void testToString() {
        assertEquals("{}", blank.toString());
        assertEquals("{A, B, C, D, E}", dequeue.toString());
    }
}
