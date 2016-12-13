package com.larsostling.adventofcode.dayten;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayTenTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayTen();

        assertThat(underTest.solvePartOne("value 61 goes to bot 2\n"
                + "bot 2 gives low to bot 1 and high to bot 0\n"
                + "value 45 goes to bot 1\n"
                + "bot 1 gives low to output 1 and high to bot 0\n"
                + "bot 0 gives low to output 2 and high to output 0\n"
                + "value 17 goes to bot 2")).isEqualTo("2");
    }
}
