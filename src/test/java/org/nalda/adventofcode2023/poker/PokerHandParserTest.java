package org.nalda.adventofcode2023.poker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PokerHandParserTest {
    @Test
    void canParse() {
        final PokerHandParser parser = new PokerHandParser();

        final HandAndBid handAndBid = parser.parse("AAAAA 325");

        assertThat(handAndBid).isEqualTo(new HandAndBid(
                PokerHand.of("AAAAA"),
                325L
        ));
    }
}