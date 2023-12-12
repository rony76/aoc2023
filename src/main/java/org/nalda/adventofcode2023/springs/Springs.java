package org.nalda.adventofcode2023.springs;

import java.util.stream.Stream;

public class Springs {
    private final SpringRecordParser parser = new SpringRecordParser();

    public long countArrangements(Stream<String> input) {
        return input
                .map(parser::parse)
                .mapToLong(DamagedSpringRecord::getNumberOfArrangements)
                .sum();
    }
}
