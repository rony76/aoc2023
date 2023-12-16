package org.nalda.adventofcode2023.springs;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.nalda.adventofcode2023.springs.SpringCondition.*;

class SpringRecordParserTest {
    @Test
    void canParseAcceptanceSample1() {
        final String input = "???.### 1,1,3";

        final DamagedSpringRecord record = new SpringRecordParser().parse(input);

        assertThat(record.getLine()).isEqualTo("???.###");
        assertThat(record.getGroups()).containsExactly(1, 1, 3);
    }
}