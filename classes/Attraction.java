package classes;

public class Attraction {
    private final int id;
    private final String name;
    private boolean active;
    private final int capacity;
    private final float duration;
    private float currDuration;
    private final Dequeue<Visitor> visitors = new Dequeue<>();

    public Attraction(String name, int capacity, float duration) {
        id = 0;
        this.name = name;
        this.capacity = capacity;
        this.duration = duration;
    }

    public int getId() {
        throw new UnsupportedOperationException("TODO");
    }

    public String getName() {
        throw new UnsupportedOperationException("TODO");
    }

    public boolean getActive() {
        throw new UnsupportedOperationException("TODO");
    }

    public int getCapacity() {
        throw new UnsupportedOperationException("TODO");
    }

    public float getDuration() {
        throw new UnsupportedOperationException("TODO");
    }

    public float getCurrDuration() {
        throw new UnsupportedOperationException("TODO");
    }

    public Dequeue<Visitor> getVisitors() {
        throw new UnsupportedOperationException("TODO");
    }

    public void setActive(boolean state) {
        throw new UnsupportedOperationException("TODO");
    }

    public boolean addVisitor(Visitor visitor) {
        throw new UnsupportedOperationException("TODO");
    }

    public void removeVisitor() {
        throw new UnsupportedOperationException("TODO");
    }

    public int remainingRounds(Visitor visitor) {
        throw new UnsupportedOperationException("TODO");
    }

    public float remainingTime(Visitor visitor) {
        throw new UnsupportedOperationException("TODO");
    }
}
