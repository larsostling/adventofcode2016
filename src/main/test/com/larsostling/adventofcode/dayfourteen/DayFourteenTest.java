package com.larsostling.adventofcode.dayfourteen;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayFourteenTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayFourteen();

        assertThat(underTest.solvePartOne("abc")).isEqualTo("22728");
    }

    @Test
    @Ignore
    public void solvePartTwo() {
        Puzzle underTest = new DayFourteen();

        assertThat(underTest.solvePartTwo("abc")).isEqualTo("22551");
    }
}
