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

    // Limits for new attractions. These match the checks in
    // Park.addAttraction(); use Park's constants instead once it has them.
    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_CAPACITY = 1;
    private static final int MAX_CAPACITY = 100;
    private static final float MIN_DURATION = 0.1f;
    private static final float MAX_DURATION = 1000f;

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
     * Shows every attraction currently in the park, or a message if the park
     * has none.
     */
    private void showAttractions() {
        Attraction[] attractions = currentAttractions();
        if (attractions.length == 0) {
            view.showMessage("There are no attractions yet.");
        }
        else {
            view.showAttractions(attractions);
        }
    }

    /**
     * Shows the details of an attraction the user selects by ID.
     */
    private void showAttractionDetails() {
        notImplemented("Attraction details");
    }

    /**
     * Prompts the user for a name, capacity, and ride duration, then adds a
     * new attraction with those values to the park. If the park already has
     * the maximum number of attractions, the user is told so and nothing is
     * prompted.
     */
    private void addAttraction() {
        if (currentAttractions().length >= Park.MAX_ATTRACTIONS) {
            view.showMessage("The park already has the maximum of "
                + Park.MAX_ATTRACTIONS + " attractions.");
            return;
        }

        String name = promptAttractionName();

        view.showMessage("Enter the capacity (" + MIN_CAPACITY + "-"
            + MAX_CAPACITY + " riders per round):");
        int capacity = view.promptIntRange(MIN_CAPACITY, MAX_CAPACITY);

        view.showMessage("Enter the ride duration in minutes (" + MIN_DURATION
            + "-" + MAX_DURATION + "):");
        float duration = view.promptFloatRange(MIN_DURATION, MAX_DURATION);

        if (park.addAttraction(name, capacity, duration)) {
            Attraction added = findAttraction(name);
            view.showMessage("Added " + added.getName() + " (ID "
                + added.getId() + ").");
        }
        else {
            view.showMessage("Could not add " + name + ".");
        }
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
     * Prompts the user for a new attraction's name until they enter one that
     * is not blank and is not already used by another attraction.
     *
     * @return the name the user entered, with surrounding spaces removed
     */
    private String promptAttractionName() {
        view.showMessage("Enter the attraction's name (up to "
            + MAX_NAME_LENGTH + " characters):");
        String name = view.promptString(MAX_NAME_LENGTH).trim();

        // Park does not reject duplicate names yet, so the menu checks here.
        while (name.isEmpty() || findAttraction(name) != null) {
            if (name.isEmpty()) {
                view.showMessage("The name cannot be blank. Try again:");
            }
            else {
                view.showMessage("An attraction named "
                    + findAttraction(name).getName()
                    + " already exists. Enter a different name:");
            }
            name = view.promptString(MAX_NAME_LENGTH).trim();
        }
        return name;
    }

    /**
     * Returns the attractions currently in the park, without the park's empty
     * slots.
     *
     * @return an array holding only the park's attractions; empty if the park
     *         has none
     */
    private Attraction[] currentAttractions() {
        Attraction[] slots = park.getAttractions();

        int count = 0;
        for (Attraction attraction : slots) {
            if (attraction != null) {
                count++;
            }
        }

        Attraction[] attractions = new Attraction[count];
        int index = 0;
        for (Attraction attraction : slots) {
            if (attraction != null) {
                attractions[index] = attraction;
                index++;
            }
        }
        return attractions;
    }

    /**
     * Finds the attraction with the specified name, ignoring case and
     * surrounding spaces.
     *
     * @param name the name to look for
     * @return the attraction with that name, or {@code null} if there is none
     */
    private Attraction findAttraction(String name) {
        for (Attraction attraction : currentAttractions()) {
            if (attraction.getName().trim().equalsIgnoreCase(name.trim())) {
                return attraction;
            }
        }
        return null;
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
