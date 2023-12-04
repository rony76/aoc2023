package org.nalda.adventofcode2023.scratchcards;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CardParserTest {
    @Test
    void canParseSample() {
        final String cardString = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53";
        final CardParser cardParser = new CardParser();

        final Card card = cardParser.parse(cardString);

        assertThat(card).isEqualTo(new Card(1, Set.of(41, 48, 83, 86, 17), Set.of( 83, 86, 6, 31, 17, 9, 48, 53)));
    }
}