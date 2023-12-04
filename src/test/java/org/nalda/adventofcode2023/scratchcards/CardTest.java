package org.nalda.adventofcode2023.scratchcards;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CardTest {
    @Test
    void canCalculateWorthOfSample1() {
        final Card card = new Card(1, Set.of(41, 48, 83, 86, 17), Set.of(83, 86, 6, 31, 17, 9, 48, 53));

        assertThat(card.getWorth()).isEqualTo(8L);
    }

    @Test
    void canCalculateWorthOfSample4() {
        final Card card = new Card(4, Set.of(41, 92, 73, 84, 69), Set.of(59, 84, 76, 51, 58, 5, 54, 83));

        assertThat(card.getWorth()).isEqualTo(1L);
    }

    @Test
    void canCalculateWorthOfSample5() {
        final Card card = new Card(5, Set.of(87, 83, 26, 28, 32), Set.of(88, 30, 70, 12, 93, 22, 82, 36));

        assertThat(card.getWorth()).isEqualTo(0L);
    }
}
