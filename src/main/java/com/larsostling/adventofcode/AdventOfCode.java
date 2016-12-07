package com.larsostling.adventofcode;

import com.larsostling.adventofcode.dayone.DayOne;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdventOfCode {

    private static Map<String, Puzzle> PUZZLE_MAP = initializePuzzleMap();

    private static Map<String, Puzzle> initializePuzzleMap() {
        Map<String, Puzzle> puzzleMap = new HashMap<>();
        puzzleMap.put("1", new DayOne());
        return puzzleMap;
    }

    private static String readInputFromFile(String file) throws IOException {
        Path path = Paths.get(file);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        return lines.stream().collect(Collectors.joining("\n"));
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
