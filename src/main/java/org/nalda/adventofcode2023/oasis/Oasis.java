package org.nalda.adventofcode2023.oasis;

import org.nalda.adventofcode2023.ResourceUtil;
import org.nalda.adventofcode2023.Timing;

import java.util.stream.Stream;

public class Oasis {
    public static void main(String[] args) {
        final Oasis oasis = new Oasis();

        Timing.runAndTrack(() -> {
            var input = ResourceUtil.getLineStream("oasis-input.txt");
            var sum  = oasis.extendSequencesAndSum(input);
            System.out.printf("Sum: %d%n", sum);
        }, 20);
    }

    public long extendSequencesAndSum(Stream<String> sequenceStream) {
        return sequenceStream
                .map(Sequence::parse)
                .mapToLong(Sequence::calculateNext)
                .sum();
    }
}