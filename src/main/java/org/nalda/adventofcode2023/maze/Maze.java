package org.nalda.adventofcode2023.maze;

import org.nalda.adventofcode2023.ResourceUtil;
import org.nalda.adventofcode2023.Timing;
import org.nalda.adventofcode2023.grids.Direction;
import org.nalda.adventofcode2023.grids.GridReader;
import org.nalda.adventofcode2023.grids.GridReader.Grid;
import org.nalda.adventofcode2023.grids.Position;

import java.util.*;

import static org.nalda.adventofcode2023.Timing.runAndTrack;
import static org.nalda.adventofcode2023.grids.Direction.*;

public class Maze {
    private static final Direction[] JUST_UP = {UP};
    private static final Direction[] JUST_RIGHT = {RIGHT};
    private static final Direction[] JUST_DOWN = {DOWN};
    private static final Direction[] JUST_LEFT = {LEFT};
    private final Grid grid;
    private final boolean slipperySlopes;
    private final Position entrance;
    private final Position exit;

    public Maze(List<String> input, boolean slipperySlopes) {
        grid = new GridReader().read(input);
        this.slipperySlopes = slipperySlopes;
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
        // this can be seens a graph of nodes... as most of the steps are "constrained"
        // what are my nodes?
        // the junctions...
        Set<Position> junctions = runAndTrack("findJunctions", this::findJunctions);

        Map<Position, Map<Position, Long>> adjacencyList =
                runAndTrack("buildAdjacencyList", () -> buildAdjacencyList(junctions));

        // now I'll try brute-forcing again, but just across junctions
        Set<Position> visited = new HashSet<>();
        visited.add(entrance);
        return runAndTrack("findLongestWalk", () -> findLongestWalk(adjacencyList, entrance, visited));
    }

    private long findLongestWalk(Map<Position, Map<Position, Long>> adjacencyList, Position pos, Set<Position> visited) {
        if (pos.equals(exit)) {
            return 0;
        }

        long max = -1;
        Map<Position, Long> neighboursToVisit = adjacencyList.get(pos);
        for (Map.Entry<Position, Long> entry : neighboursToVisit.entrySet()) {
            Position neighbour = entry.getKey();
            Long distance = entry.getValue();

            if (visited.contains(neighbour)) {
                continue;
            }

            visited.add(neighbour);
            long w = findLongestWalk(adjacencyList, neighbour, visited);
            visited.remove(neighbour);

            if (w >= 0) {
                max = Math.max(max, w + distance);
            }
        }

        return max;
    }

    private record Segment(Position p, long length) {
    }

    private Map<Position, Map<Position, Long>> buildAdjacencyList(Set<Position> junctions) {
        Map<Position, Map<Position, Long>> result = new HashMap<>();
        for (Position junction : junctions) {
            if (junction.equals(exit)) {
                continue;
            }

            Map<Position, Long> adjacentJunctionsAndDistance = new HashMap<>();
            result.put(junction, adjacentJunctionsAndDistance);

            Deque<Segment> stack = new ArrayDeque<>();
            Set<Position> visited = new HashSet<>();
            stack.push(new Segment(junction, 0));
            visited.add(junction);

            while (!stack.isEmpty()) {
                Segment segment = stack.pop();
                if (segment.length != 0 && junctions.contains(segment.p)) {
                    adjacentJunctionsAndDistance.put(segment.p, segment.length);
                    continue;
                }

                for (Direction dir : directionsFrom(segment.p)) {
                    Position newPos = dir.move(segment.p);
                    if (grid.contains(newPos) && grid.at(newPos) != '#' && !visited.contains(newPos)) {
                        stack.push(new Segment(newPos, segment.length + 1));
                        visited.add(newPos);
                    }
                }
            }

        }
        return result;
    }

    private Direction[] directionsFrom(Position p) {
        char c = grid.at(p);
        return switch (c) {
            case '.' -> Direction.values();
            case '^' -> slipperySlopes ? JUST_UP : Direction.values();
            case '>' -> slipperySlopes ? JUST_RIGHT : Direction.values();
            case 'v' -> slipperySlopes ? JUST_DOWN : Direction.values();
            case '<' -> slipperySlopes ? JUST_LEFT : Direction.values();
            default -> throw new IllegalArgumentException("Cannot determine directions from " + c);
        };
    }

    private Set<Position> findJunctions() {
        Set<Position> junctions = new HashSet<>();
        junctions.add(entrance);
        junctions.add(exit);
        for (int row = 0; row < grid.height(); row++) {
            for (int col = 0; col < grid.width(); col++) {
                Position pos = new Position(row, col);
                if (grid.at(pos) == '#') {
                    continue;
                }

                int validNeighbours = 0;
                for (Direction dir : values()) {
                    Position neighbour = dir.move(pos);
                    if (grid.contains(neighbour) && grid.at(neighbour) != '#') {
                        validNeighbours++;
                    }
                }

                if (validNeighbours > 2) {
                    junctions.add(pos);
                }
            }
        }
        return junctions;
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
        var maze = new Maze(input, true);

        long longestWalk = maze.findLongestWalk();
        System.out.println("Longest walk with slippery slopes: " + longestWalk);

        maze = new Maze(input, false);

        longestWalk = maze.findLongestWalk();
        System.out.println("Longest walk without slippery slopes: " + longestWalk);
    }
}
