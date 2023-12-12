package org.nalda.adventofcode2023.springs;

enum SpringCondition {
    OPERATIONAL, DAMAGED, UNKNOWN;

    public static SpringCondition fromChar(char c) {
        switch (c) {
            case '#':
                return DAMAGED;
            case '.':
                return OPERATIONAL;
            case '?':
                return UNKNOWN;
            default:
                throw new IllegalArgumentException("Unknown spring condition: " + c);
        }
    }

    public static SpringCondition[] getSpringConditions(String condition) {
        return condition.chars()
                .mapToObj(c -> fromChar((char) c))
                .toArray(SpringCondition[]::new);
    }
}
