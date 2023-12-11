package org.nalda.adventofcode2023.galaxies;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.*;

public class Galaxies {
    private final UniverseMap map;

    public static void main(String[] args) {
        final List<String> input = ResourceUtil.getLineList("galaxies-input.txt");
        final Galaxies galaxies = new Galaxies(input);
        final long sumOfShortestPaths = galaxies.findSumOfShortestPaths();

        System.out.println("Sum of shortest paths: " + sumOfShortestPaths);
    }

    record GalaxyPosition(long row, long column) {
        public long getDistanceTo(GalaxyPosition target) {
            return Math.abs(row - target.row) + Math.abs(column - target.column);
        }
    }

    public Galaxies(List<String> input) {
        final UniverseScanner universeScanner = new UniverseScanner(input);
        this.map = universeScanner.scan();
    }

    public long findSumOfShortestPaths() {
        var positions = map.findPositions();

        long result = 0L;

        for (int s = 0; s < positions.size(); s++) {
            var source = positions.get(s);
            for (int t = s + 1; t < positions.size(); t++) {
                var target = positions.get(t);

                result += source.getDistanceTo(target);
            }
        }

        return result;
    }

}