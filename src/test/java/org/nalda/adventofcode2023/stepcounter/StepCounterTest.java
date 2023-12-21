package org.nalda.adventofcode2023.stepcounter;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StepCounterTest {
    @Test
    void acceptance() {
        List<String> input = ResourceUtil.getLineList("step-counter-acceptance.txt");

        long count = new StepCounter(input).countGardenPlots(6);

        assertThat(count).isEqualTo(16);
    }
}
