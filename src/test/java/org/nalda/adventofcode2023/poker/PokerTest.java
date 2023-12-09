package org.nalda.adventofcode2023.poker;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

class PokerTest {
    @Disabled("I haven't updated it after the Joker rule")
    @Test
    void acceptance() {
        final Poker poker = new Poker();

        final long totalWinnings = poker.calculateTotalWinnings(ResourceUtil.getLineStream("poker-acceptance.txt"));

        Assertions.assertThat(totalWinnings).isEqualTo(6440L);
    }
}