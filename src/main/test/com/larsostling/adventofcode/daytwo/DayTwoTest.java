package com.larsostling.adventofcode.daytwo;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DayTwoTest {

    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayTwo();

        assertThat(underTest.solvePartOne("ULL\nRRDDD\nLURDL\nUUUUD")).isEqualTo("1985");
    }

    @Test
    public void solvePartTwo() {
        Puzzle underTest = new DayTwo();

        assertThat(underTest.solvePartTwo("ULL\nRRDDD\nLURDL\nUUUUD")).isEqualTo("5DB3");
    }
}
