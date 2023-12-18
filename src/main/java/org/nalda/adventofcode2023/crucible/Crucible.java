package org.nalda.adventofcode2023.crucible;

import org.nalda.adventofcode2023.pipes.Direction;

import java.util.*;

import static org.nalda.adventofcode2023.pipes.Direction.*;

public class Crucible {
    private final int[][] losses;
    private final int height;
    private final int width;

    public Crucible(List<String> input) {
        height = input.size();
        width = input.get(0).length();
        losses = new int[height][width];
        for (int row = 0; row < height; row++) {
            final String line = input.get(row);
            for (int col = 0; col < width; col++) {
                losses[row][col] = line.charAt(col) - '0';
            }
        }
    }

    private record Point(int row, int col) {
        private Point goNorth() {
            return new Point(row - 1, col);
        }

        private Point goWest() {
            return new Point(row, col - 1);
        }

        private Point goSouth() {
            return new Point(row + 1, col);
        }

        private Point goEast() {
            return new Point(row, col + 1);
        }
    }

    private record Move(Point point, Direction direction) {
    }

    public long findMinimumHeatLoss() {
        return new Dijkstra().invoke();
    }

    private boolean onNorthernBorder(Point considerationPoint) {
        return considerationPoint.row == 0;
    }

    private boolean onEasternBorder(Point p) {
        return p.col == width - 1;
    }

    private boolean onSouthernBorder(Point p) {
        return p.row == height - 1;
    }

    private boolean onWesternBorder(Point p) {
        return p.col == 0;
    }

    private boolean prevMoveGoingTo(Move prevMove, Direction moveDirection) {
        return prevMove != null && prevMove.direction == moveDirection;
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
                Point considerationPoint = toBeVisited.poll();
                List<Direction> possibleDirections = findDirections(considerationPoint);
                if (considerationPoint.equals(destination)) {
                    break;
                }
                for (Direction direction : possibleDirections) {
                    Point nextPoint = switch (direction) {
                        case NORTH -> considerationPoint.goNorth();
                        case WEST -> considerationPoint.goWest();
                        case SOUTH -> considerationPoint.goSouth();
                        case EAST -> considerationPoint.goEast();
                    };
                    long pathLossViaCurrentPoint = pathLosses[considerationPoint.row][considerationPoint.col] + losses[nextPoint.row][nextPoint.col];
                    if (pathLossViaCurrentPoint < pathLosses[nextPoint.row][nextPoint.col]) {
                        pathLosses[nextPoint.row][nextPoint.col] = pathLossViaCurrentPoint;
                        parents[nextPoint.row][nextPoint.col] = new Move(considerationPoint, direction);
                        toBeVisited.add(nextPoint);
                    }
                }
                visited.add(considerationPoint);
            }

            return pathLosses[destination.row][destination.col];
        }

        private void initParents() {
            parents = new Move[height][width];
            for (int i = 0; i < height; i++) {
                Arrays.fill(parents[i], null);
            }
        }

        private void initialiseToBeVisited() {
            var leastLossComparator = Comparator.<Point>comparingLong(p -> pathLosses[p.row][p.col]);
            toBeVisited = new PriorityQueue<Point>(leastLossComparator);
            toBeVisited.add(new Point(0, 0));
        }

        private void initialiseLosses() {
            pathLosses = new long[height][width];
            for (int row = 0; row < height; row++) {
                Arrays.fill(pathLosses[row], Long.MAX_VALUE);
            }
            pathLosses[0][0] = 0;
        }

        private List<Direction> findDirections(Point source) {
            Move prevMove = parents[source.row][source.col];
            List<Direction> directions = new ArrayList<>(4);

            Direction directionForPrevious3Moves = findDirectionForPrevious3Moves(source);

            if (!onNorthernBorder(source)) {
                if (!prevMoveGoingTo(prevMove, SOUTH)) {
                    if (!visited.contains(source.goNorth())) {
                        if (!NORTH.equals(directionForPrevious3Moves)) {
                            directions.add(NORTH);
                        } else {
                            System.out.println("We got to " + source + " by moving NORTH 3 times, so we can't go NORTH again");
                        }
                    }
                }
            }
            if (!onWesternBorder(source)) {
                if (!prevMoveGoingTo(prevMove, EAST)) {
                    if (!visited.contains(source.goWest())) {
                        if (!WEST.equals(directionForPrevious3Moves)) {
                            directions.add(WEST);
                        } else {
                            System.out.println("We got to " + source + " by moving WEST 3 times, so we can't go WEST again");
                        }
                    }
                }
            }
            if (!onSouthernBorder(source)) {
                if (!prevMoveGoingTo(prevMove, NORTH)) {
                    if (!visited.contains(source.goSouth())) {
                        if (!SOUTH.equals(directionForPrevious3Moves)) {
                            directions.add(SOUTH);
                        } else {
                            System.out.println("We got to " + source + " by moving SOUTH 3 times, so we can't go SOUTH again");
                        }
                    }
                }
            }
            if (!onEasternBorder(source)) {
                if (!prevMoveGoingTo(prevMove, WEST)) {
                    if (!visited.contains(source.goEast())) {
                        if (!EAST.equals(directionForPrevious3Moves)) {
                            directions.add(EAST);
                        } else {
                            System.out.println("We got to " + source + " by moving EAST 3 times, so we can't go EAST again");
                        }
                    }
                }
            }
            return directions;
        }

        private Direction findDirectionForPrevious3Moves(Point source) {
            Point p = source;
            Move prevMove = parents[p.row][p.col];
            if (prevMove == null) {
                return null;
            }
            final Direction candidateDirection = prevMove.direction;

            int jumps = 0;
            while (jumps < 3 && p != null) {
                prevMove = parents[p.row][p.col];
                if (prevMove == null) {
                    return null;
                }
                if (prevMove.direction != candidateDirection) {
                    return null;
                }
                p = prevMove.point;
                jumps++;
            }

            return candidateDirection;
        }
    }
}
