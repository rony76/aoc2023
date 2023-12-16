package org.nalda.adventofcode2023.reflector;

import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

public class Reflectors {

    public static void main(String[] args) {
        final List<String> input = ResourceUtil.getLineList("reflector-input.txt");
        Reflector reflector = Reflector.fromStrings(input);

        var northWeight = reflector.findNorthWeight();

        System.out.println("North weight, before cycles: " + northWeight);

        reflector = reflector.rotateABillionTimes();
        if (reflector == null) return;

        System.out.println("Reflector after cycles:" + reflector);

        northWeight = reflector.findNorthWeight();

        System.out.println("North weight, after cycles: " + northWeight);
    }

}
