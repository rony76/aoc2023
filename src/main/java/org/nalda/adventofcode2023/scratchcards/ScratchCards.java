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
        final long totalWorth = Files.lines(inputPath)
                .map(cardParser::parse)
                .mapToLong(Card::getWorth)
                .sum();
        System.out.println("Total worth: " + totalWorth);
    }
}
