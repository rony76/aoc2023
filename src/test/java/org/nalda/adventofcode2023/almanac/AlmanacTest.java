package org.nalda.adventofcode2023.almanac;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import static org.assertj.core.api.Assertions.assertThat;

class AlmanacTest {
    @Test
    void acceptance() {
        final Almanac almanac = new AlmanacParser().parse(ResourceUtil.getLineList("almanac-acceptance.txt"));

        final long lowestLocation = almanac.findLowestLocation();

        assertThat(lowestLocation).isEqualTo(35L);
    }
}