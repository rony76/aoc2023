package org.nalda.adventofcode2023.cube;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class Game {

    private final int gameNumber;

    private final List<CubeSet> draws;

    public int getGameNumber() {
        return gameNumber;
    }

    // only for testing purposes
    List<CubeSet> getDraws() {
        return draws;
    }

    public boolean isValidFor(CubeSet limits) {
        return draws.stream().allMatch(d -> d.isValidFor(limits));
    }

    public int getGamePower() {
        return draws.stream()
                .reduce(CubeSet::getMinimumRequirementsWith)
                .orElseThrow(() -> new IllegalStateException("No draws found"))
                .getPower();
    }

    @AllArgsConstructor
    @Getter
    @Builder
    @EqualsAndHashCode
    public static class CubeSet {
        @Builder.Default
        private int red = 0;
        @Builder.Default
        private int green = 0;
        @Builder.Default
        private int blue = 0;

        public static CubeSet rgb(int r, int g, int b) {
            return new CubeSet(r, g, b);
        }

        public boolean isValidFor(CubeSet limits) {
            return limits.red >= red
                    && limits.green >= green
                    && limits.blue >= blue;
        }

        public CubeSet getMinimumRequirementsWith(CubeSet other) {
            return CubeSet.builder()
                    .red(Math.max(red, other.red))
                    .green(Math.max(green, other.green))
                    .blue(Math.max(blue, other.blue))
                    .build();
        }

        public int getPower() {
            return red * green * blue;
        }
    }
}
