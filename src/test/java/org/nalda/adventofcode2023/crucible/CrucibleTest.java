package org.nalda.adventofcode2023.crucible;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CrucibleTest {
    @Test
    void acceptance() {
        final List<String> input = ResourceUtil.getLineList("crucible-acceptance.txt");
        final Crucible crucible = new Crucible(input);

        long loss = crucible.findMinimumHeatLoss();

        assertThat(loss).isEqualTo(102);
    }
}
