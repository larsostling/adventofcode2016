package com.larsostling.adventofcode.dayfifteen;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayFifteenTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayFifteen();

        assertThat(underTest.solvePartOne("Disc #1 has 5 positions; at time=0, it is at position 4.\n" +
                "Disc #2 has 2 positions; at time=0, it is at position 1.")).isEqualTo("5");
    }
}
