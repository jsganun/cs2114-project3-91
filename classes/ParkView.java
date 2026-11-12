package classes;

import java.util.Scanner;

public class ParkView {
    private final Scanner input;

    public ParkView() 
    {
        input = new Scanner(System.in);
    }

    public void showOptions() {
        throw new UnsupportedOperationException("TODO");
    }

    public void showAttractionDetails(Attraction attraction) {
        throw new UnsupportedOperationException("TODO");
    }

    public void showVisitors(Visitor[] visitors) {
        throw new UnsupportedOperationException("TODO");
    }

    public void showMessage(String message) {
        throw new UnsupportedOperationException("TODO");
    }

    public boolean promptConfirmation() {
        throw new UnsupportedOperationException("TODO");
    }

    public String promptString(int chars) {
        throw new UnsupportedOperationException("TODO");
    }

    public int promptIntRange(int min, int max) {
        throw new UnsupportedOperationException("TODO");
    }

    public float promptFloatRange(float min, float max) {
        throw new UnsupportedOperationException("TODO");
    }
}
