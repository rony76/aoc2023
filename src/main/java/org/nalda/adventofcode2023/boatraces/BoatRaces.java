package org.nalda.adventofcode2023.boatraces;

import java.math.BigInteger;
import java.util.List;

public class BoatRaces {
    public static void main(String[] args) {
        /*
         * Time:        40     81     77     72
         * Distance:   219   1012   1365   1089
         * */
        List<Race> races = List.of(
                new Race(40, BigInteger.valueOf(219)),
                new Race(81, BigInteger.valueOf(1012)),
                new Race(77, BigInteger.valueOf(1365)),
                new Race(72, BigInteger.valueOf(1089))
        );

        final long totalWaysToWin = races.stream()
                .mapToLong(Race::bruteForceToWinningWays)
                .peek(System.out::println)
                .reduce((t, l) -> t * l)
                .orElseThrow();

        System.out.println("Star 1: Total ways to win: " + totalWaysToWin);

        final Race race = new Race(40817772, new BigInteger("219101213651089"));

        executeAndPrintElapsed(() -> System.out.println("Star 2: Longer race ways to win, brute force: " + race.bruteForceToWinningWays()));
        executeAndPrintElapsed(() -> System.out.println("Star 2: Longer race ways to win, bisecting: " + race.bisectToWinningWays()));
        executeAndPrintElapsed(() -> System.out.println("Star 2: Longer race ways to win, parabola formula: " + race.bisectToWinningWays()));
    }

    private static void executeAndPrintElapsed(Runnable r) {
        final long start = System.currentTimeMillis();
        r.run();
        final long elapsed = System.currentTimeMillis() - start;
        System.out.printf("^^^^ Operation took %dms ^^^^ %n%n", elapsed);
    }
}