package org.nalda.adventofcode2023.springs;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@EqualsAndHashCode
public class DamagedSpringRecord {
    private final String line;
    private final int[] groups;

    public DamagedSpringRecord(String line, int[] groups) {
        this.line = line;
        this.groups = groups;
    }

    @Override
    public String toString() {
        return line + " " + Arrays.toString(groups);
    }

    public long arrangements() {
        final Map<DamagedSpringRecord, Long> cache = new HashMap<>();

        return arrangements(cache);
    }

    public long arrangements(Map<DamagedSpringRecord, Long> cache) {
        var cachedResult = cache.get(this);
        if (cachedResult != null) {
            return cachedResult;
        }

        long result = arrangementsPreMemoize(cache);

        cache.put(this, result);
        return result;
    }

    private long arrangementsPreMemoize(Map<DamagedSpringRecord, Long> cache) {
        if (groups.length == 0) {
            return line.contains("#") ? 0L : 1L;
        }

        if (line.length() < groups[0]) {
            return 0L;
        }

        switch (line.charAt(0)) {
            case '.' -> {
                return new DamagedSpringRecord(line.substring(1), groups).arrangements(cache);
            }
            case '#' -> {
                var sizeBlock = line.substring(0, groups[0]);
                var rest = line.substring(groups[0]);
                if (sizeBlock.contains(".") || rest.startsWith("#")) {
                    return 0L;
                } else {
                    final int[] groupRest = groups.length > 1 ? groupTail() : new int[0];
                    rest = rest.isEmpty() ? "." : "." + rest.substring(1);
                    return new DamagedSpringRecord(rest, groupRest).arrangements(cache);
                }
            }
            case '?' -> {
                var arrangementsWithOp = new DamagedSpringRecord(line.substring(1), groups).arrangements(cache);
                var arrangementsWithDamaged = new DamagedSpringRecord("#" + line.substring(1), groups).arrangements(cache);
                return arrangementsWithOp + arrangementsWithDamaged;
            }
            default -> throw new IllegalStateException("Unexpected value: " + line.charAt(0));
        }
    }

    private int[] groupTail() {
        return Arrays.copyOfRange(this.groups, 1, groups.length);
    }

    public DamagedSpringRecord unfold(int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            if (i > 0) {
                sb.append("?");
            }
            sb.append(line);
        }

        final int[] unfoldedGroups = new int[groups.length * times];
        for (int j = 0; j < times; j++) {
            for (int i = 0; i < groups.length; i++) {
                unfoldedGroups[j * groups.length + i] = groups[i];
            }
        }

        return new DamagedSpringRecord(sb.toString(), unfoldedGroups);
    }
}
