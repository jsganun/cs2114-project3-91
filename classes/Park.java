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

    /**
     * Opens or closes the park.
     *
     * @param state {@code true} to open the park; {@code false} to close it
     * @return {@code true} if the park changed state; {@code false} if it was
     *         already open or closed
     */
    public boolean setActive(boolean state) {
        if (active == state){
            return false;
        }
        active = state;
        return true;
    }

    /**
     * Adds an attraction to the park. The attraction is not added if the name
     * is blank or already used (ignoring case), the capacity is not between 1
     * and 100, the duration is not between 0 and 1000 minutes, or the park is
     * full.
     *
     * @param name the attraction's name
     * @param capacity the number of riders per round
     * @param duration the length of one round, in minutes
     * @return {@code true} if the attraction was added; otherwise,
     *         {@code false}
     */
    public boolean addAttraction(String name, int capacity, float duration) {
        if (name == null || name.trim().isEmpty()){
            return false;
        }
        for (int i = 0; i < attractions.length; i++){
            if (attractions[i] != null
                && attractions[i].getName().trim().equalsIgnoreCase(name.trim())){
                return false;
            }
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

    /**
     * Adds a visitor without a fast pass to the park.
     *
     * @param name the visitor's name
     * @param maxWaitTime the longest the visitor will wait, in minutes
     * @return {@code true} if the visitor was added; otherwise, {@code false}
     */
    public boolean addVisitor(String name, float maxWaitTime) {
        return addVisitor(name, maxWaitTime, false);
    }

    /**
     * Adds a visitor to the park. The visitor is not added if the name is
     * blank, the maximum wait time is not between 0 and 1000 minutes, or the
     * park is full.
     *
     * @param name the visitor's name
     * @param maxWaitTime the longest the visitor will wait, in minutes
     * @param fastPass {@code true} if the visitor has a fast pass
     * @return {@code true} if the visitor was added; otherwise, {@code false}
     */
    public boolean addVisitor(String name, float maxWaitTime, boolean fastPass) {
        if (name == null || name.trim().isEmpty()){
            return false;
        }
        if (!Float.isFinite(maxWaitTime) || maxWaitTime <= 0 || maxWaitTime > 1000){
            return false;
        }
        Visitor meep = new Visitor(name, maxWaitTime, fastPass);
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
