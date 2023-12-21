package org.nalda.adventofcode2023.aplenty;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AplentyTest {
    @Test
    void acceptance() {
        List<String> input = ResourceUtil.getLineList("aplenty-acceptance.txt");
        long sum = new Aplenty().sumAccepted(input);

        assertThat(sum).isEqualTo(19114L);
    }
}
