package org.nalda.adventofcode2023.poker;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

class PokerTest {
    @Test
    void accepance() {
        final Poker poker = new Poker();

        final long totalWinnings = poker.calculateTotalWinnings(ResourceUtil.getLineStream("poker-acceptance.txt"));

        Assertions.assertThat(totalWinnings).isEqualTo(6440L);
    }
}