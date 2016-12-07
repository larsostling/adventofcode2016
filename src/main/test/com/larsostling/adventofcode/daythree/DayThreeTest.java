package com.larsostling.adventofcode.daythree;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayThreeTest {

    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayThree();

        assertThat(underTest.solvePartOne("3 4 5")).isEqualTo("1");
        assertThat(underTest.solvePartOne("5 10 25")).isEqualTo("0");
        assertThat(underTest.solvePartOne("3 4 5\n4 5 6\n5 10 25")).isEqualTo("2");
        assertThat(underTest.solvePartOne("3 4 5\n4 5 6\n5 10 25")).isEqualTo("2");
    }

    @Test
    public void solvePartTwo() {
        Puzzle underTest = new DayThree();

        assertThat(underTest.solvePartTwo("101 301 501\n" +
                "102 302 502\n" +
                "103 303 503\n" +
                "201 401 601\n" +
                "202 402 602\n" +
                "203 403 603")).isEqualTo("6");
    }
}
