package org.nalda.adventofcode2023.stepcounter;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.*;
import java.util.stream.Collectors;

public class StepCounter {

    private final int height;
    private final int width;
    private final char[][] map;
    private Pos start;

    public StepCounter(List<String> input) {
        height = input.size();
        width = input.get(0).length();
        map = new char[height][width];
        for (int row = 0; row < input.size(); row++) {
            String line = input.get(row);
            for (int col = 0; col < line.length(); col++) {
                char c = line.charAt(col);
                map[row][col] = c;

                if (c == 'S') {
                    start = new Pos(row, col);
                }
            }
        }
    }

    private record Pos(int row, int col) implements Comparable<Pos>{
        private Pos moveTo(Direction direction) {
            return new Pos(row + direction.deltaRow, col + direction.deltaCol);
        }

        @Override
        public int compareTo(Pos o) {
            int result = row - o.row;
            if (result != 0) {
                return result;
            }

            return col - o.col;
        }
    }

    private enum Direction {
        UP(-1, 0),
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1);

        private final int deltaRow;
        private final int deltaCol;

        Direction(int deltaRow, int deltaCol) {
            this.deltaRow = deltaRow;
            this.deltaCol = deltaCol;
        }
    }

    public long countGardenPlots(int steps) {
        Map<Pos, Integer> visitedPositions = new TreeMap<>();
        List<Pos> positions = List.of(start);
        visitedPositions.put(start, 0);

        for (int step = 1; step <= steps; step++) {
            positions = calculateNextStepPositions(visitedPositions, positions, step);
        }

        Set<Pos> visitedPointSet = visitedPositions.entrySet().stream()
                .filter(e -> e.getValue() % 2 == steps % 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());


        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (map[row][col] == '.' && visitedPointSet.contains(new Pos(row, col))) {
                    System.out.print('O');
                } else {
                    System.out.print(map[row][col]);
                }
            }
            System.out.println();
        }
        return visitedPointSet.size();
    }

    private List<Pos> calculateNextStepPositions(Map<Pos, Integer> visitedPositions, List<Pos> positions, int step) {
        var discardCount = 0;
        var newPositions = new ArrayList<Pos>(positions.size() * 4);
        for (Pos position : positions) {
            for (Direction direction : Direction.values()) {
                Pos newPos = position.moveTo(direction);

                if (!isWithinBoundaries(newPos)) {
                    continue;
                }

                if (at(newPos) == '#') {
                    continue;
                }

                Integer prevVisit = visitedPositions.get(newPos);
                if (prevVisit != null && ((prevVisit % 2) == (step % 2))) {
                    discardCount++;
                    continue;
                }

                newPositions.add(newPos);
                visitedPositions.put(newPos, step);
            }
        }
        System.out.println("Discarded " + discardCount);
        return newPositions;
    }

    private char at(Pos p) {
        return map[p.row][p.col];
    }

    private boolean isWithinBoundaries(Pos pos) {
        return pos.row >= 0 &&
                pos.row < height &&
                pos.col >= 0 &&
                pos.col < width;
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtil.getLineList("step-counter-input.txt");

        long count = new StepCounter(input).countGardenPlots(64);

        System.out.println("Count of garden plots: " + count);

    }
}
