package com.larsostling.adventofcode.daytwo;

import com.larsostling.adventofcode.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class DayTwo implements Puzzle {

    private static char[][] KEY_PAD_ONE = new char[][]{
            { 0, 0, 0, 0, 0 },
            { 0, '1', '2', '3', 0 },
            { 0, '4', '5', '6', 0 },
            { 0, '7', '8', '9', 0 },
            { 0, 0, 0, 0, 0 },
    };

    private static char[][] KEY_PAD_TWO = new char[][]{
            { 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, '1', 0, 0, 0 },
            { 0, 0, '2', '3', '4', 0, 0 },
            { 0, '5', '6', '7', '8', '9', 0 },
            { 0, 0, 'A', 'B', 'C', 0, 0 },
            { 0, 0, 0, 'D', 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0 }
    };


    @Override
    public String solvePartOne(String input) {
        return solvePuzzle(KEY_PAD_ONE, new Position(2, 2), input);
    }

    @Override
    public String solvePartTwo(String input) {
        return solvePuzzle(KEY_PAD_TWO, new Position(3, 1), input);
    }

    private static String solvePuzzle(char[][] keyPad, Position startPosition, String input) {
        StringBuilder resultBuilder = new StringBuilder();

        Position position = startPosition;
        for (String line : input.split("\n")) {
            List<Direction> directions = parseInputLine(line);

            for (Direction direction : directions) {
                position = moveIfPossible(keyPad, position, direction);
            }

            resultBuilder.append(keyPad[position.row][position.column]);
        }

        return resultBuilder.toString();
    }

    private static List<Direction> parseInputLine(String line) {
        List<Direction> instructions = new ArrayList<>();

        for (char c : line.toCharArray()) {
            instructions.add(Direction.fromChar(c));
        }

        return instructions;
    }

    private static Position moveIfPossible(char[][] keyPad, Position position, Direction direction) {
        Position newPosition = movePosition(position, direction);

        if (keyPad[newPosition.row][newPosition.column] > 0) {
            return newPosition;
        } else {
            return position;
        }
    }

    private static Position movePosition(Position position, Direction direction) {
        switch(direction) {
            case UP:
                return new Position(position.row - 1, position.column);
            case DOWN:
                return new Position(position.row + 1, position.column);
            case LEFT:
                return new Position(position.row, position.column - 1);
            case RIGHT:
                return new Position(position.row, position.column + 1);
            default:
                throw new IllegalArgumentException("Unknown direction");
        }
    }

    private static class Position {
        private final int row;
        private final int column;

        private Position(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public static Direction fromChar(char c) {
            switch(c) {
                case 'U':
                    return UP;
                case 'D':
                    return DOWN;
                case 'L':
                    return LEFT;
                case 'R':
                    return RIGHT;
                default:
                    throw new IllegalArgumentException("Unknown direction");
            }
        }
    }
}
