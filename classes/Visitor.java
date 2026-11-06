package classes;

public class Visitor {
    private final int id;
    private final String name;
    private final float maxWaitTime;

    public Visitor(String name, float maxWaitTime) {
        id = 0;
        this.name = name;
        this.maxWaitTime = maxWaitTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getMaxWaitTime() {
        return maxWaitTime;
    }
}
