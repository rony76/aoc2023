package org.nalda.adventofcode2023.almanac;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongUnaryOperator;

public class AlmanacParser {
    public Almanac parse(List<String> lines) {
        List<Long> seeds = null;
        List<Range> currentMapRanges = new ArrayList<>();
        String currentMapName = "";
        LongUnaryOperator mappingFunction = LongUnaryOperator.identity();

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                if (!currentMapRanges.isEmpty()) {
                    mappingFunction = mappingFunction.andThen(new RangeFunction(currentMapName, currentMapRanges));
                }
                continue;
            }

            if (line.startsWith("seeds:")) {
                seeds = parseSeeds(line);
                continue;
            }

            if (line.trim().endsWith("map:")) {
                currentMapName = line.split(" ")[0];
                currentMapRanges = new ArrayList<>();
                continue;
            }

            final String[] rangeItems = line.trim().split(" ");
            if (rangeItems.length != 3) {
                throw new RuntimeException("Unexpected line: " + line);
            } else {
                currentMapRanges.add(buildRange(rangeItems));
            }
        }

        if (!currentMapRanges.isEmpty()) {
            mappingFunction = mappingFunction.andThen(new RangeFunction(currentMapName, currentMapRanges));
        }

        return new Almanac(seeds, mappingFunction);
    }

    private Range buildRange(String[] rangeItems) {
        final long destStart = Long.parseLong(rangeItems[0]);
        final long sourceStart = Long.parseLong(rangeItems[1]);
        final long length = Long.parseLong(rangeItems[2]);
        final Range range = new Range(destStart, sourceStart, length);
        return range;
    }

    List<Long> parseSeeds(String seedsLine) {
        return Arrays.stream(seedsLine.split(":")[1].trim().split(" "))
                .map(Long::parseLong)
                .toList();
    }
}
