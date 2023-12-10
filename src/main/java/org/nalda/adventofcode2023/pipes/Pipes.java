package org.nalda.adventofcode2023.pipes;

import lombok.EqualsAndHashCode;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

public class Pipes {

    private final PipeType[][] data;
    private final int width;
    private final int height;

    public Pipes(List<String> data) {
        this.height = data.size();
        this.width = data.get(0).length();
        this.data = new PipeType[height][];
        for (int i = 0; i < height; i++) {
            final String line = data.get(i);
            this.data[i] = new PipeType[width];
            for (int j = 0; j < width; j++) {
                this.data[i][j] = PipeType.of(line.charAt(j));
            }
        }
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
            return switch (d) {
                case NORTH -> line == 0 ? null : new Position(line - 1, index);
                case SOUTH -> line == height - 1 ? null : new Position(line + 1, index);
                case EAST -> line == width - 1 ? null : new Position(line, index + 1);
                case WEST -> line == 0 ? null : new Position(line, index - 1);
            };
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
            PipeType pipe = pipeAt(pos);
            direction = pipe.runAcrossFrom(direction);
            System.out.printf("Moved to %s where I found %s and will go %s next%n", pos, pipe, direction);
        }
    }

    private Direction findDirectionFromStart(Position start) {
        for (int i = 0; i < Direction.values().length; i++) {
            Direction direction = Direction.values()[i];
            Position next = start.go(direction);
            if (next != null) {
                PipeType pipeType = pipeAt(next);
                if (pipeType.runAcrossFrom(direction) != null) {
                    System.out.println("From start will go " + direction + " to " + next);
                    return direction;
                }
            }
        }

        throw new IllegalStateException("No direction found from start");
    }

    private PipeType pipeAt(Position next) {
        return data[next.line][next.index];
    }

    private Position findStart() {
        for (int l = 0; l < data.length; l++) {
            for (int i = 0; i < data[l].length; i++) {
                if (data[l][i] == PipeType.START) {
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
    }
}