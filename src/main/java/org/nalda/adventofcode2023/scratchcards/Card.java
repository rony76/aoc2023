package org.nalda.adventofcode2023.scratchcards;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@EqualsAndHashCode
public class Card {
    private final int cardId;
    private final Set<Integer> winningNumbers;
    private final Set<Integer> scratchedNumbers;

    @Override
    public String toString() {
        return "Card %d: %s | %s".formatted(cardId, winningNumbers, scratchedNumbers);
    }

    public long getWorth() {
        var intersection = new HashSet(winningNumbers);
        intersection.retainAll(scratchedNumbers);

        final int matchingNumbersCount = intersection.size();
        if (matchingNumbersCount == 0) {
            return 0;
        }
        return (long) Math.pow(2, matchingNumbersCount - 1);
    }
}
