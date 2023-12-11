package org.nalda.adventofcode2023.galaxies;

import org.nalda.adventofcode2023.galaxies.UniverseScanner.UniverseMap;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class UniverseExpander {
    private final UniverseMap universeMap;

    public UniverseExpander(UniverseMap universeMap) {
        this.universeMap = universeMap;
    }

    public UniverseMap execute() {
        Set<Integer> emptyRows = findEmptyRows();
        Set<Integer> emptyColumns = findEmptyColumns();

        var sourceHeight = universeMap.height();
        var targetHeight = sourceHeight + emptyRows.size();
        var sourceWidth = universeMap.width();
        var targetWidth = sourceWidth + emptyColumns.size();

        final boolean[][] target = new boolean[targetHeight][targetWidth];
        int sourceRow = 0, targetRow = 0;
        while (sourceRow < sourceHeight) {
            if (emptyRows.contains(sourceRow)) {
                Arrays.fill(target[targetRow], false);
                targetRow++;
                Arrays.fill(target[targetRow], false);
            } else {
                int sourceColumn = 0, targetColumn = 0;
                while (sourceColumn < sourceWidth) {
                    if (emptyColumns.contains(sourceColumn)) {
                        target[targetRow][targetColumn] = false;
                        targetColumn++;
                        target[targetRow][targetColumn] = false;
                    } else {
                        target[targetRow][targetColumn] = universeMap.map()[sourceRow][sourceColumn];
                    }
                    sourceColumn++;
                    targetColumn++;
                }
            }
            sourceRow++;
            targetRow++;
        }
        return new UniverseMap(target, targetHeight, targetWidth);
    }

    private Set<Integer> findEmptyRows() {
        Set<Integer> emptyRows = new HashSet<>();
        for (int row = 0; row < universeMap.height(); row++) {
            boolean isEmpty = true;
            for (boolean rowValue : universeMap.map()[row]) {
                if (rowValue) {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {
                emptyRows.add(row);
            }
        }
        return emptyRows;
    }

    private Set<Integer> findEmptyColumns() {
        Set<Integer> emptyColumns = new HashSet<>();
        for (int column = 0; column < universeMap.height(); column++) {
            boolean isEmpty = true;
            for (int row = 0; row < universeMap.width(); row++) {
                if (universeMap.map()[row][column]) {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {
                emptyColumns.add(column);
            }
        }
        return emptyColumns;
    }
}
