package org.nalda.adventofcode2023.oasis;

import org.nalda.adventofcode2023.ResourceUtil;
import org.nalda.adventofcode2023.Timing;

import java.util.function.ToLongFunction;
import java.util.stream.Stream;

public class Oasis {
    public static void main(String[] args) {
        final Oasis oasis = new Oasis();

        Timing.runAndTrack(() -> {
            var input = ResourceUtil.getLineStream("oasis-input.txt");
            var sum  = oasis.forwardSequencesAndSum(input);
            System.out.printf("Sum: %d%n", sum);

            input = ResourceUtil.getLineStream("oasis-input.txt");
            sum  = oasis.backwardSequencesAndSum(input);
            System.out.printf("Sum: %d%n", sum);
        }, 20);
    }

    public long forwardSequencesAndSum(Stream<String> sequenceStream) {
        return sumOverSequenceFunction(sequenceStream, Sequence::calculateNext);
    }

    public long backwardSequencesAndSum(Stream<String> sequenceStream) {
        return sumOverSequenceFunction(sequenceStream, Sequence::calculatePrev);
    }

    private long sumOverSequenceFunction(Stream<String> sequenceStream, ToLongFunction<Sequence> f) {
        return sequenceStream
                .map(Sequence::parse)
                .mapToLong(f)
                .sum();
    }
}