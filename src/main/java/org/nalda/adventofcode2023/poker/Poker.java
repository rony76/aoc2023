package org.nalda.adventofcode2023.poker;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.stream.Stream;

public class Poker {
    private final PokerHandParser parser = new PokerHandParser();

    public static void main(String[] args) {
        final Poker poker = new Poker();
        long totalWinnings = poker.calculateTotalWinnings(ResourceUtil.getLineStream("poker-input.txt"));

        System.out.println("Total winnings: " + totalWinnings);
    }

    long calculateTotalWinnings(Stream<String> lineStream) {
        return lineStream
                .map(parser::parse)
                .sorted()
                .collect(new WinningCollector());
    }
}
