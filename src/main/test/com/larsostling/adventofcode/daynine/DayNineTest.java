package com.larsostling.adventofcode.daynine;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayNineTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayNine();

        assertThat(underTest.solvePartOne("ADVENT")).isEqualTo("6");
        assertThat(underTest.solvePartOne("A(1x5)BC")).isEqualTo("7");
        assertThat(underTest.solvePartOne("(3x3)XYZ")).isEqualTo("9");
        assertThat(underTest.solvePartOne("A(2x2)BCD(2x2)EFG")).isEqualTo("11");
        assertThat(underTest.solvePartOne("(6x1)(1x3)A")).isEqualTo("6");
        assertThat(underTest.solvePartOne("X(8x2)(3x3)ABCY")).isEqualTo("18");
    }

    @Test
    public void solvePartTwo() {
        Puzzle underTest = new DayNine();

        assertThat(underTest.solvePartTwo("(3x3)XYZ")).isEqualTo("9");
        assertThat(underTest.solvePartTwo("X(8x2)(3x3)ABCY")).isEqualTo("20");
        assertThat(underTest.solvePartTwo("(27x12)(20x12)(13x14)(7x10)(1x12)A")).isEqualTo("241920");
        assertThat(underTest.solvePartTwo("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")).isEqualTo("445");
    }
}
