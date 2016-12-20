package com.larsostling.adventofcode.daytwenty;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayTwentyTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayTwenty();

        assertThat(underTest.solvePartOne("5-8\n" +
                "0-2\n" +
                "4-7")).isEqualTo("3");
    }

    @Test
    public void solvePartTwo() {
        Puzzle underTest = new DayTwenty();

        assertThat(underTest.solvePartTwo("5-8\n" +
                "0-2\n" +
                "4-7")).isEqualTo("4294967288");
    }
}
