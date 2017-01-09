package com.larsostling.adventofcode.daytwentyfour;

import com.larsostling.adventofcode.Puzzle;

import java.util.*;

public class DayTwentyFour implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        return solvePuzzle(input, false);
    }

    @Override
    public String solvePartTwo(String input) {
        return solvePuzzle(input, true);
    }

    private static String solvePuzzle(String input, boolean returnToStart) {
        DuctMap ductMap = parseDuctMap(input);
        int pointOfInterestCount = ductMap.getPointOfInterestCount();
        Location startingLocation = ductMap.getStartingLocation();
        Position startingPosition = new Position(ductMap, startingLocation, Optional.empty());
        Optional<Position> finalPosition = Optional.empty();

        Set<PositionKey> visitedPositionKeys = new HashSet<>();
        Queue<Position> positionQueue = new ArrayDeque<>();
        positionQueue.add(startingPosition);

        while (!finalPosition.isPresent() && !positionQueue.isEmpty()) {
            Position currentPosition = positionQueue.remove();

            if (visitedPositionKeys.add(currentPosition.positionKey)) {
                for (Position newPosition : currentPosition.newPositions()) {
                    if (newPosition.getVisitedPointsOfInterest().size() >= pointOfInterestCount
                            && (!returnToStart || newPosition.location.c == '0')) {
                        finalPosition = Optional.of(newPosition);
                    } else if (!visitedPositionKeys.contains(newPosition.positionKey)) {
                        positionQueue.add(newPosition);
                    }
                }
            }
        }

        return String.valueOf(finalPosition.get().getPreviousPositionCount());
    }

    private static DuctMap parseDuctMap(String input) {
        String[] rows = input.split("\n");
        Location[][] locations = new Location[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            locations[i] = parseRow(i, rows[i]);
        }
        return new DuctMap(locations);
    }

    private static Location[] parseRow(int rowNumber, String row) {
        char[] chars = row.toCharArray();
        Location[] locations = new Location[chars.length];
        for (int i = 0; i < chars.length; i++) {
            locations[i] = new Location(rowNumber, i, chars[i]);
        }
        return locations;
    }

    private static class Location {
        private final int x;
        private final int y;
        private final char c;

        public Location(int x, int y, char c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }

        public boolean isPointOfInterest() {
            return Character.isDigit(c);
        }

        public boolean isOpenSpace() {
            return c != '#';
        }
    }

    private static class DuctMap {
        private final Location[][] locations;

        private DuctMap(Location[][] locations) {
            this.locations = locations;
        }

        public Location getLocation(int x, int y) {
            return locations[x][y];
        }

        public Location getStartingLocation() {
            for (int i = 0; i < locations.length; i++) {
                for (int j = 0; j < locations[i].length; j++) {
                    Location location = locations[i][j];
                    if (location.c == '0') {
                        return location;
                    }
                }
            }
            throw new IllegalStateException("No starting position found");
        }

        public int getPointOfInterestCount() {
            return (int) Arrays.stream(locations)
                    .flatMap(Arrays::stream)
                    .filter(Location::isPointOfInterest)
                    .count();
        }
    }

    private static class Position {
        private final DuctMap ductMap;
        private final Location location;
        private final Optional<Position> previousPosition;
        private final PositionKey positionKey;

        public Position(DuctMap ductMap, Location location, Optional<Position> previousPosition) {
            this.ductMap = ductMap;
            this.location = location;
            this.previousPosition = previousPosition;
            this.positionKey = generatePositionKey();
        }

        private PositionKey generatePositionKey() {
            return new PositionKey(location.x, location.y, getVisitedPointsOfInterest());
        }

        public Set<Integer> getVisitedPointsOfInterest() {
            Set<Integer> visitedPointsOfInterest = new HashSet<>();
            if (previousPosition.isPresent()) {
                visitedPointsOfInterest.addAll(previousPosition.get().getVisitedPointsOfInterest());
            }
            if (location.isPointOfInterest()) {
                visitedPointsOfInterest.add(location.c - '0');
            }
            return visitedPointsOfInterest;
        }

        public int getPreviousPositionCount() {
            return previousPosition.map(p -> p.getPreviousPositionCount() + 1).orElse(0);
        }

        public List<Position> newPositions() {
            List<Position> positions = new ArrayList<>();
            Location newLocation = ductMap.getLocation(location.x - 1, location.y);
            if (shouldVisit(newLocation)) {
                positions.add(new Position(ductMap, newLocation, Optional.of(this)));
            }
            newLocation = ductMap.getLocation(location.x + 1, location.y);
            if (shouldVisit(newLocation)) {
                positions.add(new Position(ductMap, newLocation, Optional.of(this)));
            }
            newLocation = ductMap.getLocation(location.x, location.y - 1);
            if (shouldVisit(newLocation)) {
                positions.add(new Position(ductMap, newLocation, Optional.of(this)));
            }
            newLocation = ductMap.getLocation(location.x, location.y + 1);
            if (shouldVisit(newLocation)) {
                positions.add(new Position(ductMap, newLocation, Optional.of(this)));
            }
            return positions;
        }

        private boolean shouldVisit(Location newLocation) {
            return newLocation.isOpenSpace()
                    && (location.isPointOfInterest() || !isPreviousLocation(newLocation));
        }

        private boolean isPreviousLocation(Location newLocation) {
            return previousPosition
                    .filter(p -> p.location.x == newLocation.x && p.location.y == newLocation.y)
                    .isPresent();
        }
    }

    private static class PositionKey {
        private final int x;
        private final int y;
        private final Set<Integer> collectedPointsOfInterest;

        public PositionKey(int x, int y, Set<Integer> collectedPointsOfInterest) {
            this.x = x;
            this.y = y;
            this.collectedPointsOfInterest = collectedPointsOfInterest;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PositionKey that = (PositionKey) o;

            if (x != that.x) return false;
            if (y != that.y) return false;
            if (!collectedPointsOfInterest.equals(that.collectedPointsOfInterest)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + collectedPointsOfInterest.hashCode();
            return result;
        }
    }
}
