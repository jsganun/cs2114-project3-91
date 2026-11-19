package tests;

import classes.ParkView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that the ParkView prompts re-prompt on bad input instead of crashing.
 * Each test types its input into the view through System.in.
 *
 * @author Sami Saleh
 * @version Nov 19, 2026
 */
public class TestParkView
{
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    /**
     * Hides the view's printed prompts so the test output stays readable
     */
    @BeforeEach
    public void setUp()
    {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
    }

    /**
     * Puts the real console input and output back after each test
     */
    @AfterEach
    public void tearDown()
    {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /**
     * Creates a view that reads the given lines as if the user typed them
     *
     * @param lines the lines the user types, in order
     *
     * @return ParkView a view that reads those lines
     */
    private ParkView viewWithInput(String... lines)
    {
        String typed = String.join("\n", lines) + "\n";
        System.setIn(new ByteArrayInputStream(typed.getBytes()));
        return new ParkView();
    }

    @Test
    public void testPromptIntRange()
    {
        assertEquals(5, viewWithInput("5").promptIntRange(0, 10));

        // Letters, decimals, and out of range numbers are all re-prompted
        ParkView view = viewWithInput("abc", "2.5", "11", "-1", "7");
        assertEquals(7, view.promptIntRange(0, 10));
    }

    @Test
    public void testPromptFloatRange()
    {
        assertEquals(2.5f, viewWithInput("2.5").promptFloatRange(0.1f, 1000f));

        // Letters, NaN, and out of range numbers are all re-prompted
        ParkView view = viewWithInput("abc", "NaN", "0", "1001", "30");
        assertEquals(30f, view.promptFloatRange(0.1f, 1000f));
    }

    @Test
    public void testPromptConfirmation()
    {
        ParkView view = viewWithInput("y", "N");
        assertTrue(view.promptConfirmation());
        assertFalse(view.promptConfirmation());

        // Anything other than Y or N is re-prompted, like the fast pass question
        view = viewWithInput("maybe", "", "yes", "Y");
        assertTrue(view.promptConfirmation());
    }

    @Test
    public void testPromptString()
    {
        assertEquals("Coaster", viewWithInput("Coaster").promptString(10));

        // A string over the character limit is re-prompted
        ParkView view = viewWithInput("This name is too long", "Tea Cups");
        assertEquals("Tea Cups", view.promptString(10));
    }

    @Test
    public void testShowNull()
    {
        ParkView view = viewWithInput();
        assertThrows(IllegalArgumentException.class,
            () -> view.showAttractions(null));
        assertThrows(IllegalArgumentException.class,
            () -> view.showAttractionDetails(null));
        assertThrows(IllegalArgumentException.class,
            () -> view.showVisitors(null));
    }
}
