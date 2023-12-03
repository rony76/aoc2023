package org.nalda.adventofcode2023.cube;

import org.nalda.adventofcode2023.cube.Game.CubeSet;
import org.nalda.adventofcode2023.cube.Game.CubeSet.CubeSetBuilder;

import java.util.ArrayList;
import java.util.List;

public class GameParser {
    public Game parse(String s) {
        var gameAndDraws = s.split(":");
        var gameNumber = Integer.parseInt(gameAndDraws[0].split(" ")[1]);

        var draws = gameAndDraws[1].trim().split(";");
        final List<CubeSet> drawList = buildDraws(draws);

        return new Game(gameNumber, drawList);
    }

    private List<CubeSet> buildDraws(String[] draws) {
        var drawList = new ArrayList<CubeSet>(draws.length);
        for (int i = 0; i < draws.length; i++) {
            draws[i] = draws[i].trim();
            drawList.add(buildDraw(draws[i].split(",")));
        }
        return drawList;
    }

    private CubeSet buildDraw(String[] cubes) {
        final CubeSetBuilder cubeSetBuilder = CubeSet.builder();

        for (int i = 0; i < cubes.length; i++) {
            cubes[i] = cubes[i].trim();
            final String[] numberAndColor = cubes[i].split(" ");
            final int drawNumber = Integer.parseInt(numberAndColor[0]);
            final String color = numberAndColor[1];

            switch (color) {
                case "green" -> cubeSetBuilder.green(drawNumber);
                case "red" -> cubeSetBuilder.red(drawNumber);
                case "blue" -> cubeSetBuilder.blue(drawNumber);
                default -> throw new IllegalArgumentException("Unknown color: " + color);
            }
        }

        return cubeSetBuilder.build();
    }
}
