package com.larsostling.adventofcode.daysix;

import com.larsostling.adventofcode.Puzzle;

import java.util.*;
import java.util.stream.Collectors;

public class DaySix implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        MessageCollection collection = new MessageCollection();
        for (String message : input.split("\n")) {
            collection.addMessage(message);
        }
        return collection.getDescrambledMessage();
    }

    @Override
    public String solvePartTwo(String input) {
        MessageCollection collection = new MessageCollection();
        for (String message : input.split("\n")) {
            collection.addMessage(message);
        }
        return collection.getActualMessage();
    }

    private static class MessageCollection {
        private List<LetterBox> letterBoxes = new ArrayList<>();

        private void addMessage(String row) {
            char[] characters = row.toCharArray();

            if (letterBoxes.isEmpty()) {
                for (int i = 0; i < characters.length; i++) {
                    letterBoxes.add(new LetterBox());
                }
            } else if (characters.length != letterBoxes.size()) {
                throw new IllegalArgumentException("Length does not match");
            }

            int index = 0;
            for (LetterBox letterBox: letterBoxes) {
                letterBox.addChar(characters[index]);

                index++;
            }
        }

        public String getDescrambledMessage() {
            return letterBoxes.stream()
                    .map(LetterBox::getMostCommonCharacter)
                    .map(String::valueOf)
                    .collect(Collectors.joining(""));
        }

        public String getActualMessage() {
            return letterBoxes.stream()
                    .map(LetterBox::getLeastCommonCharacter)
                    .map(String::valueOf)
                    .collect(Collectors.joining(""));
        }
    }

    private static class LetterBox {
        private Map<Character, Letter> letterMap = new HashMap<>();

        public void addChar(char c) {
            if (letterMap.containsKey(c)) {
                letterMap.put(c, letterMap.get(c).addOccurence());
            } else {
                letterMap.put(c, new Letter(c, 1));
            }
        }

        public char getMostCommonCharacter() {
            return getFirstCharacterSorted((a, b) -> b.occurrences - a.occurrences);
        }

        public char getLeastCommonCharacter() {
            return getFirstCharacterSorted((a, b) -> a.occurrences - b.occurrences);
        }

        public char getFirstCharacterSorted(Comparator<Letter> comparator) {
            return letterMap.values().stream()
                    .sorted(comparator)
                    .map(letter -> letter.character)
                    .findFirst()
                    .get();
        }
    }

    private static class Letter {
        private final char character;
        private final int occurrences;

        private Letter(char character, int occurrences) {
            this.character = character;
            this.occurrences = occurrences;
        }

        private Letter addOccurence() {
            return new Letter(character, occurrences + 1);
        }
    }
}
