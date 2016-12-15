package com.larsostling.adventofcode.daytwelve;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayTwelveTest {
    @Test
    public void testDayTwelve() {
        Puzzle underTest = new DayTwelve();

        assertThat(underTest.solvePartOne("cpy 41 a\n" +
                "inc a\n" +
                "inc a\n" +
                "dec a\n" +
                "jnz a 2\n" +
                "dec a")).isEqualTo("42");
    }
}
