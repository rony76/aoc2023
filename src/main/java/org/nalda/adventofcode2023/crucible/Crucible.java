package org.nalda.adventofcode2023.crucible;

import org.nalda.adventofcode2023.ResourceUtil;
import org.nalda.adventofcode2023.Timing;
import org.nalda.adventofcode2023.pipes.Direction;

import java.util.*;
import java.util.stream.Stream;

public class Crucible {
    public static final Node START_NODE = new Node(0, 0, Direction.EAST, 0);
    private final int[][] localLosses;
    private final int height;
    private final int width;

    public Crucible(List<String> input) {
        height = input.size();
        width = input.get(0).length();
        localLosses = new int[height][width];
        for (int row = 0; row < height; row++) {
            final String line = input.get(row);
            for (int col = 0; col < width; col++) {
                localLosses[row][col] = line.charAt(col) - '0';
            }
        }
    }

    private record Node(int row, int col, Direction dir, int steps) {
        public Node moveTo(Direction dir) {
            var newSteps = this.dir == dir ? steps + 1 : 1;
            return switch (dir) {
                case NORTH -> new Node(row - 1, col, dir, newSteps);
                case EAST -> new Node(row, col + 1, dir, newSteps);
                case SOUTH -> new Node(row + 1, col, dir, newSteps);
                case WEST -> new Node(row, col - 1, dir, newSteps);
            };
        }

        public boolean isWithinBounds(int height, int width) {
            return row >= 0 && row < height && col >= 0 && col < width;
        }
    }

    public long findMinimumHeatLoss(int minStep, int maxStep) {
        return new Dijkstra(minStep, maxStep).visit();
    }

    private boolean onBorder(Node p, Direction d) {
        return switch (d) {
            case NORTH -> p.row == 0;
            case WEST -> p.col == 0;
            case SOUTH -> p.row == height - 1;
            case EAST -> p.col == width - 1;
        };
    }

    private class Dijkstra {
        private final int minStep;
        private final int maxStep;
        private Map<Node, Long> pathLosses;
        private PriorityQueue<Node> toBeVisited;

        public Dijkstra(int minStep, int maxStep) {
            this.minStep = minStep;
            this.maxStep = maxStep;
        }

        public long visit() {
            initialiseLosses();
            initialiseToBeVisited();

            while (!toBeVisited.isEmpty()) {
                Node current = toBeVisited.poll();
                if (current.row == width - 1 && current.col == height - 1) {
                    break;
                }

                findAdjacent(current).forEach(nextNode -> {
                    long pathLossViaCurrentPoint = pathLosses.get(current) + localLosses[nextNode.row][nextNode.col];
                    if (pathLossViaCurrentPoint < pathLosses.getOrDefault(nextNode, Long.MAX_VALUE)) {
                        pathLosses.put(nextNode, pathLossViaCurrentPoint);
                        toBeVisited.add(nextNode);
                    }
                });
            }

            return pathLosses.entrySet().stream()
                    .filter(e -> e.getKey().row == height - 1 && e.getKey().col == width - 1)
                    .filter(e -> e.getKey().steps >= minStep)
                    .mapToLong(Map.Entry::getValue)
                    .min()
                    .orElseThrow();
        }

        private void initialiseToBeVisited() {
            toBeVisited = new PriorityQueue<>(Comparator.comparingLong(p -> pathLosses.get(p)));
            toBeVisited.add(START_NODE);
        }

        private void initialiseLosses() {
            pathLosses = new HashMap<>();
            pathLosses.put(START_NODE, 0L);
        }

        private Stream<Node> findAdjacent(Node source) {
            Stream<Direction> dirStream = Stream.empty();
            if (source.steps >= minStep) {
                dirStream = source.dir.getCrossStream();
            }

            if (source.steps < maxStep) {
                dirStream = Stream.concat(dirStream, Stream.of(source.dir));
            }

            return dirStream
                    .map(source::moveTo)
                    .filter(node -> node.isWithinBounds(height, width));
        }
    }

    public static void main(String[] args) {
        final List<String> input = ResourceUtil.getLineList("crucible-input.txt");
        final Crucible crucible = new Crucible(input);

        Timing.runAndTrack(() -> {
            long loss = crucible.findMinimumHeatLoss(1, 3);

            System.out.println("Minimal loss for crucible: " + loss);

            loss = crucible.findMinimumHeatLoss(4, 10);

            System.out.println("Minimal loss for ultra crucible: " + loss);
        });
    }
}
