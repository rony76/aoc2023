package org.nalda.adventofcode2023.grids;

public enum Direction {
    UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Point move(Point source) {
        return move(source, 1L);
    }

    public Point move(Point source, long distance) {
        return new Point(source.x() + dx * distance, source.y() + dy * distance);
    }

    public Position move(Position source) {
        return new Position(source.row() + dy, source.column() + dx);
    }

    public boolean isHorizontal() {
        return this == LEFT || this == RIGHT;
    }

    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case RIGHT -> LEFT;
            case DOWN -> UP;
            case LEFT -> RIGHT;
        };
    }
}
