package org.nalda.adventofcode2023.scratchcards;

import org.nalda.adventofcode2023.ResourceUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ScratchCards {
    public static void main(String[] args) throws URISyntaxException, IOException {
        final CardParser cardParser = new CardParser();
        final Supplier<Stream<String>> linesSupplier = ResourceUtil.getLineStreamSupplier("scratchcards-input.txt");

        calculateTotalWorth(cardParser, linesSupplier);
        calculateTotalCards(cardParser, linesSupplier);
    }

    private static void calculateTotalWorth(CardParser cardParser, Supplier<Stream<String>> linesSupplier) throws IOException {
        final long totalWorth = linesSupplier.get()
                .map(cardParser::parse)
                .mapToLong(Card::getWorth)
                .sum();

        System.out.println("Total worth: " + totalWorth);
    }

    private static void calculateTotalCards(CardParser cardParser, Supplier<Stream<String>> linesSupplier) throws IOException {
        final long totalCards = linesSupplier.get()
                .map(cardParser::parse)
                .reduce(CardCopies.empty(), CardCopies::accumulateCard, (a, b) -> b)
                .totalCards();

        System.out.println("Total cards: " + totalCards);
    }
}
