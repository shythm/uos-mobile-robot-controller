package teamkernel.sim.common;

public enum Direction {

    UNKNOWN(-1), NORTH(0), EAST(1), SOUTH(2), WEST(3);

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    /**
     * Convert an integer to a direction
     * 
     * @return a direction
     */
    public static Direction fromInteger(int x) {
        switch (x) {
        case 0:
            return NORTH;
        case 1:
            return EAST;
        case 2:
            return SOUTH;
        case 3:
            return WEST;
        default:
            return UNKNOWN;
        }
    }

    /**
     * Return the direction after turning 90 degrees clockwise/counter-clockwise by
     * `count` times
     * 
     * @param count     the number of times to turn
     * @param clockwise true if turning clockwise, false if turning
     *                  counter-clockwise
     * @return a direction
     */
    public Direction turn(int count, boolean clockwise) throws IllegalArgumentException {
        if (this == UNKNOWN) {
            throw new IllegalArgumentException("Cannot turn from UNKNOWN direction");
        }

        int delta = clockwise ? count : -count;

        return Direction.fromInteger((this.value + delta) & 0x03);
    }

    /**
     * Return the direction after turning 90 degrees clockwise
     * 
     * @return a direction
     */
    public Direction clockwise() {
        return turn(1, true);
    }

    /**
     * Return the direction after turning 90 degrees counter-clockwise
     * 
     * @return a direction
     */
    public Direction counterClockwise() {
        return turn(1, false);
    }

}