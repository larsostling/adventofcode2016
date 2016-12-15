package com.larsostling.adventofcode.dayeleven;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayElevenTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayEleven();

        assertThat(underTest.solvePartOne(
                "The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.\n" +
                        "The second floor contains a hydrogen generator.\n" +
                        "The third floor contains a lithium generator.\n" +
                        "The fourth floor contains nothing relevant.")).isEqualTo("11");
    }
}
