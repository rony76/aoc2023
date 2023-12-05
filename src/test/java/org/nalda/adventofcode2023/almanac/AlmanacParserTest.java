package org.nalda.adventofcode2023.almanac;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AlmanacParserTest {

    private final AlmanacParser parser = new AlmanacParser();

    @Test
    @Disabled
    void acceptance() {

        final Almanac almanac = parser.parse(ResourceUtil.getLineList("almanac-acceptance.txt"));

        assertThat(almanac).isNotNull();
    }

    @Test
    void canParseSeeds() {
        final List<Long> seeds = parser.parseSeeds("seeds: 79 14 55 13");

        assertThat(seeds).containsExactly(79L, 14L, 55L, 13L);
    }
}