package enums;

public enum BuildDirection {
    Forward("Next Hall"),
    Backward("Previous Hall");

    public final String label;

    BuildDirection(String label) {
        this.label = label;
    }
}
