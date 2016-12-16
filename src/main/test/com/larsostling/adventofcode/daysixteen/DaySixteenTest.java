package com.larsostling.adventofcode.daysixteen;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DaySixteenTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DaySixteen();

        assertThat(underTest.solvePartOne("10000")).isEqualTo("11010011110011010");
    }
}
