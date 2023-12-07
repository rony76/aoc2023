package org.nalda.adventofcode2023.poker;

public class PokerHandParser {
    public HandAndBid parse(String s) {
        final String[] tokens = s.split("\\s+");
        final PokerHand hand = PokerHand.of(tokens[0]);
        final long bid = Long.parseLong(tokens[1]);
        return new HandAndBid(hand, bid);
    }
}
