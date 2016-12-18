package com.larsostling.adventofcode.dayeighteen;

import com.larsostling.adventofcode.Puzzle;

import java.util.stream.Stream;

public class DayEighteen implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        return String.valueOf(Stream.iterate(new Row(input), Row::nextRow)
                .limit(40)
                .mapToInt(Row::countSafeTiles)
                .sum());
    }

    @Override
    public String solvePartTwo(String input) {
        return String.valueOf(Stream.iterate(new Row(input), Row::nextRow)
                .limit(400000)
                .mapToInt(Row::countSafeTiles)
                .sum());
    }

    private static class Row {
        private final String tiles;

        private Row(String tiles) {
            this.tiles = tiles;
        }

        public Row nextRow() {
            char[] newTiles = new char[tiles.length()];
            for (int i = 0; i < tiles.length(); i++) {
                newTiles[i] = getTileAt(i - 1) == getTileAt(i + 1) ? '.' : '^';
            }
            return new Row(new String(newTiles));
        }

        private char getTileAt(int i) {
            if (i < 0 || i >= tiles.length()) {
                return '.';
            } else {
                return tiles.charAt(i);
            }
        }

        public int countSafeTiles() {
            return (int) tiles.chars().filter(ch -> ch == '.').count();
        }
    }
}
