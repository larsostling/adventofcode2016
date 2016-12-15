package com.larsostling.adventofcode.daythirteen;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayThirteenTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayThirteen();

        assertThat(underTest.solvePartOne("1352")).isEqualTo("90");
    }
}
