package org.nalda.adventofcode2023.pipes;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public boolean isOpposite(Direction direction) {
        return switch (direction) {
            case NORTH -> this == SOUTH;
            case EAST -> this == WEST;
            case SOUTH -> this == NORTH;
            case WEST -> this == EAST;
        };
    }
}
