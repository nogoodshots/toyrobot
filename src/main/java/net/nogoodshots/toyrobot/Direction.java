package net.nogoodshots.toyrobot;

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

    public abstract Direction left();
    public abstract Direction right();
}
