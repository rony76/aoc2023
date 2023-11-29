package org.nalda.adventofcode2023.crucible;

import org.nalda.adventofcode2023.pipes.Direction;

import java.util.*;
import java.util.stream.Collectors;

import static org.nalda.adventofcode2023.pipes.Direction.*;

public class Crucible {
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

    private record Point(int row, int col) {
        private Point moveTo(Direction d) {
            return switch (d) {
                case NORTH -> new Point(row - 1, col);
                case WEST -> new Point(row, col - 1);
                case SOUTH -> new Point(row + 1, col);
                case EAST -> new Point(row, col + 1);
            };
        }
    }

    private record Move(Point point, Direction direction, int stepCount) {
    }

    public long findMinimumHeatLoss() {
        return new Dijkstra().invoke();
    }

    private boolean onBorder(Point p, Direction d) {
        return switch (d) {
            case NORTH -> p.row == 0;
            case WEST -> p.col == 0;
            case SOUTH -> p.row == height - 1;
            case EAST -> p.col == width - 1;
        };
    }

    private class Dijkstra {

        private Set<Point> visited;
        private Move[][] parents;
        private long[][] pathLosses;
        private PriorityQueue<Point> toBeVisited;

        public long invoke() {
            visited = new HashSet<>();
            initParents();
            initialiseLosses();
            initialiseToBeVisited();
            Point destination = new Point(height - 1, width - 1);

            while (!toBeVisited.isEmpty()) {
                Point current = toBeVisited.poll();
                Move prevMove = parents[current.row][current.col];
                List<Direction> possibleDirections = findDirections(current, prevMove);
                if (current.equals(destination)) {
                    break;
                }
                for (Direction direction : possibleDirections) {
                    Point nextPoint = current.moveTo(direction);
                    long pathLossViaCurrentPoint = pathLosses[current.row][current.col] + localLosses[nextPoint.row][nextPoint.col];
                    if (pathLossViaCurrentPoint < pathLosses[nextPoint.row][nextPoint.col]) {
                        pathLosses[nextPoint.row][nextPoint.col] = pathLossViaCurrentPoint;
                        storeMove(current, prevMove, direction, nextPoint);
                        toBeVisited.add(nextPoint);
                    }
                }
                visited.add(current);
            }

            return pathLosses[destination.row][destination.col];
        }

        private void storeMove(Point current, Move prevMove, Direction direction, Point nextPoint) {
            var stepCount = prevMove.direction.equals(direction) ? prevMove.stepCount + 1 : 1;
            parents[nextPoint.row][nextPoint.col] = new Move(current, direction, stepCount);
        }

        private void initParents() {
            parents = new Move[height][width];
            for (int i = 0; i < height; i++) {
                Arrays.fill(parents[i], null);
            }
            parents[0][0] = new Move(new Point(0, -1), EAST, 0);
        }

        private void initialiseToBeVisited() {
            toBeVisited = new PriorityQueue<>(Comparator.comparingLong(p -> pathLosses[p.row][p.col]));
            toBeVisited.add(new Point(0, 0));
        }

        private void initialiseLosses() {
            pathLosses = new long[height][width];
            for (int row = 0; row < height; row++) {
                Arrays.fill(pathLosses[row], Long.MAX_VALUE);
            }
            pathLosses[0][0] = 0;
        }

        private List<Direction> findDirections(Point source, Move prevMove) {
            return Arrays.stream(values())
                    .filter(d -> isMoveToDirectionValid(source, prevMove, d))
                    .collect(Collectors.toList());
        }

        private boolean isMoveToDirectionValid(Point source, Move prevMove, Direction dir) {
            // cannot go out of bounds
            if (onBorder(source, dir)) {
                return false;
            }

            // cannot go back to previous point
            if (prevMove.direction.isOpposite(dir)) {
                return false;
            }

            // cannot stretch edge for more than 3 moves
            if (dir.equals(prevMove.direction) && prevMove.stepCount >= 3) {
                return false;
            }

            // was the point in that direction already visited?
            return !visited.contains(source.moveTo(dir));
        }
    }
}
