package org.nalda.adventofcode2023.hash;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.Arrays;

public class Hash {
    public long stepSum(String s) {
        return Arrays.stream(s.split(","))
                .mapToLong(this::stepValue)
                .sum();
    }

    long stepValue(String s) {
        long result = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = s.charAt(i);
            result += ascii;
            result *= 17;
            result %= 256;
        }
        return result;
    }

    public static void main(String[] args) {
        final Hash hash = new Hash();
        String line = ResourceUtil.getLineList("hash-input.txt").get(0);

        long sum = hash.stepSum(line);

        System.out.println("Sum of steps' hash: " + sum);
    }
}
