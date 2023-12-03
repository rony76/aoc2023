package org.nalda.adventofcode2023.cube;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.nalda.adventofcode2023.cube.Game.CubeSet;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class GameTest {
    @Test
    void isValidWithASingleValidDraw() {
        final CubeSet draw = CubeSet.rgb(10, 10, 10);
        final CubeSet drawSpy = spy(draw);
        final Game game = new Game(1, List.of(drawSpy));
        final CubeSet limits = CubeSet.rgb(20, 20, 20);

        assertThat(game.isValidFor(limits)).isTrue();

        verify(drawSpy).isValidFor(limits);
    }

    @Test
    void isValidWithMultipleValidDraws() {
        final List<CubeSet> draws = List.of(
                spy(CubeSet.rgb(10, 10, 10)),
                spy(CubeSet.rgb(12, 12, 12)),
                spy(CubeSet.rgb(14, 14, 14))
        );
        final Game game = new Game(1, draws);
        final CubeSet limits = CubeSet.rgb(20, 20, 20);

        assertThat(game.isValidFor(limits)).isTrue();

        draws.forEach(s -> verify(s).isValidFor(limits));
    }

    @Test
    void isNotValidWithOneInvalidDrawAmongMultipleValidDraws() {
        final List<CubeSet> draws = List.of(
                spy(CubeSet.rgb(10, 10, 10)),
                spy(CubeSet.rgb(12, 12, 12)),
                spy(CubeSet.rgb(14, 24, 14))
        );
        final Game game = new Game(1, draws);
        final CubeSet limits = CubeSet.rgb(20, 20, 20);

        assertThat(game.isValidFor(limits)).isFalse();

        draws.forEach(s -> verify(s).isValidFor(limits));
    }
}