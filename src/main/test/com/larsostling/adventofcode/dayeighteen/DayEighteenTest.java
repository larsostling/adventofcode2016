package com.larsostling.adventofcode.dayeighteen;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayEighteenTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayEighteen();

        assertThat(underTest.solvePartOne(".^^.^.^^^^")).isEqualTo("185");
    }
}
