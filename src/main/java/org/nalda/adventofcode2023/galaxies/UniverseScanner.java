package org.nalda.adventofcode2023.galaxies;

import java.util.*;

public class UniverseScanner {
    public record UniverseMap(boolean[][] map, int height, int width) {

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
    }

    private final List<String> input;

    public UniverseScanner(List<String> input) {
        this.input = input;
    }

    public ArrayList<Galaxies.GalaxyPosition> findPositions() {
        UniverseMap universeMap = buildUniverseMap();

        universeMap = new UniverseExpander(universeMap).execute();

        final ArrayList<Galaxies.GalaxyPosition> positions = new ArrayList<>();
        for (int row = 0; row < universeMap.height(); row++) {
            for (int column = 0; column < universeMap.width(); column++) {
                if (universeMap.map()[row][column]) {
                    positions.add(new Galaxies.GalaxyPosition(row, column));
                }
            }
        }
        return positions;
    }

    public UniverseMap buildUniverseMap() {
        final int height = input.size();
        final int width = input.get(0).length();

        boolean[][] universeMap = new boolean[height][width];
        for (int row = 0; row < height; row++) {
            var s = input.get(row);
            for (int column = 0; column < width; column++) {
                universeMap[row][column] = s.charAt(column) == '#';
            }
        }
        return new UniverseMap(universeMap, height, width);
    }

}
