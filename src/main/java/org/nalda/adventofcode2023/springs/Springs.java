package org.nalda.adventofcode2023.springs;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Springs {
    private final SpringRecordParser parser = new SpringRecordParser();

    private static class ProgressTracker {
        private int progress = 0;

        public void track() {
            progress++;
            System.out.print("*");
            if (progress % 100 == 0) {
                System.out.println();
            }
        }
    }

    public long countArrangements(Stream<String> input) {
        Map<DamagedSpringRecord, Long> cache = new HashMap<>();

        final ProgressTracker tracker = new ProgressTracker();

        return input
                .map(parser::parse)
                .map(r -> r.unfold(5))
                .mapToLong(damagedSpringRecord -> damagedSpringRecord.arrangements(cache))
                .peek(l -> tracker.track())
                .sum();
    }

    public static void main(String[] args) {
        final Stream<String> input = ResourceUtil.getLineStream("springs-input.txt");
        final Springs springs = new Springs();

        long arrangementCount = springs.countArrangements(input);

        System.out.println("Arrangements: " + arrangementCount);
    }
}
