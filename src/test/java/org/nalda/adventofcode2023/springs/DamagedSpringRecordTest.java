package org.nalda.adventofcode2023.springs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.nalda.adventofcode2023.springs.SpringCondition.*;

class DamagedSpringRecordTest {
    @Test
    void countsSample1() {
        final DamagedSpringRecord record = new DamagedSpringRecord(
                new SpringCondition[]{UNKNOWN, UNKNOWN, UNKNOWN, OPERATIONAL, DAMAGED, DAMAGED, DAMAGED},
                new int[]{1, 1, 3}
        );

        final long arrangements = record.getNumberOfArrangements();

        assertEquals(1, arrangements);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "#.#.### 1,1,3",
            ".#...#....###. 1,1,3",
            ".#.###.#.###### 1,3,1,6",
            "####.#...#... 4,1,1",
            "#....######..#####. 1,6,5",
            ".###.##....# 3,2,1"
    })
    void acceptsRecordWithNoUnknowns(String recordText) {
        final DamagedSpringRecord record = new SpringRecordParser().parse(recordText);

        final long arrangements = record.getNumberOfArrangements();

        assertEquals(1, arrangements);

    }
}