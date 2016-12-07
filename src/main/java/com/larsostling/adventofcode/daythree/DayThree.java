package com.larsostling.adventofcode.daythree;

import com.larsostling.adventofcode.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayThree implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        List<Triangle> triangles = parseTrianglesByRow(input);
        return String.valueOf(countPossibleTriangles(triangles));
    }

    @Override
    public String solvePartTwo(String input) {
        List<Triangle> triangles = parseTrianglesByColumn(input);
        return String.valueOf(countPossibleTriangles(triangles));
    }

    private static List<Triangle> parseTrianglesByRow(String input) {
        return Arrays.stream(input.split("\n"))
                .map(DayThree::parseSingleTriangle)
                .collect(Collectors.toList());
    }

    private static Triangle parseSingleTriangle(String input) {
        String[] parts = input.trim().split(" +");
        return new Triangle(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    private static List<Triangle> parseTrianglesByColumn(String input) {
        List<Triangle> triangles = new ArrayList<>();

        String[] lines = input.split("\n");
        for (int i = 0; i < lines.length ; i += 3) {
            String[] lineOne = lines[i].trim().split(" +");
            String[] lineTwo = lines[i + 1].trim().split(" +");
            String[] lineThree = lines[i + 2].trim().split(" +");

            triangles.add(new Triangle(Integer.parseInt(lineOne[0]), Integer.parseInt(lineTwo[0]),
                    Integer.parseInt(lineThree[0])));
            triangles.add(new Triangle(Integer.parseInt(lineOne[1]), Integer.parseInt(lineTwo[1]),
                    Integer.parseInt(lineThree[1])));
            triangles.add(new Triangle(Integer.parseInt(lineOne[2]), Integer.parseInt(lineTwo[2]),
                    Integer.parseInt(lineThree[2])));
        }

        return triangles;
    }

    private long countPossibleTriangles(List<Triangle> triangles) {
        return triangles.stream()
                .filter(Triangle::isPossible)
                .count();
    }

    private static class Triangle {
        private final int a;
        private final int b;
        private final int c;

        public Triangle(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public boolean isPossible() {
            return a + b > c && b + c > a && c + a > b;
        }
    }
}
