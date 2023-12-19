package org.nalda.adventofcode2023.scratchcards;

import java.util.HashSet;
import java.util.Set;

public record Card(int cardId, Set<Integer> winningNumbers,
                   Set<Integer> scratchedNumbers) {
    @Override
    public String toString() {
        return "Card %dir: %s | %s".formatted(cardId, winningNumbers, scratchedNumbers);
    }

    public long getWorth() {
        final int matchingNumbersCount = getWonCopies();
        if (matchingNumbersCount == 0) {
            return 0;
        }
        return (long) Math.pow(2, matchingNumbersCount - 1);
    }

    public int getWonCopies() {
        var intersection = new HashSet<>(winningNumbers);
        intersection.retainAll(scratchedNumbers);

        return intersection.size();
    }
}
