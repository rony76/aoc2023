package org.nalda.adventofcode2023.pipes;

import lombok.EqualsAndHashCode;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

public class Pipes {

    private final PipeElement[][] data;
    private final int width;
    private final int height;

    public Pipes(List<String> data) {
        this.height = data.size();
        this.width = data.get(0).length();
        this.data = new PipeElement[height][];
        for (int i = 0; i < height; i++) {
            final String line = data.get(i);
            this.data[i] = new PipeElement[width];
            for (int j = 0; j < width; j++) {
                this.data[i][j] = PipeElement.of(line.charAt(j));
            }
        }
    }

    private enum PipePartType {
        VERTICAL,
        HORIZONTAL,
        CORNER,
        NONE;
    }

    public long findEnclosedArea() {
        PipeElement[][] matrix = initialisePipelineMatrix();
        Position start = findStart();
        Direction direction = findDirectionFromStart(start);
        matrix[start.line][start.index] = findStartPipePart(start);

        Position pos = start;
        while (true) {
            pos = pos.go(direction);
            if (pos.equals(start)) {
                break;
            }
            PipeElement pipe = pipeAt(pos);
            direction = pipe.runAcrossFrom(direction);
            matrix[pos.line][pos.index] = pipe;
            System.out.printf("Moved to %s where I found %s and will go %s next%n", pos, pipe, direction);
        }

        printPipeline(matrix);

        System.out.println();

        return countInnerArea(matrix);
    }

    private long countInnerArea(PipeElement[][] matrix) {
        long innerArea = 0;
        for (int l = 0; l < height; l++) {
            boolean inside = false;
            for (int i = 0; i < width; i++) {
                switch (matrix[l][i]) {
                    case GROUND -> {
                        if (inside) {
                            innerArea++;
                        }
                    }
                    case NORTH_SOUTH, NORTH_EAST, NORTH_WEST -> inside = !inside;
                }

                System.out.print(matrix[l][i].equals(PipeElement.GROUND) ? inside ? "I" : "." : "X");
            }
            System.out.println();
        }
        return innerArea;
    }

    private void printPipeline(PipeElement[][] alignments) {
        for (int l = 0; l < height; l++) {
            for (int i = 0; i < width; i++) {
                switch (alignments[l][i]) {
                    case GROUND -> System.out.print(".");
                    case NORTH_SOUTH -> System.out.print("|");
                    case WEST_EAST -> System.out.print("-");
                    case NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST -> System.out.print("*");
                }
            }
            System.out.println();
        }
    }

    private PipeElement findStartPipePart(Position start) {
        Direction prevDirection = null;

        for (int i = 0; i < Direction.values().length; i++) {
            Direction direction = Direction.values()[i];
            Position next = start.go(direction);
            if (next != null) {
                PipeElement pipeElement = pipeAt(next);
                if (pipeElement.runAcrossFrom(direction) != null) {
                    if (prevDirection == null) {
                        prevDirection = direction;
                    } else {
                        return switch (prevDirection) {
                            case NORTH -> switch (direction) {
                                case EAST -> PipeElement.NORTH_EAST;
                                case SOUTH -> PipeElement.NORTH_SOUTH;
                                case WEST -> PipeElement.NORTH_WEST;
                                default -> throw new IllegalStateException("Unexpected value: " + direction);
                            };
                            case EAST -> switch (direction) {
                                case SOUTH -> PipeElement.SOUTH_EAST;
                                case WEST -> PipeElement.WEST_EAST;
                                default -> throw new IllegalStateException("Unexpected value: " + direction);
                            };
                            case SOUTH -> switch (direction) {
                                case WEST -> PipeElement.SOUTH_WEST;
                                default -> throw new IllegalStateException("Unexpected value: " + direction);
                            };
                            default -> throw new IllegalStateException("Unexpected value: " + direction);
                        };
                    }
                }
            }
        }

        throw new IllegalStateException("Could not find start pipe part");
    }

    private PipeElement[][] initialisePipelineMatrix() {
        PipeElement[][] matrix = new PipeElement[height][width];
        for (int l = 0; l < height; l++) {
            for (int i = 0; i < width; i++) {
                matrix[l][i] = PipeElement.GROUND;
            }
        }
        return matrix;
    }

    @EqualsAndHashCode
    final class Position {
        private final int line;
        private final int index;

        Position(int line, int index) {
            this.line = line;
            this.index = index;
        }

        Position go(Direction d) {
            final Position position = switch (d) {
                case NORTH -> line == 0 ? null : new Position(line - 1, index);
                case SOUTH -> line == height - 1 ? null : new Position(line + 1, index);
                case EAST -> index == width - 1 ? null : new Position(line, index + 1);
                case WEST -> index == 0 ? null : new Position(line, index - 1);
            };

            if (position == null) {
                System.out.printf("Cannot go %s from %s%n", d, this);
            }
            return position;
        }

        @Override
        public String toString() {
            return "<%d, %d>".formatted(line + 1, index + 1);
        }
    }

    public long findFurthestPositionDistance() {
        Position start = findStart();
        Direction direction = findDirectionFromStart(start);
        Position pos = start;
        long steps = 0;
        while (true) {
            pos = pos.go(direction);
            steps++;
            if (pos.equals(start)) {
                return steps / 2;
            }
            PipeElement pipe = pipeAt(pos);
            direction = pipe.runAcrossFrom(direction);
            System.out.printf("Moved to %s where I found %s and will go %s next%n", pos, pipe, direction);
        }
    }

    private Direction findDirectionFromStart(Position start) {
        for (int i = 0; i < Direction.values().length; i++) {
            Direction direction = Direction.values()[i];
            Position next = start.go(direction);
            if (next != null) {
                PipeElement pipeElement = pipeAt(next);
                if (pipeElement.runAcrossFrom(direction) != null) {
                    System.out.println("From start will go " + direction + " to " + next);
                    return direction;
                }
            }
        }

        throw new IllegalStateException("No direction found from start");
    }

    private PipeElement pipeAt(Position next) {
        return data[next.line][next.index];
    }

    private Position findStart() {
        for (int l = 0; l < data.length; l++) {
            for (int i = 0; i < data[l].length; i++) {
                if (data[l][i] == PipeElement.START) {
                    final Position position = new Position(l, i);
                    System.out.println("Start at " + position);
                    return position;
                }
            }
        }
        throw new IllegalStateException("No start found");
    }

    public static void main(String[] args) {
        final List<String> input = ResourceUtil.getLineList("pipes-input.txt");
        final Pipes pipes = new Pipes(input);
        final long findFurthestPositionDistance = pipes.findFurthestPositionDistance();

        System.out.println("Furthest position distance: " + findFurthestPositionDistance);

        final long enclosedArea = pipes.findEnclosedArea();

        System.out.println("Enclosed area: " + enclosedArea);
    }
}