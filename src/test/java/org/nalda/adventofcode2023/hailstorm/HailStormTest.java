package org.nalda.adventofcode2023.hailstorm;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.nalda.adventofcode2023.hailstorm.HailStorm.HailVector;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class HailStormTest {
    @ParameterizedTest
    @MethodSource("collisionTestSamples")
    void testCollision1(HailVector hv1, HailVector hv2, boolean expectedCrossing) {
        var hs = new HailStorm(7, 27);

        assertThat(hs.willCrossPath(hv1, hv2)).isEqualTo(expectedCrossing);
    }

    public static Stream<Arguments> collisionTestSamples() {
        return Stream.of(
                Arguments.of(
                        new HailVector(19, 13, -2, 1),
                        new HailVector(18, 19, -1, -1),
                        true),
                Arguments.of(
                        new HailVector(19, 13, -2, 1),
                        new HailVector(20, 25, -2, -2),
                        true),
                Arguments.of(
                        new HailVector(19, 13, -2, 1),
                        new HailVector(12, 31, -1, -2),
                        false),
                Arguments.of(
                        new HailVector(19, 13, -2, 1),
                        new HailVector(20, 19, 1, -5),
                        false),
                Arguments.of(
                        new HailVector(18, 19, -1, -1),
                        new HailVector(20, 25, -2, -2),
                        false),
                Arguments.of(
                        new HailVector(18, 19, -1, -1),
                        new HailVector(12, 31, -1, -2),
                        false),
                Arguments.of(
                        new HailVector(18, 19, -1, -1),
                        new HailVector(20, 19, 1, -5),
                        false),
                Arguments.of(
                        new HailVector(20, 25, -2, -2),
                        new HailVector(12, 31, -1, -2),
                        false),
                Arguments.of(
                        new HailVector(20, 25, -2, -2),
                        new HailVector(20, 19, 1, -5),
                        false),
                Arguments.of(
                        new HailVector(12, 31, -1, -2),
                        new HailVector(20, 19, 1, -5)
                        , false)
        );
    }


}