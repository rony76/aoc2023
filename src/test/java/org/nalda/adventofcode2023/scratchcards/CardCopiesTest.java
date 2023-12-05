package org.nalda.adventofcode2023.scratchcards;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CardCopiesTest {

    private CardParser cardParser = new CardParser();

    @Test
    void firstAccumulation() {
        final CardCopies empty = CardCopies.empty();
        final Card firstCard = cardParser.parse("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53");

        final CardCopies firstAccumulation = empty.accumulateCard(firstCard);

        assertThat(firstAccumulation.totalCards()).isEqualTo(1);
        assertThat(firstAccumulation.nextCards()).isEqualTo(List.of(
                1, // card 2: 1 copy won with card 1
                1, // card 3: 1 copy won with card 1
                1, // card 4: 1 copy won with card 1
                1  // card 5: 1 copy won with card 1
        ));
    }

    @Test
    void secondAccumulation() {
        final Card firstCard = cardParser.parse("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53");
        final Card secondCard = cardParser.parse("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19");
        assertThat(firstCard.getWonCopies()).isEqualTo(4);
        assertThat(secondCard.getWonCopies()).isEqualTo(2);

        final CardCopies secondAccumulation = CardCopies.empty()
                .accumulateCard(firstCard)
                .accumulateCard(secondCard);


        assertThat(secondAccumulation.totalCards()).isEqualTo(3);
        assertThat(secondAccumulation.nextCards()).isEqualTo(List.of(
                3, // card 3: 1 copy I had won with card 1 + 2 copies I won with card 2
                3, // card 4: 1 copy I had won with card 1 + 2 copies I won with card 2
                1  // card 5: 1 copy I had won with card 1
        ));
    }

    @Test
    void thirdAccumulation() {
        final Card firstCard = cardParser.parse("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53");
        final Card secondCard = cardParser.parse("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19");
        final Card thirdCard = cardParser.parse("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1");
        assertThat(firstCard.getWonCopies()).isEqualTo(4);
        assertThat(secondCard.getWonCopies()).isEqualTo(2);
        assertThat(thirdCard.getWonCopies()).isEqualTo(2);

        final CardCopies thirdAccumulation = CardCopies.empty()
                .accumulateCard(firstCard)
                .accumulateCard(secondCard)
                .accumulateCard(thirdCard);


        assertThat(thirdAccumulation.totalCards()).isEqualTo(7);
        assertThat(thirdAccumulation.nextCards()).isEqualTo(List.of(
                7, // card 4: 1 copy I had won with card 1 + 2 copies I won with card 2 + 4 copies I won with card 3
                5  // card 5: 1 copy I had won with card 1 + 4 copies I won with card 3
        ));
    }

    @Test
    void acceptance() {
        final BinaryOperator<CardCopies> dummyCombiner = (a, b) -> b;
        final Stream<String> lines = ResourceUtil.getInputLines("scratchcards-acceptance.txt");

        final long totalCards = lines
                .map(cardParser::parse)
                .reduce(CardCopies.empty(), CardCopies::accumulateCard, dummyCombiner)
                .totalCards();

        assertThat(totalCards).isEqualTo(30L);

    }
}