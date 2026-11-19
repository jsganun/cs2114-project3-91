package classes;

public class Visitor {
    private static int nextId = 0;
    private final int id;
    private final String name;
    private final float maxWaitTime;
    private final boolean fastPass;

    public Visitor(String name, float maxWaitTime) {
        this(name, maxWaitTime, false);
    }

    /**
     * Creates a visitor with a unique ID.
     *
     * @param name the visitor's name
     * @param maxWaitTime the longest the visitor will wait, in minutes
     * @param fastPass {@code true} if the visitor has a fast pass
     * @throws IllegalArgumentException if {@code name} is blank or
     *         {@code maxWaitTime} is not a positive number
     */
    public Visitor(String name, float maxWaitTime, boolean fastPass) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (!Float.isFinite(maxWaitTime) || maxWaitTime <= 0f) {
            throw new IllegalArgumentException("Max wait time must be positive");
        }

        this.id = nextId++;
        this.name = name;
        this.maxWaitTime = maxWaitTime;
        this.fastPass = fastPass;
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

    public boolean getFastPass() {
        return fastPass;
    }

    @Override 
    public boolean equals(Object obj){
        if (obj == this){
            return true;
        }
        if (!(obj instanceof Visitor)){
            return false;
        }
        Visitor visitor = (Visitor) obj;
        if (this.getId() == visitor.getId() && this.getName().equals(visitor.getName()) 
            && Float.compare(this.getMaxWaitTime(), visitor.getMaxWaitTime()) == 0){
                return true;
            }
            return false;
    }
}
