package com.larsostling.adventofcode.dayseventeen;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DaySeventeenTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DaySeventeen();

        assertThat(underTest.solvePartOne("ihgpwlah")).isEqualTo("DDRRRD");
        assertThat(underTest.solvePartOne("kglvqrro")).isEqualTo("DDUDRLRRUDRD");
        assertThat(underTest.solvePartOne("ulqzkmiv")).isEqualTo("DRURDRUDDLLDLUURRDULRLDUUDDDRR");
    }

    @Test
    public void solvePartTwo() {
        Puzzle underTest = new DaySeventeen();

        assertThat(underTest.solvePartTwo("ihgpwlah")).isEqualTo("370");
        assertThat(underTest.solvePartTwo("kglvqrro")).isEqualTo("492");
        assertThat(underTest.solvePartTwo("ulqzkmiv")).isEqualTo("830");
    }
}
