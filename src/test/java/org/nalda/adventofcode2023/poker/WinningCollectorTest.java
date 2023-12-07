package org.nalda.adventofcode2023.poker;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class WinningCollectorTest {
    @Test
    void accumulatesWinnings() {
        final Stream<HandAndBid> handAndBidStream = Stream.of(
                new HandAndBid(PokerHand.of("QQQQQ"), 10L),
                new HandAndBid(PokerHand.of("KKKKK"), 20L),
                new HandAndBid(PokerHand.of("AAAAA"), 15L)
        );
        final Long totalWinnings = handAndBidStream
                .collect(new WinningCollector());

        assertThat(totalWinnings).isEqualTo(10L + 40L + 45L);
    }
}