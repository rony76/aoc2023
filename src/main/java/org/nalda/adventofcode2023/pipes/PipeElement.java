package org.nalda.adventofcode2023.pipes;

import static org.nalda.adventofcode2023.pipes.Direction.*;

public enum PipeElement {
    GROUND(),
    START(),
    NORTH_SOUTH(NORTH, NORTH, SOUTH, SOUTH),
    WEST_EAST(WEST, WEST, EAST, EAST),
    NORTH_EAST(SOUTH, EAST, WEST, NORTH),
    NORTH_WEST(SOUTH, WEST, EAST, NORTH),
    SOUTH_EAST(NORTH, EAST, WEST, SOUTH),
    SOUTH_WEST(NORTH, WEST, EAST, SOUTH);

    private final Direction from1;
    private final Direction to1;
    private final Direction from2;
    private final Direction to2;

    PipeElement() {
        this(null, null, null, null);
    }

    PipeElement(Direction from1, Direction to1, Direction from2, Direction to2) {
        this.from1 = from1;
        this.to1 = to1;
        this.from2 = from2;
        this.to2 = to2;
    }


    static PipeElement of(char c) {
        return switch (c) {
            case '.' -> GROUND;
            case '|' -> NORTH_SOUTH;
            case '-' -> WEST_EAST;
            case 'S' -> START;
            case 'L' -> NORTH_EAST;
            case 'J' -> NORTH_WEST;
            case 'F' -> SOUTH_EAST;
            case '7' -> SOUTH_WEST;
            default -> throw new IllegalArgumentException("Unknown map point: " + c);
        };
    }

    public Direction runAcrossFrom(Direction direction) {
        if (direction == from1) {
            return to1;
        } else if (direction == from2) {
            return to2;
        }
        return null;
    }
}
