package classes;

public class Main {
    private static final float VISITOR_INTERVAL = 0.0f;
    private static UserMenu menu;

    private Main() {
    }

    public static void main(String[] args) {
        menu = new UserMenu(new Park(), new ParkView());
        menu.run();
    }
}
