package org.nalda.adventofcode2023.springs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DamagedSpringRecordTest {
    @Test
    void countsSample1() {
        final DamagedSpringRecord record = new DamagedSpringRecord(
                "???.###",
                new int[]{1, 1, 3}
        );

        final long arrangements = record.arrangements();

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

        final long arrangements = record.arrangements();

        assertEquals(1, arrangements);

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "#.#.### 1,1,4",
            ".#...#... 1,1,3",
            ".#.####.###### 1,3,1,6"
    })
    void rejectsRecordWithNoUnknowns(String recordText) {
        final DamagedSpringRecord record = new SpringRecordParser().parse(recordText);

        final long arrangements = record.arrangements();

        assertEquals(0, arrangements);
    }

    public static Stream<Arguments> acceptanceRecords() {
        return Stream.of(
                Arguments.of("???.### 1,1,3", 1),
                Arguments.of(".??..??...?##. 1,1,3", 4),
                Arguments.of("?#?#?#?#?#?#?#? 1,3,1,6", 1),
                Arguments.of("????.#...#... 4,1,1", 1),
                Arguments.of("????.######..#####. 1,6,5", 4),
                Arguments.of("?###???????? 3,2,1", 10)
        );
    }

    @ParameterizedTest
    @MethodSource("acceptanceRecords")
    void acceptance(String recordText, long expectedArrangements) {
        final DamagedSpringRecord record = new SpringRecordParser().parse(recordText);

        final long arrangements = record.arrangements();

        assertEquals(expectedArrangements, arrangements);
    }

    @Test
    void canUnfold() {
        final DamagedSpringRecord record = new DamagedSpringRecord(
                "???.###",
                new int[]{1, 1, 3}
        );

        final DamagedSpringRecord unfolded = record.unfold(3);

        assertEquals(
                new DamagedSpringRecord(
                        "???.###????.###????.###",
                        new int[]{1, 1, 3, 1, 1, 3, 1, 1, 3}
                ),
                unfolded
        );
    }
}