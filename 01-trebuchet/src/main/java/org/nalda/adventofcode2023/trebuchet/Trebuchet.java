package org.nalda.adventofcode2023.trebuchet;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class Trebuchet {
    public static void main(String[] args) throws URISyntaxException, IOException {
        final Path path = getInputPath();
        final long value = Files.lines(path)
                .mapToInt(Trebuchet::extractCalibrationParameter)
                .sum();
        System.out.println(value);
    }

    public static int extractCalibrationParameter(String line) {
        final int firstDigit = getFirstDigit(line, line.chars());
        final int lastDigit = getFirstDigit(line, new StringBuilder(line).reverse().chars());
        return firstDigit * 10 + lastDigit;
    }

    private static int getFirstDigit(String line, IntStream chars) {
        return chars
                .filter(Character::isDigit)
                .map(Trebuchet::charToInt)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Cannot find digit in '%s'", line)));
    }

    private static int charToInt(int d) {
        return d - '0';
    }

    private static Path getInputPath() throws URISyntaxException {
        final URL resource = Trebuchet.class.getClassLoader().getResource("input.txt");
        return new File(resource.toURI()).toPath();
    }
}
