package org.nalda.adventofcode2023.mirrors;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

public class Mirrors {
    public long findMirrorsAndSum(List<String> input) {
        List<Mirror> mirrors = parseMirrors(input);
        return mirrors.stream()
                .mapToLong(Mirror::getValue)
                .sum();
    }

    private static List<Mirror> parseMirrors(List<String> input) {
        List<Mirror> result = new ArrayList<>();

        int matrixStart = 0;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).trim().equals("")) {
                result.add(Mirror.fromStringList(input.subList(matrixStart, i)));
                matrixStart = i + 1;
            }
        }

        if (matrixStart < input.size()) {
            result.add(Mirror.fromStringList(input.subList(matrixStart, input.size())));
        }
        return result;
    }

    public static void main(String[] args) {
        final List<String> input = ResourceUtil.getLineList("mirrors-input.txt");

        long sum = new Mirrors().findMirrorsAndSum(input);

        System.out.println("Sum of mirror values is: " + sum);
    }
}
