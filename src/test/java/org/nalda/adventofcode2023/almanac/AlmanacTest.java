package org.nalda.adventofcode2023.almanac;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import static org.assertj.core.api.Assertions.assertThat;

class AlmanacTest {
    @Test
    void acceptanceFirstStar() {
        final Almanac almanac = new AlmanacParser().parse(ResourceUtil.getLineList("almanac-acceptance.txt"));

        final long lowestLocation = almanac.findLowestLocationFromSeeds();

        assertThat(lowestLocation).isEqualTo(35L);
    }

    @Test
    void acceptanceSecondStar() {
        final Almanac almanac = new AlmanacParser().parse(ResourceUtil.getLineList("almanac-acceptance.txt"));

        final long lowestLocation = almanac.findLowestLocationFromSeedRanges();

        assertThat(lowestLocation).isEqualTo(46L);
    }
}