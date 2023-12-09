package org.nalda.adventofcode2023.navigation;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Navigation {

    public static void main(String[] args) {
        final Navigation navigation = new Navigation();
        final List<String> input = ResourceUtil.getLineList("navigation-input.txt");

        navigation.findPeriodicPaths(input)
                .forEach(System.out::println);
    }

    public Stream<PeriodicPath> findPeriodicPaths(List<String> input) {
        final String directionList = input.get(0);
        final int period = directionList.length();
        System.out.println("Period is " + period);

        var nodeMap = input.stream()
                .skip(2)
                .map(Node::parse)
                .collect(Collectors.toMap(n -> n.name, n -> n));
        nodeMap.values().forEach(n -> n.resolve(nodeMap));

        return nodeMap.values().stream()
                .filter(Node::isStart)
                .parallel()
                .flatMap(n -> n.generateLoops(directionList, period));
    }

    private DirectionProvider createDirectionProvider(String directionList) {
        return new DirectionProvider(directionList);
    }

    enum Direction {
        LEFT, RIGHT
    }

    record PeriodicPath(String startNode, String endNode, long steps) {
    }

    static class DirectionProvider {
        private final String directionList;
        private int index = 0;

        DirectionProvider(String directionList) {
            this.directionList = directionList;
        }

        Direction next() {
            final char c = directionList.charAt(index++);
            if (index >= directionList.length()) {
                index = 0;
            }
            return c == 'L' ? Direction.LEFT : Direction.RIGHT;
        }
    }

    static final class Node {
        static final Pattern PATTERN = Pattern.compile("(\\w+) = \\((\\w+),\\s+(\\w+)\\)");
        private final String name;
        private final String leftName;
        private final String rightName;
        private Node left;
        private Node right;

        Node(String name, String leftName, String rightName) {
            this.name = name;
            this.leftName = leftName;
            this.rightName = rightName;
        }

        void resolve(Map<String, Node> nodeMap) {
            left = nodeMap.get(leftName);
            right = nodeMap.get(rightName);
        }

        Node go(Direction direction) {
            return direction == Direction.LEFT ? left : right;
        }

        static Node parse(String s) {
            final Matcher matcher = PATTERN.matcher(s);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid input: " + s);
            }
            return new Node(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        private boolean isDestination() {
            return name.endsWith("Z");
        }

        private boolean isStart() {
            return name.endsWith("A");
        }

        @Override
        public String toString() {
            return "%s = (%s, %s)".formatted(name, leftName, rightName);
        }

        public String name() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            return name.equals(node.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        public Stream<PeriodicPath> generateLoops(String directionList, int period) {
            var directionProvider = new DirectionProvider(directionList);
            var node = this;

            Map<Node, List<Long>> destinationSteps = new HashMap<>();
            var stepCount = 0L;
            var loopFound = false;
            while (!loopFound) {
                stepCount++;
                final Direction nextMove = directionProvider.next();
                node = node.go(nextMove);
                if (node.isDestination()) {
                    final List<Long> stepForTheSameNodeInPreviousPaths = destinationSteps.computeIfAbsent(node, n -> new LinkedList<>());
                    for (Long s : stepForTheSameNodeInPreviousPaths) {
                        if ((stepCount - s) % period == 0) {
                            loopFound = true;
                            break;
                        }
                    }
                    stepForTheSameNodeInPreviousPaths.add(stepCount);
                }
            }

            String startName = this.name;
            return destinationSteps.keySet().stream()
                    .flatMap(dest -> destinationSteps.get(dest).stream()
                            .map(s -> new PeriodicPath(startName, dest.name, s)));
        }
    }

}