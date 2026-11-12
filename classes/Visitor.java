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

    public Visitor(String name, float maxWaitTime, boolean fastPass) {
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
