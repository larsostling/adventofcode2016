package com.larsostling.adventofcode.dayfour;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayFourTest {
    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayFour();

        assertThat(underTest.solvePartOne("aaaaa-bbb-z-y-x-123[abxyz]")).isEqualTo("123");
        assertThat(underTest.solvePartOne("a-b-c-d-e-f-g-h-987[abcde]")).isEqualTo("987");
        assertThat(underTest.solvePartOne("not-a-real-room-404[oarel]")).isEqualTo("404");
        assertThat(underTest.solvePartOne("totally-real-room-200[decoy]")).isEqualTo("0");

        assertThat(underTest.solvePartOne("aaaaa-bbb-z-y-x-123[abxyz]\n" +
                "a-b-c-d-e-f-g-h-987[abcde]\n" +
                "not-a-real-room-404[oarel]\n" +
                "totally-real-room-200[decoy]")).isEqualTo("1514");
    }

    @Test
    public void solvePartTwo() {
        Puzzle underTest = new DayFour();

        assertThat(underTest.solvePartTwo("northpole-object-storage-52[oetra]")).isEqualTo("52");
    }
}
