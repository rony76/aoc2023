package org.nalda.adventofcode2023.poker;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class WinningCollector implements Collector<HandAndBid, WinningCollector, Long> {
    private long totalWinnings = 0L;
    private int bidCount = 0;

    public void accept(HandAndBid handAndBid) {
        totalWinnings += handAndBid.bid() * ++bidCount;
    }

    public long getTotalWinnings() {
        return totalWinnings;
    }

    @Override
    public Supplier<WinningCollector> supplier() {
        return WinningCollector::new;
    }

    @Override
    public BiConsumer<WinningCollector, HandAndBid> accumulator() {
        return WinningCollector::accept;
    }

    @Override
    public BinaryOperator<WinningCollector> combiner() {
        return (a, b) -> {
            throw new UnsupportedOperationException();
        };
    }

    @Override
    public Function<WinningCollector, Long> finisher() {
        return WinningCollector::getTotalWinnings;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}