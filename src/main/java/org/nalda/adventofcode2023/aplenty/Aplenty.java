package org.nalda.adventofcode2023.aplenty;

import org.nalda.adventofcode2023.ResourceUtil;
import org.nalda.adventofcode2023.Timing;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aplenty {
    private record Rule(String guardString, JumpOrCompleted action) {

        public static Rule fromString(String s) {
            String[] tokens = s.split(":");
            if (tokens.length == 1) {
                JumpOrCompleted action = JumpOrCompleted.fromString(tokens[0]);
                return new Rule("", action);
            }

            JumpOrCompleted action = JumpOrCompleted.fromString(tokens[1]);
            return new Rule(tokens[0], action);
        }


        public PartRatingsRanges[] split(PartRatingsRanges ranges) {
            if (guardString.isBlank()) {
                return new PartRatingsRanges[]{ranges, PartRatingsRanges.empty()};
            }

            char part = guardString.charAt(0);
            boolean operatorIsLessThan = guardString.charAt(1) == '<';
            int comparisonValue = Integer.parseInt(guardString.substring(2));

            Range[] accepted = ranges.ranges.clone();
            Range[] ignored = ranges.ranges.clone();

            int ratingIndex = getRatingIndex(part);
            Range range = accepted[ratingIndex];
            if (operatorIsLessThan) {
                accepted[ratingIndex] = range.withUpperBoundTo(Math.min(range.to, comparisonValue));
                ignored[ratingIndex] = range.withLowerBoundTo(Math.max(range.from, comparisonValue));
            } else {
                accepted[ratingIndex] = range.withLowerBoundTo(Math.max(range.from, comparisonValue + 1));
                ignored[ratingIndex] = range.withUpperBoundTo(Math.min(range.to, comparisonValue + 1));
            }

            return new PartRatingsRanges[]{new PartRatingsRanges(accepted), new PartRatingsRanges(ignored)};
        }

        public boolean test(PartRatings ratings) {
            if (guardString.isBlank()) {
                return true;
            }

            char part = guardString.charAt(0);
            boolean operatorIsLessThan = guardString.charAt(1) == '<';
            long comparisonValue = Integer.parseInt(guardString.substring(2));
            return operatorIsLessThan ?
                    ratings.rating(part) < comparisonValue :
                    ratings.rating(part) > comparisonValue;
        }
    }

    private record Workflow(String name, Rule[] rules) {

        public static Workflow fromLine(String line) {
            line = line.trim();
            line = line.substring(0, line.length() - 1);
            String[] nameAndRules = line.split("\\{");
            String name = nameAndRules[0];
            String[] ruleStrings = nameAndRules[1].split(",");
            Rule[] rules = Arrays.stream(ruleStrings).map(Rule::fromString).toArray(Rule[]::new);
            return new Workflow(name, rules);
        }

        public JumpOrCompleted process(PartRatings pr) {
            for (Rule rule : rules) {
                if (rule.test(pr)) {
                    return rule.action;
                }
            }

            throw new IllegalStateException("Unexpected end of rules in " + this);
        }

        public long countAcceptable(Map<String, Workflow> workflowMap, PartRatingsRanges partRatingsRanges) {
            var toBeProcessed = partRatingsRanges;
            long result = 0;
            for (Rule rule : rules) {
                PartRatingsRanges[] splitRanges = rule.split(toBeProcessed);
                PartRatingsRanges acceptedRanges = splitRanges[0];
                if (rule.action.jump != null) {
                    Workflow workflow = workflowMap.get(rule.action.jump);
                    result += workflow.countAcceptable(workflowMap, acceptedRanges);
                } else {
                    if (rule.action.accepted) {
                        result += acceptedRanges.count();
                    }
                }
                toBeProcessed = splitRanges[1];
            }

            return result;
        }
    }

    private record PartRatings(long[] ratings) {

        public static PartRatings fromLine(String s) {
            long[] ratings = Arrays.stream(s.split(","))
                    .map(r -> r.split("="))
                    .map(r -> r[1])
                    .mapToLong(Long::parseLong)
                    .toArray();

            return new PartRatings(ratings);
        }

        public long sumRatings() {
            return Arrays.stream(ratings).sum();
        }

        public long rating(char part) {
            return ratings[getRatingIndex(part)];
        }

    }

    private static int getRatingIndex(char part) {
        return switch (part) {
            case 'x' -> 0;
            case 'm' -> 1;
            case 'a' -> 2;
            case 's' -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + part);
        };
    }

    private record JumpOrCompleted(String jump, boolean accepted) {

        public static JumpOrCompleted fromString(String s) {
            if (s.equals("A")) {
                return new JumpOrCompleted(null, true);
            }
            if (s.equals("R")) {
                return new JumpOrCompleted(null, false);
            }
            return new JumpOrCompleted(s, true);
        }
    }

    private record Range(int from, int to) {
        private static Range empty;

        public static Range empty() {
            if (empty == null) {
                empty = new Range(0, 0);
            }
            return empty;
        }

        public long size() {
            return to - from;
        }

        public Range withUpperBoundTo(int newUpperBound) {
            return new Range(from, newUpperBound);
        }

        public Range withLowerBoundTo(int newLowerBound) {
            return new Range(newLowerBound, to);
        }

        @Override
        public String toString() {
            return "[%d, %d)".formatted(from, to);
        }
    }

    private record PartRatingsRanges(Range[] ranges) {

        public long count() {
            return Arrays.stream(ranges)
                    .mapToLong(Range::size)
                    .reduce(1, (a, b) -> a * b);
        }

        private static PartRatingsRanges empty;

        static PartRatingsRanges empty() {
            if (empty == null) {
                empty = new PartRatingsRanges(new Range[]{Range.empty(), Range.empty(), Range.empty(), Range.empty()});
            }
            return empty;
        }

        @Override
        public String toString() {
            return "{x=%s, m=%s, a=%s, s=%s}".formatted(ranges[0], ranges[1], ranges[2], ranges[3]);
        }
    }

    private static Stream<PartRatings> parseRatings(List<String> partRatingsLines) {
        return partRatingsLines.stream()
                .map(s -> s.substring(1, s.length() - 1))
                .map(PartRatings::fromLine);
    }

    private static int findSeparatorLineIndex(List<String> input) {
        int i = 0;
        while (i < input.size()) {
            String line = input.get(i);
            if (line.isBlank()) {
                break;
            }
            i++;
        }
        return i;
    }

    private static Map<String, Workflow> parseWorkflows(List<String> workflowLines) {
        return workflowLines.stream()
                .map(Workflow::fromLine)
                .collect(Collectors.toMap(Workflow::name, Function.identity()));
    }

    public long sumAccepted(List<String> input) {
        int idx = findSeparatorLineIndex(input);

        List<String> workflowLines = input.subList(0, idx);
        List<String> partRatingsLines = input.subList(idx + 1, input.size());

        Map<String, Workflow> workflowMap = parseWorkflows(workflowLines);
        Stream<PartRatings> ratingsStream = parseRatings(partRatingsLines);

        return ratingsStream
                .filter(pr -> accept(workflowMap, pr))
                .mapToLong(PartRatings::sumRatings)
                .sum();
    }

    public long countAcceptable(List<String> input) {
        Map<String, Workflow> workflowMap = loadWorkflowMap(input);

        Workflow workflow = workflowMap.get("in");
        Range[] fullRanges = initFullRanges();
        return workflow.countAcceptable(workflowMap, new PartRatingsRanges(fullRanges));
    }

    private static Range[] initFullRanges() {
        Range[] fullRanges = new Range[4];
        Arrays.fill(fullRanges, new Range(1, 4001));
        return fullRanges;
    }

    private static Map<String, Workflow> loadWorkflowMap(List<String> input) {
        int idx = findSeparatorLineIndex(input);

        List<String> workflowLines = input.subList(0, idx);

        return parseWorkflows(workflowLines);
    }

    private boolean accept(Map<String, Workflow> workflowMap, PartRatings pr) {
        Workflow workflow = workflowMap.get("in");
        while (true) {
            JumpOrCompleted joc = workflow.process(pr);
            if (joc.jump != null) {
                workflow = workflowMap.get(joc.jump);
            } else {
                return joc.accepted;
            }
        }
    }

    public static void main(String[] args) {
        Timing.runAndTrack(() -> {
            List<String> input = ResourceUtil.getLineList("aplenty-input.txt");
            Aplenty aplenty = new Aplenty();

            long sum = aplenty.sumAccepted(input);

            System.out.println("Sum of accepted parts: " + sum);

            long count = aplenty.countAcceptable(input);

            System.out.println("Combination count: " + count);
        });
    }
}
