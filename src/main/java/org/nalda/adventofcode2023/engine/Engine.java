package org.nalda.adventofcode2023.engine;

import com.codepoetics.protonpack.StreamUtils;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.LinkedList;
import java.util.OptionalLong;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

public class Engine {
    public static void main(String[] args) {
        final Supplier<Stream<String>> linesSupplier = ResourceUtil.getLineStreamSupplier("engine-schema.txt");

        final Engine engine = new Engine();
        final int lineLength = engine.calcLineLength(linesSupplier.get());

        final long partNumberSum = engine.calculatePartNumberSum(linesSupplier.get(), lineLength);
        System.out.println("Part number sum: " + partNumberSum);

        final long gearRatioSum = engine.calculateGearRatioSum(linesSupplier.get(), lineLength);
        System.out.println("Gear ratio sum: " + gearRatioSum);
    }

    private long calculatePartNumberSum(Stream<String> lineStream, int lineLength) {
        return extractFromThreeLineWindowStream(lineStream, lineLength, this::extractPartNumbers);
    }

    private long calculateGearRatioSum(Stream<String> lineStream, int lineLength) {
        return extractFromThreeLineWindowStream(lineStream, lineLength, Engine::extractGearRatios);
    }

    private long extractFromThreeLineWindowStream(Stream<String> lineStream, int lineLength, Function<ThreeLines, LongStream> longExtractor) {
        Stream<String> paddedLineStream = padLinesStream(lineStream, lineLength);

        return StreamUtils.windowed(paddedLineStream, 3)
                .map(ThreeLines::new)
                .flatMapToLong(longExtractor)
                .sum();
    }

    private Stream<String> padLinesStream(Stream<String> lines, int lineLength) {
        final String pad = ".".repeat(lineLength);

        return concat(concat(Stream.of(pad), lines), Stream.of(pad))
                .map(this::padLine);
    }

    private LongStream extractPartNumbers(ThreeLines threeLines) {
        final LinkedList<Long> partNumbers = new LinkedList<>();

        final String haystack = threeLines.centralLine();

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

    private static LongStream extractGearRatios(ThreeLines threeLines) {
        final String haystack = threeLines.centralLine();

        LinkedList<Long> gearRatios = new LinkedList<>();

        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == '*') {
                OptionalLong gearRationOpt = new GearFrame(threeLines, i).findGearRatio();
                gearRationOpt.ifPresent(gearRatios::add);
            }
        }
        return gearRatios.stream().mapToLong(Long::longValue);
    }

    private boolean findNeighbouringSymbol(ThreeLines window, int firstDigitIndex, int firstNonDigitIndex) {
        return new Frame(window, firstDigitIndex - 1, firstNonDigitIndex).containsSymbol();
    }

    private int calcLineLength(Stream<String> lines) {
        return lines.findFirst().orElseThrow().length();
    }

    private String padLine(String line) {
        return "." + line + ".";
    }

    private record Frame(ThreeLines threeLines, int left, int right) {

        public boolean containsSymbol() {
            if (rightBorderIsSymbol()) return true;
            if (leftBorderIsSymbol()) return true;
            if (lineContainsSymbol(threeLines.topLine())) return true;
            if (lineContainsSymbol(threeLines.bottomLine())) return true;
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
            return isSymbol(threeLines.centralLine().charAt(right));
        }

        private boolean rightBorderIsSymbol() {
            return isSymbol(threeLines.centralLine().charAt(left));
        }

        private boolean isSymbol(char c) {
            return c != '.' && !Character.isDigit(c);
        }
    }

}
