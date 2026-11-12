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

    // Limits for new attractions and visitors. These match the checks in
    // Park.addAttraction() and Park.addVisitor(); use Park's constants
    // instead once it has them.
    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_CAPACITY = 1;
    private static final int MAX_CAPACITY = 100;
    private static final float MIN_DURATION = 0.1f;
    private static final float MAX_DURATION = 1000f;
    private static final float MIN_WAIT_LIMIT = 0.1f;
    private static final float MAX_WAIT_LIMIT = 1000f;

    // Limits for advancing time, in minutes. Time passes in steps of
    // TIME_STEP because Attraction.tick() boards riders at most once per call.
    private static final float MIN_ADVANCE = 0.1f;
    private static final float MAX_ADVANCE = 60f;
    private static final float TIME_STEP = 1f;

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
        Attraction attraction = selectAttraction();
        if (attraction != null) {
            view.showAttractionDetails(attraction);
        }
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
        Attraction attraction = selectAttraction();
        if (attraction == null) {
            return;
        }

        view.showMessage("Remove " + attraction.getName() + "?");
        if (!view.promptConfirmation()) {
            view.showMessage("Cancelled. " + attraction.getName()
                + " was not removed.");
        }
        else if (park.removeAttraction(attraction.getId())) {
            view.showMessage("Removed " + attraction.getName() + ".");
        }
        else {
            view.showMessage("Could not remove " + attraction.getName() + ".");
        }
    }

    /**
     * Opens an attraction the user selects by ID so visitors can join its
     * line. If the attraction is already open, the user is told so.
     */
    private void openAttraction() {
        Attraction attraction = selectAttraction();
        if (attraction == null) {
            return;
        }

        if (attraction.getActive()) {
            view.showMessage(attraction.getName() + " is already open.");
        }
        else {
            attraction.setActive(true);
            view.showMessage(attraction.getName() + " is now open.");
        }
    }

    /**
     * Closes an attraction the user selects by ID, which clears its line. If
     * people are waiting, the user must confirm first. If the attraction is
     * already closed, the user is told so.
     */
    private void closeAttraction() {
        Attraction attraction = selectAttraction();
        if (attraction == null) {
            return;
        }

        if (!attraction.getActive()) {
            view.showMessage(attraction.getName() + " is already closed.");
            return;
        }

        int waiting = attraction.getLineSize();
        if (waiting > 0) {
            view.showMessage("Closing " + attraction.getName()
                + " sends away the " + waiting
                + " people in its line. Close it?");
            if (!view.promptConfirmation()) {
                view.showMessage("Cancelled. " + attraction.getName()
                    + " is still open.");
                return;
            }
        }

        attraction.setActive(false);
        view.showMessage(attraction.getName() + " is now closed.");
    }

    /**
     * Opens the park after the user confirms, so visitors can be added. If
     * the park is already open, the user is told so.
     */
    private void openPark() {
        if (park.getActive()) {
            view.showMessage("The park is already open.");
            return;
        }

        view.showMessage("Open the park?");
        if (view.promptConfirmation()) {
            park.setActive(true);
            view.showMessage("The park is now open. Visitors can be added.");
        }
        else {
            view.showMessage("Cancelled. The park is still closed.");
        }
    }

    /**
     * Closes the park after the user confirms, so no new visitors can be
     * added. Attractions keep running for the people already in line. If the
     * park is already closed, the user is told so.
     */
    private void closePark() {
        if (!park.getActive()) {
            view.showMessage("The park is already closed.");
            return;
        }

        view.showMessage("Close the park?");
        if (view.promptConfirmation()) {
            park.setActive(false);
            view.showMessage("The park is now closed. No new visitors can be "
                + "added.");
        }
        else {
            view.showMessage("Cancelled. The park is still open.");
        }
    }

    /**
     * Adds a new visitor to the park and places them in the line of an open
     * attraction the user selects by ID. Tells the user how many rounds and
     * minutes the visitor will wait, or why they did not join the line.
     */
    private void addVisitor() {
        if (!park.getActive()) {
            view.showMessage("The park is closed. Open the park before adding "
                + "visitors.");
            return;
        }

        Attraction attraction = selectAttraction();
        if (attraction == null) {
            return;
        }

        if (!attraction.getActive()) {
            view.showMessage(attraction.getName() + " is closed. Open it "
                + "before adding visitors to its line.");
            return;
        }

        view.showMessage("Enter the visitor's name (up to " + MAX_NAME_LENGTH
            + " characters):");
        String name = view.promptString(MAX_NAME_LENGTH).trim();
        while (name.isEmpty()) {
            view.showMessage("The name cannot be blank. Try again:");
            name = view.promptString(MAX_NAME_LENGTH).trim();
        }

        view.showMessage("Enter the longest " + name + " will wait, in minutes ("
            + MIN_WAIT_LIMIT + "-" + MAX_WAIT_LIMIT + "):");
        float maxWait = view.promptFloatRange(MIN_WAIT_LIMIT, MAX_WAIT_LIMIT);

        if (!park.addVisitor(name, maxWait)) {
            view.showMessage("Could not add " + name + ". The park is full ("
                + Park.MAX_VISITORS + " visitors).");
            return;
        }

        Visitor visitor = newestVisitor();
        if (attraction.addVisitor(visitor)) {
            view.showMessage(name + " joined the line for "
                + attraction.getName() + ". Rounds until boarding: "
                + attraction.remainingRounds(visitor) + ". Estimated wait: "
                + attraction.remainingTime(visitor) + " minutes.");
        }
        else {
            view.showMessage(name + " entered the park but did not join the "
                + "line. The wait for " + attraction.getName() + " is "
                + attraction.remainingTime() + " minutes, which is longer than "
                + maxWait + " minutes.");
        }
    }

    /**
     * Advances the simulation by a number of minutes the user enters. Every
     * attraction runs its rounds and boards visitors as time passes, then the
     * user is shown how each line changed.
     */
    private void advanceTime() {
        Attraction[] attractions = currentAttractions();
        if (attractions.length == 0) {
            view.showMessage("There are no attractions yet.");
            return;
        }

        view.showMessage("How many minutes should pass? (" + MIN_ADVANCE + "-"
            + MAX_ADVANCE + "):");
        float minutes = view.promptFloatRange(MIN_ADVANCE, MAX_ADVANCE);

        int[] waitingBefore = new int[attractions.length];
        for (int i = 0; i < attractions.length; i++) {
            waitingBefore[i] = attractions[i].getLineSize();
        }

        float remaining = minutes;
        while (remaining > 0) {
            float step = Math.min(TIME_STEP, remaining);
            for (Attraction attraction : attractions) {
                attraction.tick(step);
            }
            remaining -= step;
        }

        view.showMessage(minutes + " minutes passed.");
        for (int i = 0; i < attractions.length; i++) {
            view.showMessage("  " + attractions[i].getName() + ": "
                + waitingBefore[i] + " -> " + attractions[i].getLineSize()
                + " people waiting");
        }
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
     * Shows the park's attractions and prompts the user for the ID of one of
     * them. Prompts again until the ID belongs to an attraction in the park,
     * since IDs can have gaps after an attraction is removed.
     *
     * @return the attraction the user selected, or {@code null} if the park
     *         has no attractions
     */
    private Attraction selectAttraction() {
        Attraction[] attractions = currentAttractions();
        if (attractions.length == 0) {
            view.showMessage("There are no attractions yet.");
            return null;
        }

        view.showAttractions(attractions);

        int minId = attractions[0].getId();
        int maxId = attractions[0].getId();
        for (Attraction attraction : attractions) {
            minId = Math.min(minId, attraction.getId());
            maxId = Math.max(maxId, attraction.getId());
        }

        view.showMessage("Enter the ID of the attraction:");
        int id = view.promptIntRange(minId, maxId);
        Attraction selected = findAttraction(id);
        while (selected == null) {
            view.showMessage("There is no attraction with ID " + id
                + ". Enter one of the IDs listed above:");
            id = view.promptIntRange(minId, maxId);
            selected = findAttraction(id);
        }
        return selected;
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
     * Finds the attraction with the specified ID.
     *
     * @param id the ID to look for
     * @return the attraction with that ID, or {@code null} if there is none
     */
    private Attraction findAttraction(int id) {
        for (Attraction attraction : currentAttractions()) {
            if (attraction.getId() == id) {
                return attraction;
            }
        }
        return null;
    }

    /**
     * Returns the visitor most recently added to the park. Park.addVisitor()
     * does not return the visitor it creates, but it fills the first empty
     * slot, so the newest visitor is the last one in the park's array.
     *
     * @return the newest visitor, or {@code null} if the park has none
     */
    private Visitor newestVisitor() {
        Visitor[] visitors = park.getVisitors();
        for (int i = visitors.length - 1; i >= 0; i--) {
            if (visitors[i] != null) {
                return visitors[i];
            }
        }
        return null;
    }
}
