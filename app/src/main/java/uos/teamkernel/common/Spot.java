package uos.teamkernel.common;

public enum Spot {
    NONE(0), HAZARD(1), COLOR_BLOB(2);

    private final int value;

    Spot(int value) {
        this.value = value;
    }

    public boolean isEqual(Spot s) {
        return this.value == s.value;
    }
}
