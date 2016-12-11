package com.larsostling.adventofcode.dayseven;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DaySevenTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DaySeven();

        assertThat(underTest.solvePartOne("abba[mnop]qrst")).isEqualTo("1");
        assertThat(underTest.solvePartOne("abcd[bddb]xyyx")).isEqualTo("0");
        assertThat(underTest.solvePartOne("aaaa[qwer]tyui")).isEqualTo("0");
        assertThat(underTest.solvePartOne("ioxxoj[asdfgh]zxcvbn")).isEqualTo("1");

        assertThat(underTest.solvePartOne("abba[mnop]qrst\nabcd[bddb]xyyx\naaaa[qwer]tyui\nioxxoj[asdfgh]zxcvbn"))
                .isEqualTo("2");
    }

    @Test
    public void solvePartTwo() {
        Puzzle underTest = new DaySeven();

        assertThat(underTest.solvePartTwo("aba[bab]xyz")).isEqualTo("1");
        assertThat(underTest.solvePartTwo("xyx[xyx]xyx")).isEqualTo("0");
        assertThat(underTest.solvePartTwo("aaa[kek]eke")).isEqualTo("1");
        assertThat(underTest.solvePartTwo("zazbz[bzb]cdb")).isEqualTo("1");

        assertThat(underTest.solvePartTwo("aba[bab]xyz\nxyx[xyx]xyx\naaa[kek]eke\nzazbz[bzb]cdb")).isEqualTo("3");
    }
}
