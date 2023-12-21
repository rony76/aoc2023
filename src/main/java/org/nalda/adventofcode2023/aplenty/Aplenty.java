package org.nalda.adventofcode2023.aplenty;

import org.nalda.adventofcode2023.ResourceUtil;
import org.nalda.adventofcode2023.Timing;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aplenty {
    private record Rule(Predicate<PartRatings> guard, JumpOrCompleted action) {

        public static Rule fromString(String s) {
            String[] tokens = s.split(":");
            if (tokens.length == 1) {
                JumpOrCompleted action = JumpOrCompleted.fromString(tokens[0]);
                return new Rule(r -> true, action);
            }

            char part = tokens[0].charAt(0);
            boolean operatorIsLessThan = tokens[0].charAt(1) == '<';
            long comparisonValue = Long.parseLong(tokens[0].substring(2));
            LongPredicate predicate = r -> operatorIsLessThan ? r < comparisonValue : r > comparisonValue;


            JumpOrCompleted action = JumpOrCompleted.fromString(tokens[1]);
            return new Rule(pr -> predicate.test(pr.rating(part)), action);
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
                if (rule.guard.test(pr)) {
                    return rule.action;
                }
            }

            throw new IllegalStateException("Unexpected end of rules in " + this);
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
            int index = switch (part) {
                case 'x' -> 0;
                case 'm' -> 1;
                case 'a' -> 2;
                case 's' -> 3;
                default -> throw new IllegalStateException("Unexpected value: " + part);
            };
            return ratings[index];
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
            long sum = new Aplenty().sumAccepted(input);

            System.out.println("Sum of accepted parts: " + sum);
        });
    }
}
