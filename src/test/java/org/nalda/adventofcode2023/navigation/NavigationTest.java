package org.nalda.adventofcode2023.navigation;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;

import static org.assertj.core.api.Assertions.assertThat;

class NavigationTest {
    @Test
    void acceptance() {
        var input = ResourceUtil.getLineList("navigation-acceptance.txt");
        final Navigation navigation = new Navigation();

        var stepCount = navigation.countStepsToReachDestination(input);

        assertThat(stepCount).isEqualTo(6L);
    }
}