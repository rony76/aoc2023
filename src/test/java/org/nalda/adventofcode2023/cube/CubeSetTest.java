package org.nalda.adventofcode2023.cube;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.cube.Game.CubeSet;

import static org.assertj.core.api.Assertions.assertThat;

public class CubeSetTest {
    @Test
    void isValidForAllValuesHigher() {
        final CubeSet draw = CubeSet.rgb(10, 10, 10);
        final CubeSet limits = CubeSet.rgb(20, 20, 20);

        assertThat(draw.isValidFor(limits)).isTrue();
    }

    @Test
    void isNotValidForAllValuesLower() {
        final CubeSet draw = CubeSet.rgb(20, 20, 20);
        final CubeSet limits = CubeSet.rgb(10, 10, 10);

        assertThat(draw.isValidFor(limits)).isFalse();
    }

    @Test
    void isNotValidForSingleValueLower() {
        final CubeSet draw = CubeSet.rgb(10, 10, 10);
        final CubeSet limits = CubeSet.rgb(5, 20, 20);

        assertThat(draw.isValidFor(limits)).isFalse();
    }
}
