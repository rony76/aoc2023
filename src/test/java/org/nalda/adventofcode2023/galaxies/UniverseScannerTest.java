package org.nalda.adventofcode2023.galaxies;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;
import org.nalda.adventofcode2023.galaxies.UniverseScanner.UniverseMap;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UniverseScannerTest {
    @Test
    void canScanAcceptance() {
        final List<String> input = ResourceUtil.getLineList("galaxies-acceptance.txt");

        final UniverseMap universeMap = new UniverseScanner(input).buildUniverseMap();

        assertThat(universeMap.toString()).isEqualTo(
                "...#......\n" +
                        ".......#..\n" +
                        "#.........\n" +
                        "..........\n" +
                        "......#...\n" +
                        ".#........\n" +
                        ".........#\n" +
                        "..........\n" +
                        ".......#..\n" +
                        "#...#....." );
    }
}