package org.nalda.adventofcode2023.pipes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

    public Stream<Direction> getCrossStream() {
        return switch (this) {
            case NORTH, SOUTH -> Stream.of(EAST, WEST);
            case EAST, WEST -> Stream.of(NORTH, SOUTH);
        };
    }
}
