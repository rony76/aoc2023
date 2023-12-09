package org.nalda.adventofcode2023.navigation;

import org.junit.jupiter.api.Test;
import org.nalda.adventofcode2023.ResourceUtil;
import org.nalda.adventofcode2023.navigation.Navigation.Direction;
import org.nalda.adventofcode2023.navigation.Navigation.PeriodicPath;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class NavigationTest {
    @Test
    void acceptanceWithPeriodicPaths() {
        var input = ResourceUtil.getLineList("navigation-acceptance.txt");
        final Navigation navigation = new Navigation();

        var paths = navigation.findPeriodicPaths(input).collect(Collectors.toList());

        assertThat(paths).hasSize(5);

        assertThat(paths).contains(new PeriodicPath("11A", "11Z", 2));
        assertThat(paths).contains(new PeriodicPath("11A", "11Z", 4));

        assertThat(paths).contains(new PeriodicPath("22A", "22Z", 3));
        assertThat(paths).contains(new PeriodicPath("22A", "22Z", 6));
        assertThat(paths).contains(new PeriodicPath("22A", "22Z", 9));
    }

    @Test
    void directionProviderGeneratesDirections() {
        final Navigation.DirectionProvider directionProvider = new Navigation.DirectionProvider("LRR");

        assertThat(directionProvider.next()).isEqualTo(Direction.LEFT);
        assertThat(directionProvider.next()).isEqualTo(Direction.RIGHT);
        assertThat(directionProvider.next()).isEqualTo(Direction.RIGHT);
        assertThat(directionProvider.next()).isEqualTo(Direction.LEFT);
        assertThat(directionProvider.next()).isEqualTo(Direction.RIGHT);
        assertThat(directionProvider.next()).isEqualTo(Direction.RIGHT);
        assertThat(directionProvider.next()).isEqualTo(Direction.LEFT);
    }
}