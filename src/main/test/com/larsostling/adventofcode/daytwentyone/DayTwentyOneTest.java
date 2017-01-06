package com.larsostling.adventofcode.daytwentyone;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayTwentyOneTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayTwentyOne();

        assertThat(underTest.solvePartOne("swap position 4 with position 0")).isEqualTo("ebcdafgh");
        assertThat(underTest.solvePartOne("swap letter d with letter b")).isEqualTo("adcbefgh");
        assertThat(underTest.solvePartOne("reverse positions 0 through 4")).isEqualTo("edcbafgh");
        assertThat(underTest.solvePartOne("rotate left 1 step")).isEqualTo("bcdefgha");
        assertThat(underTest.solvePartOne("move position 1 to position 4")).isEqualTo("acdebfgh");
        assertThat(underTest.solvePartOne("move position 3 to position 0")).isEqualTo("dabcefgh");
        assertThat(underTest.solvePartOne("rotate based on position of letter b")).isEqualTo("ghabcdef");
    }

    @Test
    public void solvePartTwo() {
        Puzzle underTest = new DayTwentyOne();

        assertThat(underTest.solvePartTwo("swap position 4 with position 0")).isEqualTo("cbgdfeah");
        assertThat(underTest.solvePartTwo("swap letter d with letter b")).isEqualTo("fdgbceah");
        assertThat(underTest.solvePartTwo("reverse positions 0 through 4")).isEqualTo("cdgbfeah");
        assertThat(underTest.solvePartTwo("rotate left 1 step")).isEqualTo("hfbgdcea");
        assertThat(underTest.solvePartTwo("move position 1 to position 4")).isEqualTo("fcbgdeah");
        assertThat(underTest.solvePartTwo("move position 3 to position 0")).isEqualTo("bgdfceah");
        assertThat(underTest.solvePartTwo("rotate based on position of letter b")).isEqualTo("bgdceahf");
    }
}
