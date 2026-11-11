package classes;

public class Park {
    public static final int MAX_ATTRACTIONS = 10;
    public static final int MAX_VISITORS = 500;

    private boolean active;
    private final Attraction[] attractions;
    private final Visitor[] visitors;

    public Park() {
        attractions = new Attraction[MAX_ATTRACTIONS];
        visitors = new Visitor[MAX_VISITORS];
    }

    public boolean getActive() {
        return active;
    }

    public Attraction[] getAttractions() {
        return attractions.clone();
    }

    public Visitor[] getVisitors() {
        return visitors.clone();
    }

    public void setActive(boolean state) {
        active = state;
    }

    public boolean addAttraction(String name, int capacity, float duration) {
        if (name == null || name.trim().isEmpty()){
            return false;
        }
        if (capacity <= 0 || capacity > 100){
            return false;
        }
        if (!Float.isFinite(duration) || duration <= 0 || duration > 1000){
            return false;
        }
        Attraction meep = new Attraction(name, capacity, duration);
        for (int i = 0; i < attractions.length; i++){
            if (attractions[i] == null){
                attractions[i] = meep;
                return true;
            }
        }
        return false;

    }
    public boolean removeAttraction(int id) {
        if (id < 0 ){
            return false;
        }
        for (int i = 0; i < attractions.length; i++){
            if (attractions[i] != null && attractions[i].getId()==id){
                for (int j = i; j < attractions.length-1; j++){
                    attractions[j] = attractions[j+1];
                }
                attractions[attractions.length - 1] = null;
                return true;
            }
            
        }
        return false;
    }

    public boolean addVisitor(String name, float maxWaitTime) {
        if (name == null || name.trim().isEmpty()){
            return false;
        }
        if (!Float.isFinite(maxWaitTime) || maxWaitTime <= 0 || maxWaitTime > 1000){
            return false;
        }
        Visitor meep = new Visitor(name, maxWaitTime);
        for (int i = 0; i < visitors.length; i++){
            if (visitors[i] == null){
                visitors[i] = meep;
                return true;
            }
        }
        return false;

    }
    public boolean removeVisitor(int id) {
        if (id < 0 ){
            return false;
        }
        for (int i = 0; i < visitors.length; i++){
            if (visitors[i] != null && visitors[i].getId()==id){
                for (int j = i; j < visitors.length-1; j++){
                    visitors[j] = visitors[j+1];
                }
                visitors[visitors.length - 1] = null;
                return true;
            }
        }
        return false;
    }
}
