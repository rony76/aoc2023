package org.nalda.adventofcode2023.trench;

import org.nalda.adventofcode2023.ResourceUtil;
import org.nalda.adventofcode2023.Timing;
import org.nalda.adventofcode2023.grids.Direction;
import org.nalda.adventofcode2023.grids.Point;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Trench {
    private final int width;
    private final int height;
    private final Point origin;

    public Trench(int width, int height, Point origin) {
        this.width = width;
        this.height = height;
        this.origin = origin;
    }

    public long calculateLakeVolume(List<DigPlanEntry> entries) {
        char[][] map = new char[height][width];
        for (char[] row : map) {
            Arrays.fill(row, '.');
        }
        map[(int) origin.y()][(int) origin.x()] = '#';

        Point current = origin;
        final var size = entries.size();
        Direction prevDir = entries.get(size - 1).direction;
        for (int j = 0; j < size; j++) {
            DigPlanEntry entry = entries.get(j);
            Direction direction = entry.direction;
            Direction nextDir = j == size - 1 ? entries.get(0).direction : entries.get(j + 1).direction;
            map[(int) current.y()][(int) current.x()] = getAngle(prevDir, direction);

            for (int i = 0; i < entry.length(); i++) {
                current = direction.move(current);
                map[(int) current.y()][(int) current.x()] =
                        i == entry.length - 1 ? getAngle(direction, nextDir) :
                                direction.isHorizontal() ? '-' : '|';
            }

            prevDir = direction;
        }

        printMap(map);

        for (int i = 0; i < map.length; i++) {
            char[] row = map[i];
            boolean inside = false;
            for (int j = 0; j < row.length; j++) {
                char c = row[j];
                if (c == '|' || c == 'F' || c == '7') {
                    inside = !inside;
                }

                if (inside && c == '.') {
                    map[i][j] = '~';
                }
            }
        }

        printMap(map);

        long result = 0;
        for (char[] row : map) {
            for (char c : row) {
                if (c != '.') {
                    result++;
                }
            }
        }

        return result;
    }

    private char getAngle(Direction prevDir, Direction nextDir) {
        switch (prevDir) {
            case UP -> {
                if (nextDir == Direction.RIGHT) {
                    return 'F';
                } else if (nextDir == Direction.LEFT) {
                    return '7';
                }
            }
            case RIGHT -> {
                if (nextDir == Direction.DOWN) {
                    return '7';
                } else if (nextDir == Direction.UP) {
                    return 'J';
                }
            }
            case DOWN -> {
                if (nextDir == Direction.LEFT) {
                    return 'J';
                } else if (nextDir == Direction.RIGHT) {
                    return 'L';
                }
            }
            case LEFT -> {
                if (nextDir == Direction.UP) {
                    return 'L';
                } else if (nextDir == Direction.DOWN) {
                    return 'F';
                }
            }

        }
        throw new IllegalArgumentException("Unknown angle: " + prevDir + " " + nextDir);
    }

    private void printMap(char[][] map) {
        for (char[] row : map) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();
    }

    public record DigPlanEntry(Direction direction, int length, String rgbColor) {
        static DigPlanEntry fromString(String s) {
            var parts = s.split(" ");
            var directionString = parts[0];
            Direction direction = switch (directionString) {
                case "U" -> Direction.UP;
                case "R" -> Direction.RIGHT;
                case "D" -> Direction.DOWN;
                case "L" -> Direction.LEFT;
                default -> throw new IllegalArgumentException("Unknown direction: " + directionString);
            };
            var rgbColor = parts[2].substring(1, 8);
            return new DigPlanEntry(direction, Integer.parseInt(parts[1]), rgbColor);
        }
    }

    public record DigPlanLongEntry(Direction direction, long distance) {
        static DigPlanLongEntry fromString(String s) {
            var parts = s.split(" ");
            var encodedInstructions = parts[2].substring(1, 8);
            var distance = Integer.parseInt(encodedInstructions.substring(1, 6), 16);
            var direction = switch (encodedInstructions.charAt(6)) {
                case '0' -> Direction.RIGHT;
                case '1' -> Direction.DOWN;
                case '2' -> Direction.LEFT;
                case '3' -> Direction.UP;
                default -> throw new IllegalArgumentException("Unexpected direction " + encodedInstructions.charAt(6));
            };

            return new DigPlanLongEntry(direction, distance);
        }
    }

    private static class GeometryDetector {
        private int minVertical, maxVertical, minHorizontal, maxHorizontal;
        private int currentVertical = 0, currentHorizontal = 0;

        public void combine(GeometryDetector other) {
            this.minVertical = Math.min(this.minVertical, other.minVertical);
            this.maxVertical = Math.max(this.maxVertical, other.maxVertical);
            this.minHorizontal = Math.min(this.minHorizontal, other.minHorizontal);
            this.maxHorizontal = Math.max(this.maxHorizontal, other.maxHorizontal);
        }

        public void accept(DigPlanEntry entry) {
            switch (entry.direction()) {
                case UP -> {
                    currentVertical -= entry.length();
                    minVertical = Math.min(minVertical, currentVertical);
                }
                case RIGHT -> {
                    currentHorizontal += entry.length();
                    maxHorizontal = Math.max(maxHorizontal, currentHorizontal);
                }
                case DOWN -> {
                    currentVertical += entry.length();
                    maxVertical = Math.max(maxVertical, currentVertical);
                }
                case LEFT -> {
                    currentHorizontal -= entry.length();
                    minHorizontal = Math.min(minHorizontal, currentHorizontal);
                }
            }
        }

        public int getWidth() {
            return maxHorizontal - minHorizontal + 1;
        }

        public int getHeight() {
            return maxVertical - minVertical + 1;
        }

        public Point getOrigin() {
            return new Point(-minHorizontal, -minVertical);
        }

    }

    public static Trench fromEntries(List<DigPlanEntry> entries) {
        GeometryDetector geometryDetector = entries.stream()
                .collect(GeometryDetector::new, GeometryDetector::accept, GeometryDetector::combine);

        return new Trench(geometryDetector.getWidth(), geometryDetector.getHeight(), geometryDetector.getOrigin());
    }

    public static void main(String[] args) {
        Timing.runAndTrack(() -> {
            final List<DigPlanEntry> shortEntries = ResourceUtil.getLineStream("trench-input.txt")
                    .map(DigPlanEntry::fromString)
                    .collect(Collectors.toList());
            final Trench trench = Trench.fromEntries(shortEntries);

            long volume = trench.calculateLakeVolume(shortEntries);
            System.out.println("Volume with short entries: " + volume);

            volume = Trench.calculateLakeVolumeWithLongEntries(ResourceUtil.getLineStream("trench-input.txt"));
            System.out.println("Volume with long entries: " + volume);
        });
    }

    public static long calculateLakeVolumeWithLongEntries(Stream<String> lineStream) {
        List<DigPlanLongEntry> longEntries = lineStream
                .map(DigPlanLongEntry::fromString)
                .toList();

        Point prevPoint = new Point(0, 0);
        long result = 0L;
        for (DigPlanLongEntry entry : longEntries) {
            Point newPoint = entry.direction.move(prevPoint, entry.distance);


            result += prevPoint.x() * newPoint.y() - newPoint.x() * prevPoint.y();
            // we need to include the border, too
            result += entry.distance();
            prevPoint = newPoint;
        }
        // the following is not needed, as origin contributes with zeros
        // result += prevPoint.x * origin.y - origin.x * prevPoint.y;

        result = Math.abs(result) / 2 + 1;

        return result;
    }


}
