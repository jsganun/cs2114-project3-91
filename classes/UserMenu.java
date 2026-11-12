package classes;

/**
 * Connects the park model to the console view. Runs the menu loop, reads each
 * choice through the {@link ParkView}, calls the matching {@link Park} or
 * {@link Attraction} method, and passes the result back to the view to
 * display.
 *
 * @author Sami Saleh
 * @version 11/12/2026
 */
public class UserMenu {
    // Menu option numbers. ParkView.showOptions() must list the same numbers.
    private static final int QUIT = 0;
    private static final int SHOW_ATTRACTIONS = 1;
    private static final int SHOW_ATTRACTION_DETAILS = 2;
    private static final int ADD_ATTRACTION = 3;
    private static final int REMOVE_ATTRACTION = 4;
    private static final int OPEN_ATTRACTION = 5;
    private static final int CLOSE_ATTRACTION = 6;
    private static final int OPEN_PARK = 7;
    private static final int CLOSE_PARK = 8;
    private static final int ADD_VISITOR = 9;
    private static final int ADVANCE_TIME = 10;

    private final Park park;
    private final ParkView view;

    /**
     * Creates a menu that manages the specified park and uses the specified
     * view for all input and output.
     *
     * @param park the park this menu manages
     * @param view the view used to show output and read input
     * @throws IllegalArgumentException if {@code park} or {@code view} is
     *         {@code null}
     */
    public UserMenu(Park park, ParkView view) {
        if (park == null || view == null) {
            throw new IllegalArgumentException("Park and view must not be null");
        }
        this.park = park;
        this.view = view;
    }

    /**
     * Runs the menu loop. Shows the options, reads the user's choice, and
     * carries out the matching action until the user chooses to quit.
     */
    public void run() {
        int choice;
        do {
            view.showOptions();
            choice = view.promptIntRange(QUIT, ADVANCE_TIME);
            handleChoice(choice);
        } 
        while (choice != QUIT);
    }

    /**
     * Carries out the action for the specified menu option.
     *
     * @param choice the menu option the user selected
     */
    private void handleChoice(int choice) {
        switch (choice) {
            case QUIT -> view.showMessage("Goodbye!");
            case SHOW_ATTRACTIONS -> showAttractions();
            case SHOW_ATTRACTION_DETAILS -> showAttractionDetails();
            case ADD_ATTRACTION -> addAttraction();
            case REMOVE_ATTRACTION -> removeAttraction();
            case OPEN_ATTRACTION -> openAttraction();
            case CLOSE_ATTRACTION -> closeAttraction();
            case OPEN_PARK -> openPark();
            case CLOSE_PARK -> closePark();
            case ADD_VISITOR -> addVisitor();
            case ADVANCE_TIME -> advanceTime();
            default -> view.showMessage("Unknown option: " + choice);
        }
    }

    /**
     * Shows every attraction currently in the park.
     */
    private void showAttractions() {
        notImplemented("View attractions");
    }

    /**
     * Shows the details of an attraction the user selects by ID.
     */
    private void showAttractionDetails() {
        notImplemented("Attraction details");
    }

    /**
     * Prompts the user for a name, capacity, and ride duration, then adds a
     * new attraction with those values to the park.
     */
    private void addAttraction() {
        notImplemented("Add attraction");
    }

    /**
     * Removes an attraction the user selects by ID after the user confirms.
     */
    private void removeAttraction() {
        notImplemented("Remove attraction");
    }

    /**
     * Opens an attraction the user selects by ID so visitors can join its
     * line.
     */
    private void openAttraction() {
        notImplemented("Open attraction");
    }

    /**
     * Closes an attraction the user selects by ID, which clears its line.
     */
    private void closeAttraction() {
        notImplemented("Close attraction");
    }

    /**
     * Opens the park after the user confirms.
     */
    private void openPark() {
        notImplemented("Open park");
    }

    /**
     * Closes the park after the user confirms.
     */
    private void closePark() {
        notImplemented("Close park");
    }

    /**
     * Adds a new visitor to the park and places them in the line of an
     * attraction the user selects by ID.
     */
    private void addVisitor() {
        notImplemented("Add visitor");
    }

    /**
     * Advances the simulation by an amount of time the user enters, letting
     * each attraction board visitors and run its rounds.
     */
    private void advanceTime() {
        notImplemented("Advance time");
    }

    /**
     * Tells the user that the specified option has not been built yet.
     *
     * @param option the name of the option
     */
    private void notImplemented(String option) {
        view.showMessage(option + " is not implemented yet.");
    }
}
