package com.larsostling.adventofcode.daythirteen;

import com.larsostling.adventofcode.Puzzle;

import java.math.BigInteger;
import java.util.*;

public class DayThirteen implements Puzzle {

    @Override
    public String solvePartOne(String input) {
        int magicNumber = Integer.parseInt(input);

        Position startingPosition = new Position(1, 1, 0);
        Optional<Position> finalPosition = Optional.empty();
        Set<Position> visitedPositions = new HashSet<>();

        Queue<Position> positionQueue = new ArrayDeque<>();
        positionQueue.add(startingPosition);

        while (!positionQueue.isEmpty()) {
            Position currentPosition = positionQueue.remove();

            if (visitedPositions.add(currentPosition)) {
                for (Position position : currentPosition.newPositions()) {
                    if (position.x == 31 && position.y == 39) {
                        finalPosition = Optional.of(position);
                        break;
                    } else if (position.isOpenSpace(magicNumber) && !visitedPositions.contains(position)) {
                        positionQueue.add(position);
                    }
                }
            }
        }

        return String.valueOf(finalPosition.get().previousPositionCount);
    }

    @Override
    public String solvePartTwo(String input) {
        int magicNumber = Integer.parseInt(input);

        Position startingPosition = new Position(1, 1, 0);
        Set<Position> visitedPositions = new HashSet<>();

        Queue<Position> positionQueue = new ArrayDeque<>();
        positionQueue.add(startingPosition);

        while (!positionQueue.isEmpty()) {
            Position currentPosition = positionQueue.remove();

            if (visitedPositions.add(currentPosition) && currentPosition.previousPositionCount < 50 ) {
                for (Position position : currentPosition.newPositions()) {
                    if (position.isOpenSpace(magicNumber) && !visitedPositions.contains(position)) {
                        positionQueue.add(position);
                    }
                }
            }
        }

        return String.valueOf(visitedPositions.size());
    }

    private static class Position {
        private final int x;
        private final int y;
        private final int previousPositionCount;

        public Position(int x, int y, int previousPositionCount) {
            this.x = x;
            this.y = y;
            this.previousPositionCount = previousPositionCount;
        }

        public List<Position> newPositions() {
            List<Position> newPositions = new ArrayList<>();
            newPositions.add(new Position(x + 1, y, previousPositionCount + 1));
            newPositions.add(new Position(x, y + 1, previousPositionCount + 1));
            if (x > 0) {
                newPositions.add(new Position(x - 1, y, previousPositionCount + 1));
            }
            if (y > 0) {
                newPositions.add(new Position(x, y - 1, previousPositionCount + 1));
            }
            return newPositions;
        }

        public boolean isOpenSpace(int magicNumber) {
            long number = x * x + 3 * x + 2 * x * y + y + y * y + magicNumber;
            return BigInteger.valueOf(number).toString(2).chars().filter(c -> (char) c == '1').count() % 2 == 0;
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

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
