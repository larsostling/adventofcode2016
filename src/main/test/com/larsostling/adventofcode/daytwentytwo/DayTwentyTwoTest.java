package com.larsostling.adventofcode.daytwentytwo;

import com.larsostling.adventofcode.Puzzle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayTwentyTwoTest {

    private static String TEST_GRID = "root@ebhq-gridcenter# df -h\n" +
            "Filesystem            Size  Used  Avail  Use%\n" +
            "/dev/grid/node-x0-y0   10T    8T     2T   80%\n" +
            "/dev/grid/node-x0-y1   11T    6T     5T   54%\n" +
            "/dev/grid/node-x0-y2   32T   28T     4T   87%\n" +
            "/dev/grid/node-x1-y0    9T    7T     2T   77%\n" +
            "/dev/grid/node-x1-y1    8T    0T     8T    0%\n" +
            "/dev/grid/node-x1-y2   11T    7T     4T   63%\n" +
            "/dev/grid/node-x2-y0   10T    6T     4T   60%\n" +
            "/dev/grid/node-x2-y1    9T    8T     1T   88%\n" +
            "/dev/grid/node-x2-y2    9T    6T     3T   66%";

    @Test
    public void solvePartOne() {
        Puzzle underTest = new DayTwentyTwo();

        assertThat(underTest.solvePartOne(TEST_GRID)).isEqualTo("7");
    }

    @Test
    public void solvePartTwo() {
        Puzzle underTest = new DayTwentyTwo();

        assertThat(underTest.solvePartTwo(TEST_GRID)).isEqualTo("7");
    }
}
