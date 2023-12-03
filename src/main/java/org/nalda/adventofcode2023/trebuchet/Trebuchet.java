package org.nalda.adventofcode2023.trebuchet;

import org.nalda.adventofcode2023.ResourceUtil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class Trebuchet {
    public static void main(String[] args) throws URISyntaxException, IOException {
        final Path path = ResourceUtil.getInputPath("trebuchet-input.txt");
        final long value = Files.lines(path)
                .mapToInt(new Trebuchet()::extractCalibrationParameter)
                .sum();
        System.out.println(value);
    }

    private static int charToInt(int d) {
        return d - '0';
    }

    public int extractCalibrationParameter(String line) {
        final int firstDigit = getFirstDigit(line);
        final int lastDigit = getLastDigit(line);
        return firstDigit * 10 + lastDigit;
    }

    private static final String[] DIGIT_NAMES = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    int getFirstDigit(String line) {
        final int lineLength = line.length();
        for (int i = 0; i < lineLength; i++) {
            Integer c = findDigitStartingAt(line, i);
            if (c != null) return c;
        }

        throw new RuntimeException(String.format("Cannot find digit in '%s'", line));
    }

    int getLastDigit(String line) {
        final int lineLength = line.length();
        for (int i = lineLength - 1; i >= 0; i--) {
            Integer c = findDigitStartingAt(line, i);
            if (c != null) return c;
        }

        throw new RuntimeException(String.format("Cannot find digit in '%s'", line));
    }

    private Integer findDigitStartingAt(String line, int i) {
        final char c = line.charAt(i);
        if (Character.isDigit(c)) {
            return charToInt(c);
        }
        for (int j = 0; j < DIGIT_NAMES.length; j++) {
            final String digitName = DIGIT_NAMES[j];
            if (line.startsWith(digitName, i)) {
                return j + 1;
            }
        }
        return null;
    }
}
