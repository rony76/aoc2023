package org.nalda.adventofcode2023.engine;

import java.util.List;

record ThreeLines(List<String> threeLines) {
    public String topLine() {
        return threeLines.get(0);
    }

    public String centralLine() {
        return threeLines.get(1);
    }

    public String bottomLine() {
        return threeLines.get(2);
    }

    public int getLineLength() {
        return centralLine().length();
    }

    @Override
    public String toString() {
        return String.join("\n", threeLines);
    }
}
