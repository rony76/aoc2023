package org.nalda.adventofcode2023.galaxies;

import java.util.*;

public class UniverseScanner {

    private final List<String> input;

    public UniverseScanner(List<String> input) {
        this.input = input;
    }

    public UniverseMap scan() {
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
