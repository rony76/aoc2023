package org.nalda.adventofcode2023.scratchcards;

import org.nalda.adventofcode2023.ResourceUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScratchCards {
    public static void main(String[] args) throws URISyntaxException, IOException {
        final CardParser cardParser = new CardParser();
        final Path inputPath = ResourceUtil.getInputPath("scratchcards-input.txt");

        calculateTotalWorth(cardParser, inputPath);
        calculateTotalCards(cardParser, inputPath);
    }

    private static void calculateTotalWorth(CardParser cardParser, Path inputPath) throws IOException {
        final long totalWorth = Files.lines(inputPath)
                .map(cardParser::parse)
                .mapToLong(Card::getWorth)
                .sum();

        System.out.println("Total worth: " + totalWorth);
    }

    private static void calculateTotalCards(CardParser cardParser, Path inputPath) throws IOException {
        final long totalCards = Files.lines(inputPath)
                .map(cardParser::parse)
                .reduce(CardCopies.empty(), CardCopies::accumulateCard, (a, b) -> b)
                .totalCards();

        System.out.println("Total cards: " + totalCards);
    }
}
