package tests;

import classes.Visitor;
import student.TestCase;

public class TestVisitor extends TestCase {
    
    private Visitor visitor1;
    private Visitor visitor2;
    private Visitor visitor3;

    public void setUp(){
        visitor1 = new Visitor ("Daniel", 30.0f);
        visitor2 = new Visitor ("Starla", 45.0f);
        visitor3 = new Visitor("Daniel", 45.0f);
    }
    @Test
    public void testConstructor(){
        assertEquals("Daniel", visitor1.getName());
        assertEquals("Starla", visitor2.getName());
        assertEquals(30.0f, visitor.getId());
        assertEquals(45.0f, visiotr.getId());
    }
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
        assertNotEquals(visiotr2.getId(), visitor3.getId());
        assertNotEquals(visitor1.getId(), visitor3.getId());
    }


}