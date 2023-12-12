package org.nalda.adventofcode2023.springs;

import java.util.Arrays;

public class SpringRecordParser {
    public DamagedSpringRecord parse(String s) {
        final String[] conditionAndGroups = s.split(" ");
        final String condition = conditionAndGroups[0];
        final int[] groups = Arrays.stream(conditionAndGroups[1].split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
        return new DamagedSpringRecord(SpringCondition.getSpringConditions(condition), groups);
    }
}
