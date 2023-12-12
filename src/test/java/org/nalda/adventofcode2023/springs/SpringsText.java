package org.nalda.adventofcode2023.springs;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SpringsTest {
    @Test
    void acceptance() {
        final Stream<String> input = ResourceUtil.getLineStream("springs-acceptance.txt");
        final Springs springs = new Springs();

        long arrangementCount = springs.countArrangements(input);

        assertThat(arrangementCount).isEqualTo(21);
    }
}