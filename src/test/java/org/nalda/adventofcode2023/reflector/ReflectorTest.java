package org.nalda.adventofcode2023.reflector;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectorTest {
    @Test
    void acceptance() {
        final List<String> input = ResourceUtil.getLineList("reflector-acceptance.txt");
        final Reflector reflector = new Reflector();

        final long northWeight = reflector.findNorthWeight(input);

        assertThat(northWeight).isEqualTo(136);
    }

    @Test
    void findWeightForSingleColumnWithOnlyRoundRocks() {
        final Reflector reflector = new Reflector();
        final List<String> input = List.of(
                "O",
                "O",
                ".",
                "O",
                ".",
                "O",
                ".",
                ".",
                "#",
                "#"
        );

        final long northWeight = reflector.findNorthWeight(input);

        assertThat(northWeight).isEqualTo(34L);
    }

    @Test
    void findWeightForSingleColumnWithOneSquareRocks() {
        final Reflector reflector = new Reflector();
        final List<String> input = List.of(
                ".",
                "O",
                ".",
                ".",
                ".",
                "#",
                "O",
                ".",
                ".",
                "O"
        );

        final long northWeight = reflector.findNorthWeight(input);

        assertThat(northWeight).isEqualTo(17L);
    }
}
