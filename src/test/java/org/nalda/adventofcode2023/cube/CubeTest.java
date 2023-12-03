package org.nalda.adventofcode2023.cube;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CubeTest {

    @Test
    void canSumGames() {
        final String gameString = "Game 1: 13 green, 3 red";
        final Game.CubeSet limits = Game.CubeSet.rgb(20, 20, 20);
        GameParser gameParser = Mockito.mock(GameParser.class);
        final Game game = Mockito.mock(Game.class);
        when(game.isValidFor(limits)).thenReturn(true);
        when(game.getGameNumber()).thenReturn(1);
        when(gameParser.parse(gameString)).thenReturn(game);
        final Stream<String> lines = Stream.of(gameString);

        Cube cube = new Cube(gameParser);

        long sumOfValidGames = cube.processGames(lines, limits);

        assertThat(sumOfValidGames).isEqualTo(1);
        verify(gameParser).parse(gameString);
    }

    @Test
    void canSumPowers() {
        final String gameString = "Game 1: 13 green, 3 red, 2 blue";
        GameParser gameParser = Mockito.mock(GameParser.class);
        final Game game = Mockito.mock(Game.class);
        when(game.getGamePower()).thenReturn(78);
        when(gameParser.parse(gameString)).thenReturn(game);
        final Stream<String> lines = Stream.of(gameString);

        Cube cube = new Cube(gameParser);

        long powerSum = cube.sumPowers(lines);
        assertThat(powerSum).isEqualTo(78L);
    }
}
