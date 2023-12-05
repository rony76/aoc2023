package org.nalda.adventofcode2023.almanac;

import java.util.Comparator;
import java.util.List;
import java.util.function.LongUnaryOperator;

public class RangeFunction implements LongUnaryOperator {
    private final String mapName;
    private final List<Range> ranges;

    public RangeFunction(String mapName, List<Range> ranges) {
        this.mapName = mapName;
        this.ranges = ranges.stream().sorted(Comparator.comparingLong(Range::sourceStart)).toList();
    }

    @Override
    public long applyAsLong(long operand) {
        if (operand < ranges.get(0).sourceStart()) {
            return operand;
        }

        for (Range range : ranges) {
            final long l = range.applyAsLong(operand);
            if (l != -1) {
                return l;
            }
        }

        return operand;
    }
}
