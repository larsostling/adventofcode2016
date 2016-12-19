package com.larsostling.adventofcode.daynineteen;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayNineteenTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayNineteen();

        assertThat(underTest.solvePartOne("5")).isEqualTo("3");
    }

    @Test
    public void solvePartTwo() {
        Puzzle underTest = new DayNineteen();

        assertThat(underTest.solvePartTwo("5")).isEqualTo("2");
    }

}
