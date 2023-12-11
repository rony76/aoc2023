package org.nalda.adventofcode2023.galaxies;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GalaxiesTest {
    @Test
    void acceptance() {
        final List<String> input = ResourceUtil.getLineList("galaxies-acceptance.txt");

        final Galaxies galaxies = new Galaxies(input);
        final long sumOfShortestPaths = galaxies.findSumOfShortestPaths();

        assertThat(sumOfShortestPaths).isEqualTo(374);
    }
}