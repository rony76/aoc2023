package org.nalda.adventofcode2023;

public class Timing {
    public static void runAndTrack(Runnable r) {
        runAndTrack(r, 1);
    }

    public static void runAndTrack(Runnable r, int times) {
        var start = System.currentTimeMillis();
        try {
            for (int i = 0; i < times; i++) {
                r.run();
            }
        } finally {
            var end = System.currentTimeMillis();
            var elapsed = end - start;
            System.out.printf("Overall operation took %dms, averaging to %dms per run%n", elapsed, elapsed / times);
        }

    }
}
