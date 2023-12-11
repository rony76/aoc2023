package org.nalda.adventofcode2023.galaxies;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UniverseScannerTest {
    @Test
    void canScanAcceptance() {
        final List<String> input = ResourceUtil.getLineList("galaxies-acceptance.txt");

        UniverseScanner universeScanner = new UniverseScanner(input);
        final UniverseMap universeMap = universeScanner.scan();

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