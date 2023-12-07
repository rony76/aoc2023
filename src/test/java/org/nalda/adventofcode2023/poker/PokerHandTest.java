package org.nalda.adventofcode2023.poker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class PokerHandTest {
    @ParameterizedTest
    @ValueSource(strings = {"AAAAA", "KKKKK", "88888", "22222", "4444J", "333JJ", "22JJJ", "AJJJJ", "JJJJJ"})
    void pokerIsIdentified(String handString) {
        final PokerHand pokerHand = PokerHand.of(handString);

        assertThat(pokerHand.type).isEqualTo(PokerHand.HandType.POKER);
    }

    @ParameterizedTest
    @ValueSource(strings = {"AAA2A", "8888Q", "233J3", "AJJJ2", "JAJ4J"})
    void fourOfAKindIsIdentified(String handString) {
        final PokerHand pokerHand = PokerHand.of(handString);

        assertThat(pokerHand.type).isEqualTo(PokerHand.HandType.FOUR_OF_A_KIND);
    }

    @ParameterizedTest
    @ValueSource(strings = {"AAA22", "QKQKQ", "8QQ8Q", "AAJKK"})
    void fullHouseIsIdentified(String handString) {
        final PokerHand pokerHand = PokerHand.of(handString);

        assertThat(pokerHand.type).isEqualTo(PokerHand.HandType.FULL_HOUSE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"AAA2K", "7Q737", "8QQ4Q", "AKQJJ", "AJQKQ"})
    void threeOfAKindIsIdentified(String handString) {
        final PokerHand pokerHand = PokerHand.of(handString);

        assertThat(pokerHand.type).isEqualTo(PokerHand.HandType.THREE_OF_A_KIND);
    }

    @ParameterizedTest
    @ValueSource(strings = {"AA22K", "7Q73Q", "8Q44Q"})
    void twoPairIsIdentified(String handString) {
        final PokerHand pokerHand = PokerHand.of(handString);

        assertThat(pokerHand.type).isEqualTo(PokerHand.HandType.TWO_PAIR);
    }

    @ParameterizedTest
    @ValueSource(strings = {"AQ22K", "7Q73K", "8Q443", "AKQJ2", "AJQK2"})
    void onePairIsIdentified(String handString) {
        final PokerHand pokerHand = PokerHand.of(handString);

        assertThat(pokerHand.type).isEqualTo(PokerHand.HandType.ONE_PAIR);
    }

    @ParameterizedTest
    @ValueSource(strings = {"AQ23K", "7Q43K", "8Q453"})
    void highCardIsIdentified(String handString) {
        final PokerHand pokerHand = PokerHand.of(handString);

        assertThat(pokerHand.type).isEqualTo(PokerHand.HandType.HIGH_CARD);
    }

    @Test
    void fullHouseIsLessThanPoker() {
        final PokerHand poker = PokerHand.of("AAAAA");
        final PokerHand fullHouse = PokerHand.of("888QQ");

        assertThat(fullHouse).isLessThan(poker);
    }

    @Test
    void onePairIsLessThanTwoPair() {
        final PokerHand onePair = PokerHand.of("AA234");
        final PokerHand twoPair = PokerHand.of("AA223");

        assertThat(onePair).isLessThan(twoPair);
    }

    @Test
    void pokerOfSixesIsLessThanPokerOfQueens() {
        final PokerHand pokerOf6 = PokerHand.of("66666");
        final PokerHand pokerOfQueens = PokerHand.of("QQQQQ");

        assertThat(pokerOf6).isLessThan(pokerOfQueens);
    }
}
