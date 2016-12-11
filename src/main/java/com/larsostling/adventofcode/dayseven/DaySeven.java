package com.larsostling.adventofcode.dayseven;

import com.larsostling.adventofcode.Puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DaySeven implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        return String.valueOf(Arrays.stream(input.split("\n"))
                .map(IPv7Address::new)
                .filter(IPv7Address::supportsTLS)
                .count());
    }

    @Override
    public String solvePartTwo(String input) {
        return String.valueOf(Arrays.stream(input.split("\n"))
                .map(IPv7Address::new)
                .filter(IPv7Address::supportsSSL)
                .count());
    }

    private static class IPv7Address {
        private final String address;

        public IPv7Address(String address) {
            this.address = address;
        }

        public boolean supportsTLS() {
            char[] chars = address.toCharArray();

            boolean foundAbbaSequence = false;
            boolean isInsideBrackets = false;

            for (int i = 0; i <= chars.length - 4; i++) {
                if (chars[i] == '[') {
                    isInsideBrackets = true;
                } else if (chars[i] == ']') {
                    isInsideBrackets = false;
                }

                if (chars[i] != chars[i + 1] && chars[i] == chars[i + 3] && chars[i + 1] == chars[i + 2]) {
                    if (isInsideBrackets) {
                        return false;
                    } else {
                        foundAbbaSequence = true;
                    }
                }
            }

            return foundAbbaSequence;
        }

        public boolean supportsSSL() {
            char[] chars = address.toCharArray();

            Set<String> abaSet = new HashSet<>();
            Set<String> babSet = new HashSet<>();

            boolean isInsideBrackets = false;

            for (int i = 0; i <= chars.length - 3; i++) {
                if (chars[i] == '[') {
                    isInsideBrackets = true;
                } else if (chars[i] == ']') {
                    isInsideBrackets = false;
                }

                if (chars[i] != chars[i + 1] && chars[i] == chars[i + 2]) {
                    if (isInsideBrackets) {
                        babSet.add(String.valueOf(chars, i, 3));
                    } else {
                        abaSet.add(String.valueOf(chars, i, 3));
                    }
                }
            }

            for (String aba : abaSet) {
                String bab = new StringBuilder()
                        .append(aba.charAt(1))
                        .append(aba.charAt(0))
                        .append(aba.charAt(1))
                        .toString();
                if (babSet.contains(bab)) {
                    return true;
                }
            }

            return false;
        }
    }
}
