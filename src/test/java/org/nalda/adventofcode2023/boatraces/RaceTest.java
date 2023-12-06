package org.nalda.adventofcode2023.boatraces;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RaceTest {
    @Test
    void acceptance() {
        var waysToWin01 = new Race(7, BigInteger.valueOf(9)).bruteForceToWinningWays();
        var waysToWin02 = new Race(15, BigInteger.valueOf(40)).bruteForceToWinningWays();
        var waysToWin03 = new Race(30, BigInteger.valueOf(200)).bruteForceToWinningWays();

        assertThat(waysToWin01).isEqualTo(4);
        assertThat(waysToWin02).isEqualTo(8);
        assertThat(waysToWin03).isEqualTo(9);
    }

    @Test
    void acceptanceSingleLongerRace() {
        var numberOfWaysToWinLongerRace = new Race(71530, BigInteger.valueOf(940200)).bruteForceToWinningWays();
        assertThat(numberOfWaysToWinLongerRace).isEqualTo(71503);
    }

    @ParameterizedTest
    @MethodSource("acceptanceParams")
    void findWaysToWinBisecting(long time, BigInteger winningThreshold, long expectedWays) {
        final long ways = new Race(time, winningThreshold).bisectToWinningWays();

        assertThat(ways).isEqualTo(expectedWays);
    }

    public static Stream<Arguments> acceptanceParams() {
        return Stream.of(
                Arguments.of(7, BigInteger.valueOf(9), 4),
                Arguments.of(15, BigInteger.valueOf(40), 8),
                Arguments.of(30, BigInteger.valueOf(200), 9)
        );
    }

    @Test
    void acceptanceSingleLongerRaceBisecting() {
        final long numberOfWaysToWinLongerRace = new Race(71530, BigInteger.valueOf(940200)).bisectToWinningWays();
        assertThat(numberOfWaysToWinLongerRace).isEqualTo(71503);
    }
}