package classes;

public class Statistic {
    private static String name;
    private float value;

    public Statistic(String name, float value) {
        Statistic.name = name;
        this.value = value;
    }

    public String getName() {
        throw new UnsupportedOperationException("TODO");
    }

    public float getValue() {
        throw new UnsupportedOperationException("TODO");
    }

    public void setValue(float newValue) {
        throw new UnsupportedOperationException("TODO");
    }
}
