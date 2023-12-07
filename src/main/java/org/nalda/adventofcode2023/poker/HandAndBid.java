package org.nalda.adventofcode2023.poker;

public record HandAndBid(PokerHand hand, long bid) implements Comparable<HandAndBid> {
    @Override
    public int compareTo(HandAndBid o) {
        return hand.compareTo(o.hand);
    }
}
