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
        return calculateNext(this.numbers);
    }

    private long calculateNext(long[] numbers) {
        final long[] childSeq = new long[numbers.length - 1];
        var allZeros = true;

        for (int i = 1; i < numbers.length; i++) {
            var diff = numbers[i] - numbers[i - 1];
            allZeros = allZeros && diff == 0;

            childSeq[i - 1] = diff;
        }

        if (allZeros) {
            return numbers[numbers.length - 1];
        } else {
            return numbers[numbers.length - 1] + calculateNext(childSeq);
        }
    }
}
