package org.nalda.adventofcode2023.oasis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.stream.Stream;

class OasisTest {
    @Test
    void acceptanceFirstStar() {
        final Oasis oasis = new Oasis();
        final Stream<String> sequenceStream = ResourceUtil.getLineStream("oasis-acceptance.txt");

        final long sum = oasis.forwardSequencesAndSum(sequenceStream);

        Assertions.assertThat(sum).isEqualTo(114L);
    }

    @Test
    void acceptanceSecondStar() {
        final Oasis oasis = new Oasis();
        final Stream<String> sequenceStream = ResourceUtil.getLineStream("oasis-acceptance.txt");

        final long sum = oasis.backwardSequencesAndSum(sequenceStream);

        Assertions.assertThat(sum).isEqualTo(2L);
    }
}