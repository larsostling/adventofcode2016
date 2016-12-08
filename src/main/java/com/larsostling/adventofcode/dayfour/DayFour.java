package com.larsostling.adventofcode.dayfour;

import com.larsostling.adventofcode.Puzzle;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayFour implements Puzzle {

    private static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    @Override
    public String solvePartOne(String input) {
        return String.valueOf(parseRooms(input)
                .filter(Room::isRealRoom)
                .mapToInt(room -> room.roomId)
                .sum());
    }

    @Override
    public String solvePartTwo(String input) {
        return parseRooms(input)
                .filter(Room::isRealRoom)
                .filter(room -> "northpole object storage".equals(room.decryptName()))
                .findFirst()
                .map(room -> String.valueOf(room.roomId))
                .orElse("No match found");
    }

    private static Stream<Room> parseRooms(String input) {
        return Arrays.stream(input.split("\n"))
                .map(DayFour::parseSingleRoom);
    }

    private static Room parseSingleRoom(String input) {
        String letters = input.substring(0, input.lastIndexOf('-' ));
        String metaData = input.substring(input.lastIndexOf('-') + 1);
        int roomId = Integer.parseInt(metaData.substring(0, metaData.indexOf('[' )));
        String checkSum = metaData.substring(metaData.indexOf('[') + 1, metaData.indexOf(']'));
        return new Room(letters, roomId, checkSum);
    }

    private static class Room {
        private final String letters;
        private final int roomId;
        private final String checkSum;

        public Room(String letters, int roomId, String checkSum) {
            this.letters = letters;
            this.roomId = roomId;
            this.checkSum = checkSum;
        }

        public boolean isRealRoom() {
            String calculatedCheckSum = calculateCheckSum();

            return checkSum.equals(calculatedCheckSum);
        }

        private String calculateCheckSum() {
            Map<Character, Letter> letterMap = letters.chars()
                    .filter(c -> c != '-')
                    .mapToObj(c -> new Letter((char) c, 1))
                    .collect(Collectors.toMap(letter -> letter.character, letter -> letter,
                            (a, b) -> new Letter(a.character, a.occurrences + b.occurrences)));

            return letterMap.values().stream()
                    .sorted((a, b) ->
                            a.occurrences != b.occurrences ? b.occurrences - a.occurrences : a.character - b.character)
                    .limit(5)
                    .collect(Collector.of(
                            StringBuilder::new,
                            (stringBuilder, letter) -> stringBuilder.append(letter.character),
                            StringBuilder::append,
                            StringBuilder::toString));
        }

        public String decryptName() {
            return letters.chars()
                    .mapToObj(c -> decryptCharacter((char) c))
                    .map(String::valueOf)
                    .collect(Collectors.joining(""));
        }

        private char decryptCharacter(char c) {
            if (c == '-') {
                return ' ';
            } else {
                return ALPHABET.charAt((ALPHABET.indexOf(c) + roomId) % ALPHABET.length());
            }
        }
    }

    private static class Letter {
        private final char character;
        private final int occurrences;

        public Letter(char character, int occurrences) {
            this.character = character;
            this.occurrences = occurrences;
        }
    }
}
