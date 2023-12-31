package org.nalda.adventofcode2023.reflector;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectorsTest {
    @Test
    void acceptanceNorthWeight() {
        final List<String> input = ResourceUtil.getLineList("reflector-acceptance.txt");
        Reflector reflector = Reflector.fromStrings(input);

        reflector = reflector.tiltNorth();
        final long northWeight = reflector.findNorthWeight();

        assertThat(northWeight).isEqualTo(136);
    }

    @Test
    void acceptanceABillionCycles() {
        final List<String> input = ResourceUtil.getLineList("reflector-acceptance.txt");
        var reflector = Reflector.fromStrings(input);

        reflector = reflector.rotateABillionTimes();

        final long northWeight = reflector.findNorthWeight();

        assertThat(northWeight).isEqualTo(64);
    }

    @Test
    void canTiltNorth() {
        final Reflector starting = new Reflector(
                "O....#...." +
                        "O.OO#....#" +
                        ".....##..." +
                        "OO.#O....O" +
                        ".O.....O#." +
                        "O.#..O.#.#" +
                        "..O..#O..O" +
                        ".......O.." +
                        "#....###.." +
                        "#OO..#....", 10);

        var tilted = starting.tiltNorth();

        assertThat(tilted).isEqualTo(new Reflector(
                "OOOO.#.O.." +
                        "OO..#....#" +
                        "OO..O##..O" +
                        "O..#.OO..." +
                        "........#." +
                        "..#....#.#" +
                        "..O..#.O.O" +
                        "..O......." +
                        "#....###.." +
                        "#....#....", 10));
    }

    @Test
    void canTiltWest() {
        final Reflector starting = new Reflector(
                "O....#...." +
                        "O.OO#....#" +
                        ".....##..." +
                        "OO.#O....O" +
                        ".O.....O#." +
                        "O.#..O.#.#" +
                        "..O..#O..O" +
                        ".......O.." +
                        "#....###.." +
                        "#OO..#....", 10);

        var tilted = starting.tiltWest();

        assertThat(tilted).isEqualTo(new Reflector(
                "O....#...." +
                        "OOO.#....#" +
                        ".....##..." +
                        "OO.#OO...." +
                        "OO......#." +
                        "O.#O...#.#" +
                        "O....#OO.." +
                        "O........." +
                        "#....###.." +
                        "#OO..#....", 10));
    }

    @Test
    void canTiltSouth() {
        final Reflector starting = new Reflector(
                "O....#...." +
                        "O.OO#....#" +
                        ".....##..." +
                        "OO.#O....O" +
                        ".O.....O#." +
                        "O.#..O.#.#" +
                        "..O..#O..O" +
                        ".......O.." +
                        "#....###.." +
                        "#OO..#....", 10);

        var tilted = starting.tiltSouth();

        assertThat(tilted).isEqualTo(new Reflector(
                ".....#...." +
                        "....#....#" +
                        "...O.##..." +
                        "...#......" +
                        "O.O....O#O" +
                        "O.#..O.#.#" +
                        "O....#...." +
                        "OO....OO.." +
                        "#OO..###.." +
                        "#OO.O#...O", 10));
    }

    @Test
    void canTiltEast() {
        final Reflector starting = new Reflector(
                "O....#...." +
                        "O.OO#....#" +
                        ".....##..." +
                        "OO.#O....O" +
                        ".O.....O#." +
                        "O.#..O.#.#" +
                        "..O..#O..O" +
                        ".......O.." +
                        "#....###.." +
                        "#OO..#....", 10);

        var tilted = starting.tiltEast();

        assertThat(tilted).isEqualTo(new Reflector(
                "....O#...." +
                        ".OOO#....#" +
                        ".....##..." +
                        ".OO#....OO" +
                        "......OO#." +
                        ".O#...O#.#" +
                        "....O#..OO" +
                        ".........O" +
                        "#....###.." +
                        "#..OO#....", 10));
    }

    @Test
    void canCycleOnce() {
        final Reflector starting = new Reflector(
                "O....#...." +
                        "O.OO#....#" +
                        ".....##..." +
                        "OO.#O....O" +
                        ".O.....O#." +
                        "O.#..O.#.#" +
                        "..O..#O..O" +
                        ".......O.." +
                        "#....###.." +
                        "#OO..#....", 10);

        var cycled = starting.cycle();

        assertThat(cycled).isEqualTo(new Reflector(
                ".....#...." +
                        "....#...O#" +
                        "...OO##..." +
                        ".OO#......" +
                        ".....OOO#." +
                        ".O#...O#.#" +
                        "....O#...." +
                        "......OOOO" +
                        "#...O###.." +
                        "#..OO#....", 10));
    }

    @Test
    void canCycleTwice() {
        final Reflector starting = new Reflector(
                "O....#...." +
                        "O.OO#....#" +
                        ".....##..." +
                        "OO.#O....O" +
                        ".O.....O#." +
                        "O.#..O.#.#" +
                        "..O..#O..O" +
                        ".......O.." +
                        "#....###.." +
                        "#OO..#....", 10);

        var cycled = starting.cycle().cycle();

        assertThat(cycled).isEqualTo(new Reflector(
                ".....#...." +
                        "....#...O#" +
                        ".....##..." +
                        "..O#......" +
                        ".....OOO#." +
                        ".O#...O#.#" +
                        "....O#...O" +
                        ".......OOO" +
                        "#..OO###.." +
                        "#.OOO#...O", 10));
    }

    @Test
    void canCycle3Times() {
        final Reflector starting = new Reflector(
                "O....#...." +
                        "O.OO#....#" +
                        ".....##..." +
                        "OO.#O....O" +
                        ".O.....O#." +
                        "O.#..O.#.#" +
                        "..O..#O..O" +
                        ".......O.." +
                        "#....###.." +
                        "#OO..#....", 10);

        var cycled = starting.cycle().cycle().cycle();

        assertThat(cycled).isEqualTo(new Reflector(
                ".....#...." +
                        "....#...O#" +
                        ".....##..." +
                        "..O#......" +
                        ".....OOO#." +
                        ".O#...O#.#" +
                        "....O#...O" +
                        ".......OOO" +
                        "#...O###.O" +
                        "#.OOO#...O", 10));
    }
}
