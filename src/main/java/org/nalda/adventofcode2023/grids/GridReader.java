package org.nalda.adventofcode2023.grids;

import java.util.List;

public class GridReader {
    public record Grid(char[][] grid, int height, int width) {

        public boolean contains(Position pos) {
            return pos.column() >= 0
                    && pos.column() < width
                    && pos.row() >= 0
                    && pos.row() < height;
        }

        public char at(Position next) {
            return grid()[next.row()][next.column()];
        }
    }

    public Grid read(List<String> input) {
        var height = input.size();
        var width = input.get(0).length();
        var grid = new char[height][width];
        for (int i = 0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
        }

        return new Grid(grid, height, width);
    }
}
