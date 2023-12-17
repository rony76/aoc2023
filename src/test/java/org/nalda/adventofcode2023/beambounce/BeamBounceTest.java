package org.nalda.adventofcode2023.beambounce;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BeamBounceTest {
    @Test
    void acceptanceStar1() {
        final List<String> input = ResourceUtil.getLineList("beam-bounce-acceptance.txt");
        final BeamBounce beamBounce = new BeamBounce(input);

        long energised = beamBounce.countEnergised();

        assertThat(energised).isEqualTo(46);
    }

    @Test
    void acceptanceStar2() {
        final List<String> input = ResourceUtil.getLineList("beam-bounce-acceptance.txt");
        final BeamBounce beamBounce = new BeamBounce(input);

        long energised = beamBounce.countMaxEnergised();

        assertThat(energised).isEqualTo(51);
    }
}
