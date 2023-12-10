package org.nalda.adventofcode2023.pipes;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import static org.assertj.core.api.Assertions.assertThat;

class PipesTest {
    @Test
    void distanceAcceptance1() {
        var input = ResourceUtil.getLineList("pipes-acceptance-1.txt");
        var furthestPositionDistance = new Pipes(input).findFurthestPositionDistance();

        assertThat(furthestPositionDistance).isEqualTo(4);
    }

    @Test
    void distanceAcceptance2() {
        var input = ResourceUtil.getLineList("pipes-acceptance-2.txt");
        var furthestPositionDistance = new Pipes(input).findFurthestPositionDistance();

        assertThat(furthestPositionDistance).isEqualTo(8);
    }

    @Test
    void enclosedAreaAcceptance3() {
        var input = ResourceUtil.getLineList("pipes-acceptance-3.txt");
        var area = new Pipes(input).findEnclosedArea();

        assertThat(area).isEqualTo(4);
    }

    @Test
    void enclosedAreaAcceptance4() {
        var input = ResourceUtil.getLineList("pipes-acceptance-4.txt");
        var area = new Pipes(input).findEnclosedArea();

        assertThat(area).isEqualTo(8);
    }

    @Test
    void enclosedAreaAcceptance5() {
        var input = ResourceUtil.getLineList("pipes-acceptance-5.txt");
        var area = new Pipes(input).findEnclosedArea();

        assertThat(area).isEqualTo(10);
    }
}