package org.nalda.adventofcode2023.hash;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class HashTest {
    @Test
    void acceptanceStar1() {
        final Hash hash = new Hash();

        long sum = hash.stepSum("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7");

        assertThat(sum).isEqualTo(1320);
    }

    @Test
    void acceptanceStar2() {
        final Hash hash = new Hash();

        long power = hash.calculateFocusingPower("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7");

        assertThat(power).isEqualTo(145);
    }

    public static Stream<Arguments> stepSamples() {
        return Stream.of(
                Arguments.of("rn=1", 30),
                Arguments.of("cm-", 253),
                Arguments.of("qp=3", 97),
                Arguments.of("cm=2", 47),
                Arguments.of("qp-", 14),
                Arguments.of("pc=4", 180),
                Arguments.of("ot=9", 9),
                Arguments.of("ab=5", 197),
                Arguments.of("pc-", 48),
                Arguments.of("pc=6", 214),
                Arguments.of("ot=7", 231)
        );
    }

    @ParameterizedTest
    @MethodSource("stepSamples")
    void testSample(String s, int expectedHash) {
        final Hash hash = new Hash();

        long hashValue = hash.stepValue(s);

        assertThat(hashValue).isEqualTo(expectedHash);
    }
}
