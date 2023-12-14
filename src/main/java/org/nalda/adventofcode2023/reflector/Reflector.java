package org.nalda.adventofcode2023.reflector;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

public class Reflector {
    private static final char ROUND_ROCK = 'O';
    private static final char SQUARE_ROCK = '#';
    private static final char EMPTY = '.';

    public long findNorthWeight(List<String> input) {
        final int width = input.get(0).length();
        long result = 0;
        for (int column = 0; column < width; column++) {
            result += findNorthColumnWeight(input, column);
        }
        return result;
    }

    private long findNorthColumnWeight(List<String> input, int column) {
        final int height = input.size();

        int nextWeight = height;
        long totalWeight = 0;
        for (int row = 0; row < height; row++) {
            final char current = input.get(row).charAt(column);
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

    public static void main(String[] args) {
        final List<String> input = ResourceUtil.getLineList("reflector-input.txt");
        final Reflector reflector = new Reflector();

        final long northWeight = reflector.findNorthWeight(input);

        System.out.println("North weight: " + northWeight);

    }
}
