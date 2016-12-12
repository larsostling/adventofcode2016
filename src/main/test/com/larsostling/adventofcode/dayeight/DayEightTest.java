package com.larsostling.adventofcode.dayeight;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayEightTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayEight();

        assertThat(underTest.solvePartOne("rect 3x2\n"
                + "rotate column x=1 by 1\n"
                + "rotate row y=0 by 4\n"
                + "rotate column x=1 by 1")).isEqualTo("6");
    }

}
