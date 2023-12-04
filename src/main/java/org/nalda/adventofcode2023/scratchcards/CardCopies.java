package org.nalda.adventofcode2023.scratchcards;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public record CardCopies(long totalCards, List<Integer> nextCards) {
    public CardCopies accumulateCard(Card card) {
        var wonCopies = card.getWonCopies();
        var newNextCardsSize = Math.max(this.nextCards.size() - 1, wonCopies);
        var nextCards = new ArrayList<Integer>(newNextCardsSize);

        final int copiesOfCurrentCard = safeCardCountForIndex(0) + 1;
        for (int i = 0; i < newNextCardsSize; i++) {
            var cardCount = safeCardCountForIndex(i + 1);
            if (i < wonCopies) {
                cardCount += copiesOfCurrentCard;
            }

            nextCards.add(cardCount);
        }

        return new CardCopies(totalCards + copiesOfCurrentCard, nextCards);
    }

    private int safeCardCountForIndex(int index) {
        return index < this.nextCards.size() ? this.nextCards.get(index) : 0;
    }

    public static CardCopies empty() {
        return new CardCopies(0, emptyList());
    }
}
