package org.nalda.adventofcode2023.galaxies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record UniverseMap(boolean[][] map, int height, int width) {
    public static final long EXPANSION_RATE = 2L;


    @Override
    public String toString() {
        var buf = new StringBuilder();
        var firstRow = true;

        for (boolean[] row : map) {
            if (!firstRow) {
                buf.append('\n');
            }
            for (boolean cell : row) {
                buf.append(cell ? '#' : '.');
            }
            firstRow = false;
        }

        return buf.toString();
    }

    public List<Galaxies.GalaxyPosition> findPositions() {
        var emptyRows = findEmptyRows();
        var emptyColumns = findEmptyColumns();

        final ArrayList<Galaxies.GalaxyPosition> positions = new ArrayList<>();
        int row = 0;
        long expandedRow = 0;
        while (row < height) {
            int column = 0;
            long expandedColumn = 0;
            while (column < width) {
                if (map[row][column]) {
                    positions.add(new Galaxies.GalaxyPosition(expandedRow, expandedColumn));
                }
                column++;
                expandedColumn++;
                if (emptyColumns.contains(column)) {
                    expandedColumn += EXPANSION_RATE;
                    column++;
                }
            }
            row++;
            expandedRow++;
            if (emptyRows.contains(row)) {
                expandedRow += EXPANSION_RATE;
                row++;
            }
        }

        return positions;
    }

    private Set<Integer> findEmptyRows() {
        Set<Integer> emptyRows = new HashSet<>();
        for (int row = 0; row < height; row++) {
            if (isRowEmpty(map[row])) {
                emptyRows.add(row);
            }
        }
        return emptyRows;
    }

    private boolean isRowEmpty(boolean[] row) {
        for (boolean rowValue : row) {
            if (rowValue) {
                return false;
            }
        }
        return true;
    }

    public Set<Integer> findEmptyColumns() {
        Set<Integer> emptyColumns = new HashSet<>();
        for (int column = 0; column < height; column++) {
            if (isColumnEmpty(column)) {
                emptyColumns.add(column);
            }
        }
        return emptyColumns;
    }

    private boolean isColumnEmpty(int column) {
        for (int row = 0; row < width; row++) {
            if (map[row][column]) {
                return false;
            }
        }
        return true;
    }

}
