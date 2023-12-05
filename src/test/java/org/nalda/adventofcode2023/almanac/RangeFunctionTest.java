package org.nalda.adventofcode2023.almanac;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RangeFunctionTest {
    @Test
    void acceptance() {
        final List<Range> ranges = List.of(
                new Range(50, 98, 2),
                new Range(52, 50, 48)
        );
        final RangeFunction rangeFunction = new RangeFunction("seed-to-soil", ranges);

        assertThat(rangeFunction.applyAsLong(79)).isEqualTo(81);
        assertThat(rangeFunction.applyAsLong(14)).isEqualTo(14);
        assertThat(rangeFunction.applyAsLong(55)).isEqualTo(57);
        assertThat(rangeFunction.applyAsLong(13)).isEqualTo(13);
    }
}