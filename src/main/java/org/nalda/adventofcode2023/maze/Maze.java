package org.nalda.adventofcode2023.maze;

import org.nalda.adventofcode2023.ResourceUtil;
import org.nalda.adventofcode2023.grids.Direction;
import org.nalda.adventofcode2023.grids.GridReader;
import org.nalda.adventofcode2023.grids.GridReader.Grid;
import org.nalda.adventofcode2023.grids.Position;

import java.util.*;

import static org.nalda.adventofcode2023.grids.Direction.*;

public class Maze {
    private final Grid grid;
    private final Position entrance;
    private final Position exit;

    public Maze(List<String> input) {
        grid = new GridReader().read(input);
        entrance = findEntrance();
        exit = findExit();
    }

    private Position findEntrance() {
        return findFreeInRow(0);
    }

    private Position findExit() {
        return findFreeInRow(grid.height() - 1);
    }

    public long findLongestWalk() {
        return findLongestWalk(entrance, UP, 0, Collections.emptySet());
    }
    private record Move(Direction direction, Position target) {
    }

    private long findLongestWalk(Position pos, Direction from, long length, Set<Position> prevVisited) {
        if (pos.equals(exit)) {
            return length;
        }

        Set<Position> visited = new HashSet<>(prevVisited);
        List<Move> suitableMoves;
        while (true) {
            visited.add(pos);
            suitableMoves = new ArrayList<>();
            for (Direction d : values()) {
                if (!d.equals(from)) {
                    Move m = new Move(d, d.move(pos));
                    if (grid.contains(m.target)) {
                        if (canRunAcross(m)) {
                            if (!prevVisited.contains(m.target)) {
                                suitableMoves.add(m);
                            }
                        }
                    }
                }
            }
            if (suitableMoves.isEmpty()) {
                return pos.equals(exit) ? length : 0;
            }

            if (suitableMoves.size() != 1) {
                break;
            }

            Move move = suitableMoves.get(0);
            from = move.direction.opposite();
            pos = move.target;
            length++;
        }

        long best = 0;
        for (Move m : suitableMoves) {
            long longestWalk = findLongestWalk(m.target, m.direction.opposite(), length + 1, visited);
            if (longestWalk > best) {
                best = longestWalk;
            }
        }
        return best;
    }

    private boolean canRunAcross(Move move) {
        char c = grid.at(move.target);
        return switch (c) {
            case '.' -> true;
            case '#' -> false;
            case '^' -> move.direction().equals(UP);
            case '>' -> move.direction().equals(RIGHT);
            case 'v' -> move.direction().equals(DOWN);
            case '<' -> move.direction().equals(LEFT);
            default -> throw new IllegalArgumentException("Cannot determine whether " + c + " can be run across");
        };
    }

    private Position findFreeInRow(int row) {
        for (int col = 0; col < grid.width(); col++) {
            if (grid.grid()[row][col] == '.') {
                return new Position(row, col);
            }
        }
        throw new IllegalArgumentException("Cannot find free slot!");
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtil.getLineList("maze-input.txt");
        Maze maze = new Maze(input);

        long longestWalk = maze.findLongestWalk();
        System.out.println("Longest walk: " + longestWalk);

    }
}
