package com.larsostling.adventofcode;

import com.larsostling.adventofcode.dayeight.DayEight;
import com.larsostling.adventofcode.dayeighteen.DayEighteen;
import com.larsostling.adventofcode.dayeleven.DayEleven;
import com.larsostling.adventofcode.dayfifteen.DayFifteen;
import com.larsostling.adventofcode.dayfive.DayFive;
import com.larsostling.adventofcode.dayfour.DayFour;
import com.larsostling.adventofcode.dayfourteen.DayFourteen;
import com.larsostling.adventofcode.daynine.DayNine;
import com.larsostling.adventofcode.daynineteen.DayNineteen;
import com.larsostling.adventofcode.dayone.DayOne;
import com.larsostling.adventofcode.dayseven.DaySeven;
import com.larsostling.adventofcode.dayseventeen.DaySeventeen;
import com.larsostling.adventofcode.daysix.DaySix;
import com.larsostling.adventofcode.daysixteen.DaySixteen;
import com.larsostling.adventofcode.dayten.DayTen;
import com.larsostling.adventofcode.daythirteen.DayThirteen;
import com.larsostling.adventofcode.daythree.DayThree;
import com.larsostling.adventofcode.daytwelve.DayTwelve;
import com.larsostling.adventofcode.daytwenty.DayTwenty;
import com.larsostling.adventofcode.daytwentyfive.DayTwentyFive;
import com.larsostling.adventofcode.daytwentyfour.DayTwentyFour;
import com.larsostling.adventofcode.daytwentyone.DayTwentyOne;
import com.larsostling.adventofcode.daytwentythree.DayTwentyThree;
import com.larsostling.adventofcode.daytwentytwo.DayTwentyTwo;
import com.larsostling.adventofcode.daytwo.DayTwo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AdventOfCode {

    private static Map<String, Puzzle> PUZZLE_MAP = initializePuzzleMap();

    private static Map<String, Puzzle> initializePuzzleMap() {
        Map<String, Puzzle> puzzleMap = new HashMap<>();
        puzzleMap.put("1", new DayOne());
        puzzleMap.put("2", new DayTwo());
        puzzleMap.put("3", new DayThree());
        puzzleMap.put("4", new DayFour());
        puzzleMap.put("5", new DayFive());
        puzzleMap.put("6", new DaySix());
        puzzleMap.put("7", new DaySeven());
        puzzleMap.put("8", new DayEight());
        puzzleMap.put("9", new DayNine());
        puzzleMap.put("10", new DayTen());
        puzzleMap.put("11", new DayEleven());
        puzzleMap.put("12", new DayTwelve());
        puzzleMap.put("13", new DayThirteen());
        puzzleMap.put("14", new DayFourteen());
        puzzleMap.put("15", new DayFifteen());
        puzzleMap.put("16", new DaySixteen());
        puzzleMap.put("17", new DaySeventeen());
        puzzleMap.put("18", new DayEighteen());
        puzzleMap.put("19", new DayNineteen());
        puzzleMap.put("20", new DayTwenty());
        puzzleMap.put("21", new DayTwentyOne());
        puzzleMap.put("22", new DayTwentyTwo());
        puzzleMap.put("23", new DayTwentyThree());
        puzzleMap.put("24", new DayTwentyFour());
        puzzleMap.put("25", new DayTwentyFive());
        return puzzleMap;
    }

    private static String readInputFromFile(String file) throws IOException {
        Path path = Paths.get(file);
        return Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n"));
    }

    public static void main(String... args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java -jar AdventOfCode <puzzleNo> <inputFile>");
        } else {
            String dayNo = args[0];
            Puzzle puzzle = PUZZLE_MAP.get(dayNo);
            String input = readInputFromFile(args[1]);
            System.out.println("Day " + dayNo + ", part one: " + puzzle.solvePartOne(input));
            System.out.println("Day " + dayNo + ", part two: " + puzzle.solvePartTwo(input));
        }
    }
}
