package org.nalda.adventofcode2023.trebuchet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TrebuchetTest {
    private final Trebuchet trebuchet = new Trebuchet();

    @Test
    void canParseDigitsAtBothEnds() {
        assertThat(trebuchet.extractCalibrationParameter("1abc2")).isEqualTo(12);
    }

    @Test
    void canParseDigitsWithAlphasInBetween() {
        assertThat(trebuchet.extractCalibrationParameter("pqr3stu8vwx")).isEqualTo(38);
    }

    @Test
    void canParseDigitsWithAlphasAndDigitsInBetween() {
        assertThat(trebuchet.extractCalibrationParameter("a1b2c3d4e5f")).isEqualTo(15);
    }

    @Test
    void canParseSingleDigit() {
        assertThat(trebuchet.extractCalibrationParameter("treb7uchet")).isEqualTo(77);
    }

    @Test
    void canFindFirstDigitWhenStringBeginsWithDigit() {
        assertThat(trebuchet.getFirstDigit("123")).isEqualTo(1);
        assertThat(trebuchet.getFirstDigit("987")).isEqualTo(9);
    }

    @Test
    void canFindFirstDigitWhenStringContainsDigitBeforeDigitName() {
        assertThat(trebuchet.getFirstDigit("a1b2c3d")).isEqualTo(1);
        assertThat(trebuchet.getFirstDigit("x9y8w7z")).isEqualTo(9);
    }

    @Test
    void canFindFirstDigitWhenStringBeginsWithDigitName() {
        assertThat(trebuchet.getFirstDigit("two4motor5")).isEqualTo(2);
    }

    public static Stream<Arguments> firstDigitExamples() {
        return Stream.of(
                Arguments.of("two1nine", 2),
                Arguments.of("eightwothree", 8),
                Arguments.of("abcone2threexyz", 1),
                Arguments.of("xtwone3four", 2),
                Arguments.of("4nineeightseven2", 4),
                Arguments.of("zoneight234", 1),
                Arguments.of("7pqrstsixteen", 7)
        );
    }

    public static Stream<Arguments> lastDigitExamples() {
        return Stream.of(
                Arguments.of("two1nine", 9),
                Arguments.of("eightwothree", 3),
                Arguments.of("abcone2threexyz", 3),
                Arguments.of("xtwone3four", 4),
                Arguments.of("4nineeightseven2", 2),
                Arguments.of("zoneight234", 4),
                Arguments.of("7pqrstsixteen", 6)
        );
    }

    public static Stream<Arguments> calibrationParameterExamples() {
        return Stream.of(
                Arguments.of("two1nine", 29),
                Arguments.of("eightwothree", 83),
                Arguments.of("abcone2threexyz", 13),
                Arguments.of("xtwone3four", 24),
                Arguments.of("4nineeightseven2", 42),
                Arguments.of("zoneight234", 14),
                Arguments.of("7pqrstsixteen", 76)
        );
    }

    @ParameterizedTest
    @MethodSource("firstDigitExamples")
    void canFindFirstDigitInExamples(String line, int expectedDigit) {
        assertThat(trebuchet.getFirstDigit(line)).isEqualTo(expectedDigit);
    }

    @ParameterizedTest
    @MethodSource("lastDigitExamples")
    void canFindLastDigitInExamples(String line, int expectedDigit) {
        assertThat(trebuchet.getLastDigit(line)).isEqualTo(expectedDigit);
    }

    @ParameterizedTest
    @MethodSource("calibrationParameterExamples")
    void canExtractCalibrationParametersFromExamples(String line, int parameter) {
        assertThat(trebuchet.extractCalibrationParameter(line)).isEqualTo(parameter);
    }
}