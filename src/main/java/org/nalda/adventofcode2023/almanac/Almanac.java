package org.nalda.adventofcode2023.almanac;

import com.codepoetics.protonpack.StreamUtils;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

public class Almanac {
    private final AlmanacParser almanacParser = new AlmanacParser();
    private final List<Long> seeds;
    private final LongUnaryOperator composedFunction;

    public Almanac(List<Long> seeds, LongUnaryOperator composedFunction) {
        this.seeds = seeds;
        this.composedFunction = composedFunction;
    }

    public static void main(String[] args) {
        final Almanac almanac = new AlmanacParser().parse(ResourceUtil.getLineList("almanac-input.txt"));
        final long lowestLocationFromSeeds = almanac.findLowestLocationFromSeeds();

        System.out.println("Lowest location from seeds: " + lowestLocationFromSeeds);

        final long lowestLocationFromSeedRanges = almanac.findLowestLocationFromSeedRanges();

        System.out.println("Lowest location from seed ranges: " + lowestLocationFromSeedRanges);
    }

    public long findLowestLocationFromSeeds() {
        return seeds.stream()
                .mapToLong(composedFunction::applyAsLong)
                .min()
                .orElseThrow();
    }

    public long findLowestLocationFromSeedRanges() {
        LongStream seedStream = generateSeedStream();

        return seedStream
                .parallel()
                .map(composedFunction::applyAsLong)
                .min()
                .orElseThrow();
    }

    private LongStream generateSeedStream() {
        LongStream seedStream = LongStream.empty();

        for (int i = 0; i < seeds.size(); i+=2) {
            final Long rangeStart = seeds.get(i);
            final Long rangeLength = seeds.get(i + 1);
            seedStream = LongStream.concat(seedStream, getSeedRangeStream(rangeLength, rangeStart));
        }
        return seedStream;
    }

    private LongStream genSeedStreamFromPair(List<Long> pair) {
        final Long rangeLength = pair.get(1);
        final Long rangeStart = pair.get(0);
        return getSeedRangeStream(rangeLength, rangeStart);
    }

    private LongStream getSeedRangeStream(Long rangeLength, Long rangeStart) {
        return LongStream.range(rangeStart, rangeStart + rangeLength);
    }
}
