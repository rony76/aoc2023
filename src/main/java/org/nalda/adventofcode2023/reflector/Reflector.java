package org.nalda.adventofcode2023.reflector;

import lombok.Getter;

import java.util.List;

@Getter
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

}
