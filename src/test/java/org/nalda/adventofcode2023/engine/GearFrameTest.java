package org.nalda.adventofcode2023.engine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalLong;

import static org.assertj.core.api.Assertions.assertThat;

class GearFrameTest {
    @Test
    void gearInLineWithTwoParts() {
        ThreeLines threeLines = new ThreeLines(List.of(
                ".........",
                "..35*2...",
                "........."));
        GearFrame gearFrame = new GearFrame(threeLines, 4);

        final OptionalLong gearRatio = gearFrame.findGearRatio();
        assertThat(gearRatio).isPresent();
        assertThat(gearRatio.getAsLong()).isEqualTo(70L);
    }

    @ParameterizedTest
    @ValueSource(strings = {
                    "..35.....\n" +
                    "....*....\n" +
                    ".....20..",
                    ".....35..\n" +
                    "....*....\n" +
                    "..20.....",
    })
    void gearInDiagonalWithTwoParts(String frame) {
        ThreeLines threeLines = new ThreeLines(Arrays.asList(frame.split("\n")));
        GearFrame gearFrame = new GearFrame(threeLines, 4);

        final OptionalLong gearRatio = gearFrame.findGearRatio();
        assertThat(gearRatio).isPresent();
        assertThat(gearRatio.getAsLong()).isEqualTo(700L);
    }

    @ParameterizedTest
    @ValueSource(strings = {
                    "..350....\n" +
                    "....*....\n" +
                    ".....2...",
                    "...350...\n" +
                    "....*....\n" +
                    "...2.....",
                    "....350..\n" +
                    "....*....\n" +
                    "...2.....",
    })
    void gearWithAboveAndDiagonalParts(String frame) {
        ThreeLines threeLines = new ThreeLines(Arrays.asList(frame.split("\n")));
        GearFrame gearFrame = new GearFrame(threeLines, 4);

        final OptionalLong gearRatio = gearFrame.findGearRatio();
        assertThat(gearRatio).isPresent();
        assertThat(gearRatio.getAsLong()).isEqualTo(700L);
    }

    @ParameterizedTest
    @ValueSource(strings = {
                    ".....2...\n" +
                    "....*....\n" +
                    "..350....",
                    "...2.....\n" +
                    "....*....\n" +
                    "...350...",
                    "...2.....\n" +
                    "....*....\n" +
                    "....350.."
    })
    void gearWithBelowAndDiagonalParts(String frame) {
        ThreeLines threeLines = new ThreeLines(Arrays.asList(frame.split("\n")));
        GearFrame gearFrame = new GearFrame(threeLines, 4);

        final OptionalLong gearRatio = gearFrame.findGearRatio();
        assertThat(gearRatio).isPresent();
        assertThat(gearRatio.getAsLong()).isEqualTo(700L);
    }

    @Test
    void gearWithInLinePartsAbove() {
        ThreeLines threeLines = new ThreeLines(List.of(
                "..45.6...",
                "....*....",
                "........."));
        GearFrame gearFrame = new GearFrame(threeLines, 4);

        final OptionalLong gearRatio = gearFrame.findGearRatio();
        assertThat(gearRatio).isPresent();
        assertThat(gearRatio.getAsLong()).isEqualTo(270L);
    }

    @Test
    void gearWithInLinePartsBelow() {
        ThreeLines threeLines = new ThreeLines(List.of(
                ".........",
                "....*....",
                "..45.6..."));
        GearFrame gearFrame = new GearFrame(threeLines, 4);

        final OptionalLong gearRatio = gearFrame.findGearRatio();
        assertThat(gearRatio).isPresent();
        assertThat(gearRatio.getAsLong()).isEqualTo(270L);
    }

    @ParameterizedTest
    @ValueSource(strings = {
                    "....45...\n" +
                    "....*....\n" +
                    "...6.56..",
                    "..34.45...\n" +
                    "....*....\n" +
                    "....6....",
                    "....45...\n" +
                    "..16*....\n" +
                    "....6....",
    })
    void tooManyParts(String frame) {
        ThreeLines threeLines = new ThreeLines(Arrays.asList(frame.split("\n")));
        GearFrame gearFrame = new GearFrame(threeLines, 4);

        final OptionalLong gearRatio = gearFrame.findGearRatio();
        assertThat(gearRatio).isEmpty();
    }
}