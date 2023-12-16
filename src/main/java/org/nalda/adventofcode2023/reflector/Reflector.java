package org.nalda.adventofcode2023.reflector;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@EqualsAndHashCode
public class Reflector {
    public static final char ROUND_ROCK = 'O';
    public static final char SQUARE_ROCK = '#';
    public static final char EMPTY = '.';

    private final String data;
    private final int width;
    private final int height;

    public Reflector(String data, int width) {
        this.data = data;
        this.width = width;
        this.height = data.length() / width;
    }

    public static Reflector fromStrings(List<String> input) {
        final StringBuilder buf = new StringBuilder();
        final int width = input.get(0).length();
        for (String line : input) {
            buf.append(line);
        }

        return new Reflector(buf.toString(), width);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Reflector{\n");
        for (int row = 0; row < height; row++) {
            if (row > 0) {
                sb.append('\n');
            }
            sb.append('\t');
            sb.append(data, row * width, (row + 1) * width);
        }
        sb.append('}');
        return sb.toString();
    }

    public long findNorthWeight() {
        long result = 0;
        for (int column = 0; column < width; column++) {
            result += findNorthColumnWeight(column);
        }
        return result;
    }

    public char at(int row, int column) {
        return data.charAt(row * width + column);
    }

    private long findNorthColumnWeight(int column) {
        int nextWeight = height;
        long totalWeight = 0;
        for (int row = 0; row < height; row++) {
            final char current = at(row, column);
            switch (current) {
                case ROUND_ROCK:
                    totalWeight += nextWeight;
                    nextWeight--;
                    break;
                case SQUARE_ROCK:
                    nextWeight = height - row - 1;
                    break;
                case EMPTY:
                    break;
                default:
                    throw new IllegalArgumentException("Unknown character: " + current);
            }
        }
        return totalWeight;
    }

    public Reflector cycle() {
        return tiltNorth()
                .tiltWest()
                .tiltSouth()
                .tiltEast();
    }

    private enum DirectionAccessors implements DirectionAccessor {
        NORTH {
            @Override
            public int laneCount(Reflector r) {
                return r.width;
            }

            @Override
            public int levelDepth(Reflector r) {
                return r.height;
            }

            @Override
            public int getDataIndex(Reflector r, int lane, int level) {
                return level * r.width + lane;
            }
        },

        SOUTH {
            @Override
            public int laneCount(Reflector r) {
                return r.width;
            }

            @Override
            public int levelDepth(Reflector r) {
                return r.height;
            }

            @Override
            public int getDataIndex(Reflector r, int lane, int level) {
                return (r.height - level - 1) * r.width + lane;
            }
        },

        EAST {
            @Override
            public int laneCount(Reflector r) {
                return r.height;
            }

            @Override
            public int levelDepth(Reflector r) {
                return r.width;
            }

            @Override
            public int getDataIndex(Reflector r, int lane, int level) {
                return lane * r.width + (r.width - level - 1);
            }
        },

        WEST {
            @Override
            public int laneCount(Reflector r) {
                return r.height;
            }

            @Override
            public int levelDepth(Reflector r) {
                return r.width;
            }

            @Override
            public int getDataIndex(Reflector r, int lane, int level) {
                return lane * r.width + level;
            }
        };
    }

    public Reflector tiltNorth() {
        return tilt(DirectionAccessors.NORTH);
    }

    public Reflector tiltWest() {
        return tilt(DirectionAccessors.WEST);
    }

    public Reflector tiltSouth() {
        return tilt(DirectionAccessors.SOUTH);
    }

    public Reflector tiltEast() {
        return tilt(DirectionAccessors.EAST);
    }

    private interface DirectionAccessor {
        int laneCount(Reflector r);

        int levelDepth(Reflector r);

        int getDataIndex(Reflector r, int lane, int level);
    }


    Reflector tilt(DirectionAccessor accessor) {
        final char[] newData = new char[data.length()];
        Arrays.fill(newData, EMPTY);

        final int laneCount = accessor.laneCount(this);
        final int levelDepth = accessor.levelDepth(this);

        for (int lane = 0; lane < laneCount; lane++) {
            int targetCursor = 0;
            for (int level = 0; level < levelDepth; level++) {
                char c = data.charAt(accessor.getDataIndex(this, lane, level));
                switch (c) {
                    case ROUND_ROCK -> {
                        newData[accessor.getDataIndex(this, lane, targetCursor)] = ROUND_ROCK;
                        targetCursor++;
                    }
                    case EMPTY -> {

                    }
                    case SQUARE_ROCK -> {
                        newData[accessor.getDataIndex(this, lane, level)] = SQUARE_ROCK;
                        targetCursor = level + 1;
                    }
                }
            }
        }

        return new Reflector(new String(newData), width);
    }


}
