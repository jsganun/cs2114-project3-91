package classes;

import java.util.NoSuchElementException;

/**
 * Represents an attraction at a theme park.
 * @author Jonathan Ganun (jsganun)
 * @version 11/5/2026
 */
public class Attraction {

    // Fast Visitors : Normal Visitors to board ride
    private static final int FAST_TO_NORMAL_RATIO = 3;

    // Capacity / MIN_CAPACITY_TO_BEGIN = Visitors required to start a round
    private static final int MIN_CAPACITY_TO_BEGIN = 2;

    private static int currId = 0;

    private final int id;
    private final String name;
    private boolean active;
    private final int capacity;
    private final float duration;
    private float currDuration;
    private final Dequeue<Visitor> visitors = new Dequeue<>();

    /**
     * Creates an attraction with a unique ID and the specified name, capacity,
     * and ride duration.
     *
     * @param name the attraction name
     * @param capacity the number of visitors served in each round
     * @param duration the duration of one round
     */
    public Attraction(String name, int capacity, float duration) {
        id = currId++;
        this.name = name;
        this.capacity = capacity;
        this.duration = duration;
    }

    /**
     * Returns this attraction's unique ID.
     *
     * @return this attraction's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns this attraction's name.
     *
     * @return this attraction's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether this attraction is active.
     *
     * @return {@code true} if this attraction is active; otherwise,
     *         {@code false}
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Returns the number of visitors this attraction serves in each round.
     *
     * @return this attraction's capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the duration of one round at this attraction.
     *
     * @return the duration of one round
     */
    public float getDuration() {
        return duration;
    }

    /**
     * Returns the elapsed duration of the current round.
     *
     * @return the current round's elapsed duration
     */
    public float getCurrDuration() {
        return currDuration;
    }

    /**
     * Returns the visitor line for this attraction.
     *
     * @return this attraction's visitor line
     */
    public Dequeue<Visitor> getVisitors() {
        return visitors;
    }

    /**
     * Returns the number of visitors waiting in this attraction's line.
     *
     * @return the number of visitors in line
     */
    public int getLineSize() {
        return visitors.size();
    }

    /**
     * Sets whether this attraction is active.
     *
     * @param state {@code true} to activate this attraction; {@code false} to
     *        deactivate it
     */
    public void setActive(boolean state) {
        if (active != state) {
            active = state;
            if (!active) {
                visitors.clear();
                currDuration = 0f;
            }
        }
    }

    /**
     * Adds a visitor to the end of the line if their maximum wait time is at
     * least the estimated wait time and the attraction is active.
     *
     * @param visitor the visitor to add
     * @return {@code true} if the visitor was added; otherwise, {@code false}
     * @throws IllegalArgumentException if {@code visitor} is {@code null}
     */
    public boolean addVisitor(Visitor visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException();
        }
        
        if (!active) {
            return false;
        }

