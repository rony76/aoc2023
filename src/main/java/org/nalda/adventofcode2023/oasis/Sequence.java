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

    private long calculateNext(long[] numbers, int length) {
        var allZeros = true;

        for (int i = 1; i < length; i++) {
            var diff = numbers[i] - numbers[i - 1];
            allZeros = allZeros && diff == 0;

            numbers[i - 1] = diff;
        }

        if (allZeros) {
            return numbers[length - 1];
        } else {
            return numbers[length - 1] + calculateNext(numbers, length -1);
        }
    }
}
