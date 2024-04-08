package org.nalda.adventofcode2023;

import java.util.function.Supplier;

public class Timing {
    public static void runAndTrack(Runnable r) {
        runAndTrack(1, r);
    }

    public static void runAndTrack(int times, Runnable r) {
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

    public static <T> T runAndTrack(String operationName, Supplier<T> supplier) {
        var start = System.currentTimeMillis();
        try {
            return supplier.get();
        } finally {
            var end = System.currentTimeMillis();
            var elapsed = end - start;
            System.out.printf("Operation '%s' took %dms%n", operationName, elapsed);
        }
    }
}