        if (remainingTime() > visitor.getMaxWaitTime()) {
            return false;
        }
        else {
            visitors.add(visitor);
            return true;
        }
    }

    /**
     * Removes and returns the visitor at the front of the line.
     *
     * @return the visitor removed from the front of the line
     * @throws NoSuchElementException if the line is empty
     */
    public Visitor removeVisitor() {
        return visitors.remove();
    }

    /**
     * Estimates the number of rounds remaining for a hypothetical visitor
     * positioned at the very end of the line.
     *
     * @return the estimated number of rounds before that visitor is served
     */
    public int remainingRounds() {
        if (currDuration == 0) {
            return visitors.size() / capacity;
        }
        else {
            return (visitors.size() / capacity) + 1;
        }
    }

    /**
     * Estimates the number of rounds remaining before the specified visitor is
     * served.
     *
     * @param visitor the visitor whose position to estimate
     * @return the estimated number of rounds before the visitor is served
     * @throws NoSuchElementException if the visitor is not in this attraction's
     *         line
     */
    public int remainingRounds(Visitor visitor) {
        int index = visitors.find(visitor);
        if (index == -1) {
            throw new NoSuchElementException();
        }

        if (currDuration == 0) {
            return index / capacity;
        }
        else {
            return (index / capacity) + 1;
        }
    }

    /**
     * Estimates the remaining wait time for a hypothetical visitor positioned
     * at the very end of the line.
     *
     * @return the estimated wait time
     */
    public float remainingTime() {
        int rounds = remainingRounds();
        // When the ride is idle, none of the rounds ahead has started yet, so
        // each one takes a full duration.
        if (currDuration == 0) {
            return rounds * duration;
        }
        else {
            return currDuration + ((rounds - 1) * duration);
        }
    }

    /**
     * Estimates the remaining wait time for the specified visitor.
     *
     * @param visitor the visitor whose wait time to estimate
     * @return the estimated wait time
     * @throws NoSuchElementException if the visitor is not in this attraction's
     *         line
     */
    public float remainingTime(Visitor visitor) {
        int index = visitors.find(visitor);
        if (index == -1) {
            throw new NoSuchElementException();
        }
        
        int rounds = remainingRounds(visitor);
        // When the ride is idle, none of the rounds ahead has started yet, so
        // each one takes a full duration.
        if (currDuration == 0) {
            return rounds * duration;
        }
        else {
            return currDuration + ((rounds - 1) * duration);
        }
    }

    /**
     * Used to simulate the logic of the attraction
     * 
     * @param delta the amount of time since last tick.
     * @throws IllegalArgumentException if the passed delta < 0
     */
    public void tick(float delta) {

        // Throw an error if delta isn't greater or equal to 0
        if (delta < 0f) {
            throw new IllegalArgumentException("Delta must be not be a negative number");
        }

        // Do not process if not active
        if (!active) {
            return;
        }

        // Don't start the ride until there is at least half of the capacity waiting.
        if (currDuration == 0 && visitors.size() >= capacity / 2) {
            int loops = Math.min(visitors.size(), capacity);
            for (int i = 0; i < loops; i++) {
                visitors.remove();
            }
            currDuration = duration;
            return;
        }

        // If the ride is running, start counting down the duration
        if (currDuration > 0) {
            currDuration = Math.clamp(currDuration - delta, 0f, duration);
        }
    }

    /**
     * Calculates how many from both the regular and fast line can board a round
     * 
     * @param regularSize the size of the regular line
     * @param fastSize the size of the fast line
     * @return [regular, fast pass] visitors that should board the attraction
     */
    private int[] getBoardingCounts(int regularSize, int fastSize) {

        // Case: Total visitors waiting <= capacity
        if (regularSize + fastSize <= capacity) {
            return new int[] {regularSize, fastSize};
        }

        // Calculate the required amount of regulars and fasts to maintain ratio
        int requiredRegulars = capacity / (FAST_TO_NORMAL_RATIO + 1);
        int requiredFasts = (capacity * FAST_TO_NORMAL_RATIO) / (FAST_TO_NORMAL_RATIO + 1);

        // Add an extra required fast if both don't add up due to integer math
        if (requiredRegulars + requiredFasts < capacity) {
            requiredFasts++;
        }

        // Case: Not enough regulars to fill ratio
        if (regularSize < requiredRegulars) {
            return new int[] {regularSize, capacity - regularSize};
        }

        // Case: Not enough fasts to fill ratio
        if (fastSize < requiredFasts) {
            return new int[] {capacity - fastSize, fastSize};
        }

        // Case: Enough of regulars and fasts to fill ratio
        return new int[] {requiredRegulars, requiredFasts};
    }

    /**
     * Calculates how many rounds it will take before a visitor at a position can board
     * 
     * @param pos the position of the visitor
     * @param fast does the visitor have a fast pass
     * @param regularSize the size of the regular line
     * @param fastSize the size of the fast pass line
     * @return the number of rounds the visitor will need to wait to board
     */
    private int remainingRoundsFromPosition(
        int pos, 
        boolean fast, 
        int regularSize, 
        int fastSize
    ) 
    {
        // Case: Total Visitors <= Capacity
        if (regularSize + fastSize <= capacity) {
            if (currDuration == 0f) {
                return 0;
            }
            else {
                return 1;
            }
        }

        // Case: Total Visitors > Capacity
        int rounds = currDuration > 0 ? 1 : 0;

        while (true) {
            int[] boarding = getBoardingCounts(regularSize, fastSize);
            int boardingAmount = boarding[fast ? 1 : 0];
            if (pos < boardingAmount) {
                return rounds;
            }

            pos -= boardingAmount;
            regularSize -= boarding[0];
            fastSize -= boarding[1];
            rounds++;
        }
    }

    /**
     * Calculates how much time it will take before a visitor at a position can board
     * 
     * @param pos the position of the visitor
     * @param fast does the visitor have a fast pass
     * @param regularSize the size of the regular line
     * @param fastSize the size of the fast pass line
     * @return the amount of time the visitor must wait before boarding
     */
    private float remainingTimeFromPosition(
        int pos, 
        boolean fast, 
        int regularSize, 
        int fastSize
    )
    {
        int rounds = remainingRoundsFromPosition(pos, fast, regularSize, fastSize);
        if (currDuration > 0) {
            return currDuration + (rounds - 1) * duration;
        }
        return rounds * duration;
    }
}
