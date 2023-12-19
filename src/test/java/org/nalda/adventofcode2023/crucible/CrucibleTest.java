package org.nalda.adventofcode2023.crucible;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CrucibleTest {
    @Test
    void acceptance1() {
        final List<String> input = ResourceUtil.getLineList("crucible-acceptance.txt");
        final Crucible crucible = new Crucible(input);

        long loss = crucible.findMinimumHeatLoss(1, 3);

        assertThat(loss).isEqualTo(102);
    }

    @Test
    void acceptance2() {
        final List<String> input = ResourceUtil.getLineList("crucible-acceptance.txt");
        final Crucible crucible = new Crucible(input);

        long loss = crucible.findMinimumHeatLoss(4, 10);

        assertThat(loss).isEqualTo(94);
    }
}
