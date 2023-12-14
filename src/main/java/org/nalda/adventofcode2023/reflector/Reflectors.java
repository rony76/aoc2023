package org.nalda.adventofcode2023.reflector;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

public class Reflectors {
    public static void main(String[] args) {
        final List<String> input = ResourceUtil.getLineList("reflector-input.txt");
        final Reflector reflector = Reflector.fromStrings(input);

        final long northWeight = reflector.findNorthWeight();

        System.out.println("North weight: " + northWeight);

    }
}
