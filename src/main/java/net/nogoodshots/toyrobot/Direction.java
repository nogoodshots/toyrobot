package net.nogoodshots.toyrobot;

/**
 * Describes the 2D space domain in the {@link Simulation}. The dimensions are separeted by 90 degrees.
 * {@link Direction#NORTH} and {@link Direction#SOUTH} are opposing directions in one dimension.
 * {@link Direction#NORTH} and {@link Direction#SOUTH} are opposing directions the other dimension.
 *
 */
public enum Direction {
    NORTH("NORTH") {
        public Direction left() {
            return WEST;
        }
        public Direction right() {
            return EAST;
        }
    },
    SOUTH("SOUTH") {
        public Direction left() {
            return EAST;
        }
        public Direction right() {
            return WEST;
        }
    },
    EAST("EAST") {
        public Direction left() {
            return NORTH;
        }
        public Direction right() {
            return SOUTH;
        }
    },
    WEST("WEST") {
        public Direction left() {
            return SOUTH;
        }
        public Direction right() {
            return NORTH;
        }
    };

    private final String name;

    private Direction(final String name) {
        this.name = name;
    }

    /**
     * @return {@link Direction} offset 90 degrees anti-clockwise.
     */
    public abstract Direction left();

    /**
     * @return {@link Direction} offset 90 degrees clockwise.
     */
    public abstract Direction right();
}
