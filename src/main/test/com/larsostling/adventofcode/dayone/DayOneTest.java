package com.larsostling.adventofcode.dayone;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayOneTest {
    @Test
    public void solvePartOne() {
        assertThat(new DayOne().solvePartOne("R2, L3")).isEqualTo("5");
        assertThat(new DayOne().solvePartOne("R2, R2, R2")).isEqualTo("2");
        assertThat(new DayOne().solvePartOne("R5, L5, R5, R3")).isEqualTo("12");
    }

    @Test
    public void solvePartTwo() {
        assertThat(new DayOne().solvePartTwo("R8, R4, R4, R8")).isEqualTo("4");
    }
}
