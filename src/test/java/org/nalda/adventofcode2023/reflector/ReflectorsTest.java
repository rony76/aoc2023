package org.nalda.adventofcode2023.reflector;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectorsTest {
    @Test
    void acceptance() {
        final List<String> input = ResourceUtil.getLineList("reflector-acceptance.txt");
        final Reflector reflector = Reflector.fromStrings(input);

        final long northWeight = reflector.findNorthWeight();

        assertThat(northWeight).isEqualTo(136);
    }

    @Test
    void findWeightForSingleColumnWithOnlyRoundRocks() {
        var r = new Reflector("OO.O.O..##", 1);

        var northWeight = r.findNorthWeight();

        assertThat(northWeight).isEqualTo(34L);
    }

    @Test
    void findWeightForSingleColumnWithOneSquareRocks() {
        var r = new Reflector(".O...#O..O", 1);

        var northWeight = r.findNorthWeight();

        assertThat(northWeight).isEqualTo(17L);
    }
}
