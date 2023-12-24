package org.nalda.adventofcode2023.trench;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TrenchTest {
    @Test
    void acceptanceShort() {
        final List<Trench.DigPlanEntry> entries = ResourceUtil.getLineStream("trench-acceptance.txt")
                .map(Trench.DigPlanEntry::fromString)
                .collect(Collectors.toList());
        final Trench trench = Trench.fromEntries(entries);

        long volume = trench.calculateLakeVolume(entries);

        assertThat(volume).isEqualTo(62);
    }

    @Test
    void acceptanceLong() {
        Stream<String> input = ResourceUtil.getLineStream("trench-acceptance.txt");

        long volume = Trench.calculateLakeVolumeWithLongEntries(input);

        assertThat(volume).isEqualTo(952408144115L);
    }
}
