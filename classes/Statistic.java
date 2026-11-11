package classes;

public class Statistic {
    private final String name;
    private float value;

    public Statistic(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        if (Float.isFinite(value) && value >= 0){
            this.value = value;
        }
    }
}
