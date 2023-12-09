package org.nalda.adventofcode2023.oasis;

import java.util.Arrays;

public class Sequence {
    private final long[] numbers;

    public Sequence(long[] numbers) {
        this.numbers = numbers;
    }

    public static Sequence parse(String s) {
        var numbers = Arrays.stream(s.split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
        return new Sequence(numbers);
    }

    public long calculateNext() {
        return calculateNext(numbers.clone(), numbers.length);
    }

    public long calculatePrev() {
        return calculatePrev(numbers.clone(), numbers.length);
    }

    private long calculateNext(long[] numbers, int length) {
        var boundaryValue = numbers[length - 1];

        boolean allZeros = replaceWithDiffSequence(numbers, length);

        long childNext = allZeros ? 0 : calculateNext(numbers, length - 1);

        return boundaryValue + childNext;
    }

    private long calculatePrev(long[] numbers, int length) {
        var boundaryValue = numbers[0];

        boolean allZeros = replaceWithDiffSequence(numbers, length);

        long childPrev = allZeros ? 0 : calculatePrev(numbers, length - 1);

        return boundaryValue - childPrev;
    }

    private boolean replaceWithDiffSequence(long[] numbers, int length) {
        var allZeros = true;
        for (int i = 1; i < length; i++) {
            var diff = numbers[i] - numbers[i - 1];
            allZeros = allZeros && diff == 0;

            numbers[i - 1] = diff;
        }
        return allZeros;
    }
}
