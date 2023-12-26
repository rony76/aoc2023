package org.nalda.adventofcode2023.maze;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MazeTest {
    @Test
    void acceptance() {
        List<String> input = ResourceUtil.getLineList("maze-acceptance.txt");
        Maze maze = new Maze(input);

        assertThat(maze.findLongestWalk()).isEqualTo(94);
    }
}
