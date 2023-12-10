package org.nalda.adventofcode2023.pipes;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import static org.assertj.core.api.Assertions.assertThat;

class PipesTest {
    @Test
    void acceptance1() {
        var input = ResourceUtil.getLineList("pipes-acceptance-1.txt");
        var furthestPositionDistance = new Pipes(input).findFurthestPositionDistance();

        assertThat(furthestPositionDistance).isEqualTo(4);
    }

    @Test
    void acceptance2() {
        var input = ResourceUtil.getLineList("pipes-acceptance-2.txt");
        var furthestPositionDistance = new Pipes(input).findFurthestPositionDistance();

        assertThat(furthestPositionDistance).isEqualTo(8);
    }
}