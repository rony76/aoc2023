package org.nalda.adventofcode2023.scratchcards;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class CardParser {
    public Card parse(String s) {
        final String[] cardIdAndNumbers = s.split(":");
        final int cardId = Integer.parseInt(cardIdAndNumbers[0].trim().split("\\s+")[1]);
        final String[] winningAndScratchedNumbers = cardIdAndNumbers[1].trim().split("\\|");
        final String[] winningNumbers = winningAndScratchedNumbers[0].trim().split("\\s+");
        final String[] scratchedNumbers = winningAndScratchedNumbers[1].trim().split("\\s+");
        return new Card(cardId, parseToSet(winningNumbers), parseToSet(scratchedNumbers));
    }

    private Set<Integer> parseToSet(String[] winningNumbers) {
        return Arrays.stream(winningNumbers).map(Integer::parseInt).collect(Collectors.toSet());
    }
}
