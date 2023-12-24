package org.nalda.adventofcode2023.pulse;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PulseTest {
    @Test
    void acceptance1() {
        final String inputString = """
                broadcaster -> a, b, c
                %a -> b
                %b -> c
                %c -> inv
                &inv -> a""";
        List<String> input = List.of(inputString.split("\n"));

        Pulse pulse = new Pulse(input);

        long product = pulse.calcPulsesProduct();

        assertThat(product).isEqualTo(32000000L);
    }

    @Test
    void acceptance2() {
        final String inputString = """
                broadcaster -> a
                %a -> inv, con
                &inv -> b
                %b -> con
                &con -> output""";
        List<String> input = List.of(inputString.split("\n"));

        Pulse pulse = new Pulse(input);

        long product = pulse.calcPulsesProduct();

        assertThat(product).isEqualTo(11687500L);
    }
}
