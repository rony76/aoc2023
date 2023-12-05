package org.nalda.adventofcode2023.almanac;

import java.util.function.LongUnaryOperator;

public record Range(long destStart, long sourceStart, long length) implements LongUnaryOperator {

    @Override
    public long applyAsLong(long operand) {
        if (!(operand >= sourceStart && operand < sourceStart + length)) {
            return -1;
        }
        return destStart + (operand - sourceStart);
    }
}
