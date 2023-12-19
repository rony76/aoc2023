package org.nalda.adventofcode2023.trench;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TrenchTest {
    @Test
    void acceptance() {
        final List<Trench.DigPlanEntry> entries = ResourceUtil.getLineStream("trench-acceptance.txt")
                .map(Trench.DigPlanEntry::fromString)
                .collect(Collectors.toList());
        final Trench trench = Trench.fromEntries(entries);

        long volume = trench.calculateLakeVolume(entries);

        assertThat(volume).isEqualTo(62);

    }
}
