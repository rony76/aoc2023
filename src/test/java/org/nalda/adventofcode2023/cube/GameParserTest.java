package org.nalda.adventofcode2023.cube;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.nalda.adventofcode2023.cube.Game.CubeSet.rgb;

class GameParserTest {

    private final GameParser gameParser = new GameParser();

    @Test
    void canParseGameWithSingleDrawWithSingleColor() {
        final Game game = gameParser.parse("Game 1: 13 green");

        assertThat(game).isNotNull();
        assertThat(game.getGameNumber()).isEqualTo(1);
        assertThat(game.getDraws()).hasSize(1);
        assertThat(game.getDraws().get(0)).isEqualTo(rgb(0, 13, 0));
    }

    @Test
    void canParseGameWithSingleDrawWithMultipleColors() {
        final Game game = gameParser.parse("Game 1: 13 green, 11 red, 9 blue");

        assertThat(game).isNotNull();
        assertThat(game.getGameNumber()).isEqualTo(1);
        assertThat(game.getDraws().get(0)).isEqualTo(rgb(11, 13, 9));
    }

    @Test
    void canParseGameWithMultipleDrawsWithMultipleColors() {
        final Game game = gameParser.parse("Game 3: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green");

        assertThat(game).isNotNull();
        assertThat(game.getGameNumber()).isEqualTo(3);
        assertThat(game.getDraws()).hasSize(3);
        assertThat(game.getDraws()).containsExactly(
                rgb(4, 0, 3),
                rgb(1, 2, 6),
                rgb(0, 2, 0)
        );
    }
}