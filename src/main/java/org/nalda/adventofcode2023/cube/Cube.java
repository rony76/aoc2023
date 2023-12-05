package org.nalda.adventofcode2023.cube;

import lombok.AllArgsConstructor;
import org.nalda.adventofcode2023.ResourceUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.nalda.adventofcode2023.ResourceUtil.getInputLines;

@AllArgsConstructor
public class Cube {
    private final GameParser gameParser;

    public static void main(String[] args) {
        final Game.CubeSet limits = Game.CubeSet.rgb(12, 13, 14);
        final Cube cube = new Cube(new GameParser());

        final long sumOfValidGames = cube.processGames(getInputLines("cube-input.txt"), limits);
        System.out.println("Sum of valid games: " + sumOfValidGames);

        final long powerSum = cube.sumPowers(getInputLines("cube-input.txt"));
        System.out.println("Sum of powers: " + powerSum);
    }

    public long processGames(Stream<String> lines, Game.CubeSet limits) {
        return lines
                .map(gameParser::parse)
                .filter(game -> game.isValidFor(limits))
                .mapToInt(Game::getGameNumber)
                .sum();
    }


    public long sumPowers(Stream<String> lines) {
        return lines
                .map(gameParser::parse)
                .mapToInt(Game::getGamePower)
                .sum();
    }
}
