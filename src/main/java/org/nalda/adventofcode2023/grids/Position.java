package org.nalda.adventofcode2023.grids;

public record Position(int row, int column) {
    @Override
    public String toString() {
        return "(%d, %d)".formatted(row, column);
    }
}
