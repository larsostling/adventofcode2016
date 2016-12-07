package com.larsostling.adventofcode.dayone;

import com.larsostling.adventofcode.Puzzle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DayOne implements Puzzle {

    public String solvePartOne(String input) {
        return String.valueOf(calculateShortestDistanceToHQ(input, false));
    }

    public String solvePartTwo(String input) {
        return String.valueOf(calculateShortestDistanceToHQ(input, true));
    }

    private static int calculateShortestDistanceToHQ(String input, boolean stopAtFirstCross) {
        List<Instruction> instructions = parseInstructions(input);
        Position position = new Position(0, 0);
        Orientation orientation = Orientation.NORTH;

        Collection<Position> visitedPositions = new ArrayList<>();
        for (Instruction instruction : instructions) {
            orientation = turn(orientation, instruction.direction);

            for (int i = 0; i < instruction.steps; i++) {
                position = move(position, orientation);

                if (stopAtFirstCross && visitedPositions.contains(position)) {
                    return getDistanceFromPosition(position);
                }

                visitedPositions.add(position);
            }
        }

        return getDistanceFromPosition(position);
    }

    private static int getDistanceFromPosition(Position position) {
        return Math.abs(position.x) + Math.abs(position.y);
    }

    private static Orientation turn(Orientation orientation, Direction direction) {
        switch (direction) {
            case LEFT:
                return orientation.turnLeft();
            case RIGHT:
                return orientation.turnRight();
            default:
                throw new IllegalArgumentException("Unknown direction");
        }
    }

    private static Position move(Position position, Orientation orientation) {
        switch(orientation) {
            case NORTH:
                return new Position(position.x, position.y + 1);
            case EAST:
                return new Position(position.x + 1, position.y);
            case SOUTH:
                return new Position(position.x, position.y - 1);
            case WEST:
                return new Position(position.x - 1, position.y);
            default:
                throw new IllegalArgumentException("Unknown orientation");
        }
    }

    private static List<Instruction> parseInstructions(String input) {
        List<Instruction> result = new ArrayList<>();
        String[] instructions = input.split(", ");
        for (String instruction : instructions) {
            Direction direction = Direction.fromChar(instruction.charAt(0));
            int steps = Integer.parseInt(instruction.substring(1));
            result.add(new Instruction(direction, steps));
        }
        return result;
    }

    private static class Position {
        private final int x;
        private final int y;

        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            if (x != position.x) return false;
            if (y != position.y) return false;

            return true;
        }
    }

    private static class Instruction {
        private final Direction direction;
        private final int steps;

        private Instruction(Direction direction, int steps) {
            this.direction = direction;
            this.steps = steps;
        }
    }

    private enum Direction {
        LEFT, RIGHT;

        private static Direction fromChar(char c) {
            switch(c) {
                case 'L':
                    return LEFT;
                case 'R':
                    return RIGHT;
                default:
                    throw new IllegalArgumentException("Unknown direction");
            }
        }
    }

    private enum Orientation {
        NORTH, EAST, SOUTH, WEST;

        public Orientation turnLeft() {
            switch (this) {
                case NORTH:
                    return WEST;
                case WEST:
                    return SOUTH;
                case SOUTH:
                    return EAST;
                case EAST:
                    return NORTH;
                default:
                    throw new IllegalArgumentException("Unknown orientation");
            }
        }

        public Orientation turnRight() {
            switch (this) {
                case NORTH:
                    return EAST;
                case EAST:
                    return SOUTH;
                case SOUTH:
                    return WEST;
                case WEST:
                    return NORTH;
                default:
                    throw new IllegalArgumentException("Unknown orientation");
            }
        }
    }
}
