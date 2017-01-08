package com.larsostling.adventofcode.daytwentythree;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayTwentyThreeTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayTwentyThree();

        assertThat(underTest.solvePartOne("cpy 2 a\n" +
                "tgl a\n" +
                "tgl a\n" +
                "tgl a\n" +
                "cpy 1 a\n" +
                "dec a\n" +
                "dec a")).isEqualTo("3");
    }
}
