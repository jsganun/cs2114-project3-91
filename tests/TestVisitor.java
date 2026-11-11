package tests;

import classes.Visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestVisitor {
    
    private Visitor visitor1;
    private Visitor visitor2;
    private Visitor visitor3;

    @BeforeEach 
    public void setUp(){
        visitor1 = new Visitor ("Daniel", 30.0f);
        visitor2 = new Visitor ("Starla", 45.0f);
        visitor3 = new Visitor("Daniel", 45.0f);
    }

    @Test
    public void testConstructor(){
        assertEquals("Daniel", visitor1.getName());
        assertEquals("Starla", visitor2.getName());
        assertEquals(30.0f, visitor1.getMaxWaitTime());
        assertEquals(45.0f, visitor2.getMaxWaitTime());
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testEquals(){
        assertTrue(visitor1.equals(visitor1));
        assertFalse(visitor1.equals(null));
        assertFalse(visitor1.equals("Daniel"));
        assertFalse(visitor1.equals(visitor2));
        assertFalse(visitor1.equals(visitor3));
        assertFalse(visitor2.equals(visitor3));
    }

    @Test
    public void testID(){
        assertNotEquals(visitor1.getId(), visitor2.getId());
        assertNotEquals(visitor2.getId(), visitor3.getId());
        assertNotEquals(visitor1.getId(), visitor3.getId());
    }
}