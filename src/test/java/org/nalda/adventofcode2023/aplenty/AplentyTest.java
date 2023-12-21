package org.nalda.adventofcode2023.aplenty;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AplentyTest {
    @Test
    void acceptance1() {
        List<String> input = ResourceUtil.getLineList("aplenty-acceptance.txt");
        long sum = new Aplenty().sumAccepted(input);

        assertThat(sum).isEqualTo(19114L);
    }

    @Test
    void acceptance2() {
        List<String> input = ResourceUtil.getLineList("aplenty-acceptance.txt");
        long count = new Aplenty().countAcceptable(input);

        assertThat(count).isEqualTo(167409079868000L);
    }
}
