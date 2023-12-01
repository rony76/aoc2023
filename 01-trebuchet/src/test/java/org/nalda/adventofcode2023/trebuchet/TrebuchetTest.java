package org.nalda.adventofcode2023.trebuchet;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TrebuchetTest {
    @Test
    void canParseDigitsAtBothEnds() {
        assertThat(Trebuchet.extractCalibrationParameter("1abc2")).isEqualTo(12);
    }

    @Test
    void canParseDigitsWithAlphasInBetween() {
        assertThat(Trebuchet.extractCalibrationParameter("pqr3stu8vwx")).isEqualTo(38);
    }

    @Test
    void canParseDigitsWithAlphasAndDigitsInBetween() {
        assertThat(Trebuchet.extractCalibrationParameter("a1b2c3d4e5f")).isEqualTo(15);
    }

    @Test
    void canParseSingleDigit() {
        assertThat(Trebuchet.extractCalibrationParameter("treb7uchet")).isEqualTo(77);
    }
}