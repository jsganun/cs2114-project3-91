package classes;

import java.util.NoSuchElementException;

/**
 * Represents an attraction at a theme park.
 * @author Jonathan Ganun (jsganun)
 * @version 11/5/2026
 */
public class Attraction {
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
        if (rounds < 1) {
            return 0;
        }
        else if (rounds == 1) {
            return currDuration;
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
        if (rounds < 1) {
            return 0f;
        }
        else if (rounds == 1) {
            return currDuration;
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

}
