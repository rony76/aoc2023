package org.nalda.adventofcode2023.mirrors;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MirrorsTest {
    @Test
    void acceptance() {
        final List<String> input = ResourceUtil.getLineList("mirrors-acceptance.txt");

        long sum = new Mirrors().findMirrorsAndSum(input);

        assertThat(sum).isEqualTo(400);
    }
}