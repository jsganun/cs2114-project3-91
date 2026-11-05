package classes;

public class Park {
    public static final int MAX_ATTRACTIONS = 0;
    public static final int MAX_VISITORS = 0;

    private boolean active;
    private final Attraction[] attractions;
    private final Visitor[] visitors;

    public Park() {
        attractions = new Attraction[MAX_ATTRACTIONS];
        visitors = new Visitor[MAX_VISITORS];
    }

    public boolean getActive() {
        throw new UnsupportedOperationException("TODO");
    }

    public Attraction[] getAttractions() {
        throw new UnsupportedOperationException("TODO");
    }

    public Visitor[] getVisitors() {
        throw new UnsupportedOperationException("TODO");
    }

    public void setActive(boolean state) {
        throw new UnsupportedOperationException("TODO");
    }

    public boolean addAttraction(String name, int capacity, float duration) {
        throw new UnsupportedOperationException("TODO");
    }

    public boolean removeAttraction(int id) {
        throw new UnsupportedOperationException("TODO");
    }

    public boolean addVisitor(String name, float maxWaitTime) {
        throw new UnsupportedOperationException("TODO");
    }

    public boolean removeVisitor(int id) {
        throw new UnsupportedOperationException("TODO");
    }
}
