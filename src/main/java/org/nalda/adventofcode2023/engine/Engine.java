package org.nalda.adventofcode2023.engine;

import com.codepoetics.protonpack.StreamUtils;
import org.nalda.adventofcode2023.ResourceUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

public class Engine {
    public static void main(String[] args) throws URISyntaxException, IOException {
        final Path inputPath = ResourceUtil.getInputPath("engine-schema.txt");

        final long partNumberSum = new Engine().calculatePartNumberSum(() -> getSafeLines(inputPath));
        System.out.println("Part number sum: " + partNumberSum);
    }

    private static Stream<String> getSafeLines(Path inputPath) {
        try {
            return Files.lines(inputPath);
        } catch (IOException e) {
            return Stream.empty();
        }
    }

    private long calculatePartNumberSum(Supplier<Stream<String>> linesSupplier) {
        Stream<String> lines = paddedLinesStream(linesSupplier);

        return StreamUtils.windowed(lines, 3)
                .flatMapToLong(this::extractPartNumbers)
                .sum();
    }

    private Stream<String> paddedLinesStream(Supplier<Stream<String>> linesSupplier) {
        final int lineLength = calcLineLength(linesSupplier);
        final String pad = ".".repeat(lineLength);

        Stream<String> lines = concat(
                concat(
                        Stream.of(pad),
                        linesSupplier.get()
                ), Stream.of(pad)).map(this::padLine);
        return lines;
    }

    private LongStream extractPartNumbers(List<String> threeLines) {
        final LinkedList<Long> partNumbers = new LinkedList<>();

        final String haystack = threeLines.get(1);

        StringBuilder digitBuffer = new StringBuilder();
        boolean inDigit = false;
        int firstDigitIndex = -1;
        for (int i = 0; i < haystack.length(); i++) {
            if (Character.isDigit(haystack.charAt(i))) {
                if (!inDigit) {
                    inDigit = true;
                    firstDigitIndex = i;
                    digitBuffer = new StringBuilder();
                }
                digitBuffer.append(haystack.charAt(i));
            } else {
                if (inDigit) {
                    inDigit = false;

                    if (findNeighbouringSymbol(threeLines, firstDigitIndex, i)) {
                        partNumbers.add(Long.parseLong(digitBuffer.toString()));
                    }
                }
            }
        }
        return partNumbers.stream().mapToLong(Long::longValue);
    }

    private boolean findNeighbouringSymbol(List<String> window, int firstDigitIndex, int firstNonDigitIndex) {
        return new Frame(window, firstDigitIndex - 1, firstNonDigitIndex).containsSymbol();
    }

    private int calcLineLength(Supplier<Stream<String>> linesSupplier) {
        return linesSupplier.get().findFirst().orElseThrow().length();
    }

    private String padLine(String line) {
        return "." + line + ".";
    }

    private record Frame(List<String> threeLines, int left, int right) {

        public boolean containsSymbol() {
            if (rightBorderIsSymbol()) return true;
            if (leftBorderIsSymbol()) return true;
            if (lineContainsSymbol(topLine())) return true;
            if (lineContainsSymbol(bottomLine())) return true;
            return false;
        }

        private boolean lineContainsSymbol(String s) {
            for (int j = left; j <= right; j++) {
                char c = s.charAt(j);
                if (isSymbol(c)) {
                    return true;
                }
            }
            return false;
        }

        private boolean leftBorderIsSymbol() {
            return isSymbol(mainLine().charAt(right));
        }

        private boolean rightBorderIsSymbol() {
            return isSymbol(mainLine().charAt(left));
        }

        private String bottomLine() {
            return threeLines.get(2);
        }

        private String topLine() {
            return threeLines.get(0);
        }

        private String mainLine() {
            return threeLines.get(1);
        }

        private boolean isSymbol(char c) {
            return c != '.' && !Character.isDigit(c);
        }
    }
}
