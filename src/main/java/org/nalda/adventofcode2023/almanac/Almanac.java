package org.nalda.adventofcode2023.almanac;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;
import java.util.function.LongUnaryOperator;
import java.util.stream.Stream;

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
        final long lowestLocation = almanac.findLowestLocation();

        System.out.println("Lowest location: " + lowestLocation);
    }

    public long findLowestLocation() {
        return seeds.stream()
                .mapToLong(composedFunction::applyAsLong)
                .min()
                .orElseThrow();
    }
}
