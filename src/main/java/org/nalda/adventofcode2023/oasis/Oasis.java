package org.nalda.adventofcode2023.oasis;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.stream.Stream;

public class Oasis {
    public static void main(String[] args) {
        final Oasis oasis = new Oasis();
        var input = ResourceUtil.getLineStream("oasis-input.txt");

        var sum  = oasis.extendSequencesAndSum(input);
        System.out.printf("Sum: %d%n", sum);
    }

    public long extendSequencesAndSum(Stream<String> sequenceStream) {
        return sequenceStream
                .map(Sequence::parse)
                .mapToLong(Sequence::calculateNext)
                .sum();
    }
}