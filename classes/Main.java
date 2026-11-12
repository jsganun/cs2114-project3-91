package classes;

/**
 * Starts the theme park queue system. Creates the park, the view, and the
 * menu that connects them, then runs the menu loop.
 *
 * @author Sami Saleh
 * @version 11/12/2026
 */
public class Main {
    // How often a new visitor arrives. Not used yet: visitors are added
    // through the menu until automatic arrivals are built.
    private static final float VISITOR_INTERVAL = 0.0f;
    private static UserMenu menu;

    /**
     * Prevents this class from being instantiated.
     */
    private Main() {
    }

    /**
     * Creates the park, view, and menu, then runs the menu loop until the
     * user quits.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        menu = new UserMenu(new Park(), new ParkView());
        menu.run();
    }
}
