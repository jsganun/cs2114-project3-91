package tests;

import classes.Attraction;
import classes.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAttraction {

    private Attraction attraction;
    private Visitor visitor;

    @BeforeEach
    public void setUp() {
        attraction = new Attraction("Test Attraction", 10, 5.0f);
        visitor = new Visitor("Test Visitor", 10.0f);
    }

    @Test
    public void testAttraction() {
        // TODO: Add assertions.
    }

    @Test
    public void testGetId() {
        // TODO: Add assertions.
    }

    @Test
    public void testGetName() {
        // TODO: Add assertions.
    }

    @Test
    public void testGetActive() {
        // TODO: Add assertions.
    }

    @Test
    public void testGetCapacity() {
        // TODO: Add assertions.
    }

    @Test
    public void testGetDuration() {
        // TODO: Add assertions.
    }

    @Test
    public void testGetCurrDuration() {
        // TODO: Add assertions.
    }

    @Test
    public void testGetVisitors() {
        // TODO: Add assertions.
    }

    @Test
    public void testSetActive() {
        // TODO: Add assertions.
    }

    @Test
    public void testAddVisitor() {
        // TODO: Add assertions.
    }

    @Test
    public void testRemoveVisitor() {
        // TODO: Add assertions.
    }

    @Test
    public void testRemainingRounds() {
        // TODO: Add assertions.
    }

    @Test
    public void testRemainingTime() {
        // TODO: Add assertions.
    }
}
