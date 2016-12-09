package com.larsostling.adventofcode.daysix;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class DaySixTest {

    private static String INPUT = "eedadn\n" +
            "drvtee\n" +
            "eandsr\n" +
            "raavrd\n" +
            "atevrs\n" +
            "tsrnev\n" +
            "sdttsa\n" +
            "rasrtv\n" +
            "nssdts\n" +
            "ntnada\n" +
            "svetve\n" +
            "tesnvt\n" +
            "vntsnd\n" +
            "vrdear\n" +
            "dvrsen\n" +
            "enarar";

    @Test
    public void solvePartOne() {
        Puzzle underTest = new DaySix();

        assertThat(underTest.solvePartOne(INPUT)).isEqualTo("easter");
    }

    @Test
    public void solvePartTwo() {
        Puzzle underTest = new DaySix();

        assertThat(underTest.solvePartTwo(INPUT)).isEqualTo("advent");
    }
}
