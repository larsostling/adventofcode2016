package com.larsostling.adventofcode.dayfive;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayFiveTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayFive();

        assertThat(underTest.solvePartOne("abc")).isEqualTo("18f47a30");
    }

    @Test
    public void solvePartTwo() {
        Puzzle underTest = new DayFive();

        assertThat(underTest.solvePartTwo("abc")).isEqualTo("05ace8e3");
    }
}
