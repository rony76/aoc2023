package org.nalda.adventofcode2023.oasis;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SequenceTest {
    public static Stream<Arguments> getSamples() {
        return Stream.of(
                Arguments.of(new long[]{0, 3, 6, 9, 12, 15}, 18, -3),
                Arguments.of(new long[]{1, 3, 6, 10, 15, 21}, 28, 0),
                Arguments.of(new long[]{10, 13, 16, 21, 30, 45}, 68, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("getSamples")
    void calcForSample(long[] sample, long expectedNext, long expectedPrev) {

        assertThat(new Sequence(sample).calculateNext()).isEqualTo(expectedNext);
        assertThat(new Sequence(sample).calculatePrev()).isEqualTo(expectedPrev);
    }
}