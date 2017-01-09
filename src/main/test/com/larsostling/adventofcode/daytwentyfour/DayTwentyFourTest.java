package com.larsostling.adventofcode.daytwentyfour;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayTwentyFourTest {

    private static String TEST_MAP = "###########\n" +
            "#0.1.....2#\n" +
            "#.#######.#\n" +
            "#4.......3#\n" +
            "###########";

    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayTwentyFour();

        assertThat(underTest.solvePartOne(TEST_MAP)).isEqualTo("14");
    }

    @Test
    public void solvePartTwo() {
        Puzzle underTest = new DayTwentyFour();

        assertThat(underTest.solvePartTwo(TEST_MAP)).isEqualTo("20");
    }
}
