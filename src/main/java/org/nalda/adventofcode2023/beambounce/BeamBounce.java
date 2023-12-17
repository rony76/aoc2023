package org.nalda.adventofcode2023.beambounce;

import org.nalda.adventofcode2023.ResourceUtil;
import org.nalda.adventofcode2023.pipes.Direction;

import java.util.*;
import java.util.stream.Stream;

import static org.nalda.adventofcode2023.pipes.Direction.*;

public class BeamBounce {
    private final int height;
    private final int width;
    private final char[][] grid;

    public BeamBounce(List<String> input) {
        height = input.size();
        width = input.get(0).length();
        grid = new char[height][width];
        for (int i = 0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
        }
    }

    public long countEnergised() {
        return new BeamWalk().invoke();
    }

    private record Move(int x, int y, Direction direction) {
        private Stream<Move> goNorth() {
            if (y > 0) {
                return Stream.of(new Move(x, y - 1, NORTH));
            } else {
                return Stream.empty();
            }
        }

        private Stream<Move> goEast(int width) {
            if (x < width - 1) {
                return Stream.of(new Move(x + 1, y, EAST));
            } else {
                return Stream.empty();
            }
        }

        private Stream<Move> goSouth(int height) {
            if (y < height - 1) {
                return Stream.of(new Move(x, y + 1, SOUTH));
            } else {
                return Stream.empty();
            }
        }

        private Stream<Move> goWest() {
            if (x > 0) {
                return Stream.of(new Move(x - 1, y, WEST));
            } else {
                return Stream.empty();
            }
        }

        public Stream<Move> nextInDirection(int width, int height) {
            return switch (direction) {
                case NORTH -> goNorth();
                case EAST -> goEast(width);
                case SOUTH -> goSouth(height);
                case WEST -> goWest();
            };
        }

        public Stream<Move> deflectForwardSlash(int width, int height) {
            return switch (direction) {
                case NORTH -> goEast(width);
                case EAST -> goNorth();
                case SOUTH -> goWest();
                case WEST -> goSouth(height);
            };
        }

        public Stream<Move> deflectBackwardSlash(int width, int height) {
            return switch (direction) {
                case NORTH -> goWest();
                case EAST -> goSouth(height);
                case SOUTH -> goEast(width);
                case WEST -> goNorth();
            };
        }

        public Stream<Move> traverseHorizontal(int width, int height) {
            return switch (direction) {
                case NORTH, SOUTH -> Stream.concat(goWest(), goEast(width));
                case EAST, WEST -> nextInDirection(width, height);
            };
        }

        public Stream<Move> traverseVertical(int width, int height) {
            return switch (direction) {
                case NORTH, SOUTH -> nextInDirection(width, height);
                case EAST, WEST -> Stream.concat(goSouth(height), goNorth());
            };
        }

        @Override
        public String toString() {
            return "<%d, %d> %s".formatted(x, y, direction);
        }
    }

    private class BeamWalk {

        private int[][] energyGrid;
        private Queue<Move> moves = new LinkedList<>();
        private Set<Move> visited = new HashSet<>();

        public long invoke() {
            initEnergyGrid();

            moves.offer(new Move(0, 0, EAST));
            while (!moves.isEmpty()) {
                Move move = moves.poll();
                energyGrid[move.y][move.x] = 1;
                visited.add(move);

                Stream<Move> nextMoves = switch (grid[move.y][move.x]) {
                    case '.' -> move.nextInDirection(width, height);
                    case '/' -> move.deflectForwardSlash(width, height);
                    case '\\' -> move.deflectBackwardSlash(width, height);
                    case '-' -> move.traverseHorizontal(width, height);
                    case '|' -> move.traverseVertical(width, height);
                    default -> throw new IllegalStateException("Unexpected value: " + grid[move.y][move.x]);
                };

                nextMoves
                        .filter(m -> !visited.contains(m))
                        .forEach(moves::offer);
            }

            return countEnergised();
        }

        private long countEnergised() {
            long result = 0;
            for (int[] row : energyGrid) {
                for (int cell : row) {
                    result += cell;
                }
            }
            return result;
        }

        private void initEnergyGrid() {
            energyGrid = new int[height][width];
            for (int[] row : energyGrid) {
                Arrays.fill(row, 0);
            }
        }
    }

    public static void main(String[] args) {
        final List<String> input = ResourceUtil.getLineList("beam-bounce-input.txt");
        final BeamBounce beamBounce = new BeamBounce(input);

        long energised = beamBounce.countEnergised();

        System.out.println("Energised: " + energised);
    }
}
