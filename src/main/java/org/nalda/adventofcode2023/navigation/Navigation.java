package org.nalda.adventofcode2023.navigation;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Navigation {

    public static final String START_NODE_NAME = "AAA";
    public static final String DESTINATION_NODE_NAME = "ZZZ";

    public static void main(String[] args) {
        final Navigation navigation = new Navigation();
        final List<String> input = ResourceUtil.getLineList("navigation-input.txt");

        final long stepCount = navigation.countStepsToReachDestination(input);

        System.out.println("Steps to reach destination: " + stepCount);
    }

    public long countStepsToReachDestination(List<String> input) {
        var directionProvider = createDirectionProvider(input.get(0));
        var stepCount = 0L;

        var graph = input.stream()
                .skip(2)
                .map(Node::parse)
                .collect(Collectors.toMap(n -> n.name, n -> n));

        var currentNode = graph.get(START_NODE_NAME);
        while (!currentNode.isDestination()) {
            stepCount++;
            final Direction nextMove = directionProvider.next();
            final String nextNodeName = nextMove == Direction.LEFT ? currentNode.left : currentNode.right;

            System.out.printf("%s, %s to ", currentNode, nextMove, nextNodeName);
            currentNode = graph.get(nextNodeName);
        }
        return stepCount;
    }

    private DirectionProvider createDirectionProvider(String directionList) {
        return new DirectionProvider(directionList);
    }

    enum Direction {
        LEFT, RIGHT
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

    record Node(String name, String left, String right) {
        static final Pattern PATTERN = Pattern.compile("([A-Z]+) = \\(([A-Z]+),\\s+([A-Z]+)\\)");
        static Node parse(String s) {
            final Matcher matcher = PATTERN.matcher(s);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid input: " + s);
            }
            return new Node(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        private boolean isDestination() {
            return name.equals(DESTINATION_NODE_NAME);
        }

        @Override
        public String toString() {
            return "%s = (%s, %s)".formatted(name, left, right);
        }
    }

}