package com.larsostling.adventofcode.daytwentyone;

import com.larsostling.adventofcode.Puzzle;

import java.util.List;
import java.util.stream.Collectors;

public class DayTwentyOne implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        Scrambler scrambler = new Scrambler("abcdefgh");
        for (String instruction : input.split("\n")) {
            scrambler.instruct(instruction);
        }
        return scrambler.password;
    }

    @Override
    public String solvePartTwo(String input) {
        Scrambler scrambler = new Scrambler("fbgdceah");
        String[] instructions = input.split("\n");
        for (int i = instructions.length - 1; i >= 0; i--) {
            scrambler.instructReverse(instructions[i]);
        }
        return scrambler.password;
    }

    private static class Scrambler {
        private String password;

        public Scrambler(String password) {
            this.password = password;
        }

        public void instruct(String instruction) {
            if (instruction.startsWith("swap position")) {
                String[] parts = instruction.split(" ");
                swapPositions(Integer.parseInt(parts[2]), Integer.parseInt(parts[5]));
            } else if (instruction.startsWith("swap letter")) {
                swapLetters(instruction.charAt(12), instruction.charAt(26));
            } else if (instruction.startsWith("rotate left")) {
                String[] parts = instruction.split(" ");
                rotateLeft(Integer.parseInt(parts[2]));
            } else if (instruction.startsWith("rotate right")) {
                String[] parts = instruction.split(" ");
                rotateRight(Integer.parseInt(parts[2]));
            } else if (instruction.startsWith("rotate based on position of letter")) {
                rotateBasedOnPositionOfLetter(instruction.charAt(instruction.length() - 1));
            } else if (instruction.startsWith("reverse position")) {
                String[] parts = instruction.split(" ");
                reversePositions(Integer.parseInt(parts[2]), Integer.parseInt(parts[4]));
            } else if (instruction.startsWith("move position")) {
                String[] parts = instruction.split(" ");
                movePosition(Integer.parseInt(parts[2]), Integer.parseInt(parts[5]));
            }
        }

        public void instructReverse(String instruction) {
            if (instruction.startsWith("swap position")) {
                String[] parts = instruction.split(" ");
                swapPositions(Integer.parseInt(parts[2]), Integer.parseInt(parts[5]));
            } else if (instruction.startsWith("swap letter")) {
                swapLetters(instruction.charAt(12), instruction.charAt(26));
            } else if (instruction.startsWith("rotate left")) {
                String[] parts = instruction.split(" ");
                rotateRight(Integer.parseInt(parts[2]));
            } else if (instruction.startsWith("rotate right")) {
                String[] parts = instruction.split(" ");
                rotateLeft(Integer.parseInt(parts[2]));
            } else if (instruction.startsWith("rotate based on position of letter")) {
                reverseRotateBasedOnPositionOfLetter(instruction.charAt(instruction.length() - 1));
            } else if (instruction.startsWith("reverse position")) {
                String[] parts = instruction.split(" ");
                reversePositions(Integer.parseInt(parts[2]), Integer.parseInt(parts[4]));
            } else if (instruction.startsWith("move position")) {
                String[] parts = instruction.split(" ");
                movePosition(Integer.parseInt(parts[5]), Integer.parseInt(parts[2]));
            }
        }

        private void swapPositions(int positionOne, int positionTwo) {
            char[] chars = password.toCharArray();
            char charOne = chars[positionOne];
            chars[positionOne] = chars[positionTwo];
            chars[positionTwo] = charOne;
            password = new String(chars);
        }

        private void swapLetters(char charOne, char charTwo) {
            swapPositions(password.indexOf(charOne), password.indexOf(charTwo));
        }

        public void rotateLeft(int steps) {
            steps = (password.length() + steps) % password.length();
            password = password.substring(steps, password.length()) + password.substring(0, steps);
        }

        public void rotateRight(int steps) {
            steps = (password.length() - steps) % password.length();
            rotateLeft(steps);
        }

        public void rotateBasedOnPositionOfLetter(char letter) {
            int index = password.indexOf(letter);
            if (index >= 4) {
                index++;
            }
            rotateRight(index + 1);
        }

        public void reverseRotateBasedOnPositionOfLetter(char letter) {
            int index = password.indexOf(letter);
            if (index % 2 == 1) {
                rotateLeft(index / 2 + 1);
            } else {
                if (index == 0) {
                    index = 8;
                }
                rotateLeft(index / 2 + 5);
            }
        }

        private void reversePositions(int positionOne, int positionTwo) {
            password = password.substring(0, positionOne)
                    + new StringBuilder(password.substring(positionOne, positionTwo + 1)).reverse().toString()
                    + password.substring(positionTwo + 1);
        }

        private void movePosition(int positionOne, int positionTwo) {
            List<Character> chars = password.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
            Character c = chars.remove(positionOne);
            chars.add(positionTwo, c);
            password = chars.stream().map(String::valueOf).collect(Collectors.joining(""));
        }
    }
}
