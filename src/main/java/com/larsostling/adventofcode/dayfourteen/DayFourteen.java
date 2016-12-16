package com.larsostling.adventofcode.dayfourteen;

import com.larsostling.adventofcode.Puzzle;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayFourteen implements Puzzle {

    private static MessageDigest MD5 = initMD5();

    private static MessageDigest initMD5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No md5 algorithm defined");
        }
    }

    @Override
    public String solvePartOne(String input) {
        return solvePuzzle(input, false);
    }

    @Override
    public String solvePartTwo(String input) {
        return solvePuzzle(input, true);
    }

    private static String solvePuzzle(String input, boolean useKeyStretching) {
        HashCollection hashCollection = new HashCollection(input, useKeyStretching);

        int index = 0;
        List<Hash> foundHashes = new ArrayList<>();

        while (foundHashes.size() < 64) {
            Hash hash = hashCollection.getNextInterestingHashForIndex(index);
            index = hash.index;
            for (Hash potentialMatch : hashCollection.getInterestingHashesInRange(index + 1, index + 1000)) {
                if (potentialMatch.hasQuintupleChar(hash.tripleChar)) {
                    foundHashes.add(hash);
                    break;
                }
            }
            index = index + 1;
        }

        return String.valueOf(foundHashes.get(63).index);
    }

    private static class HashCollection {
        private List<Hash> calculatedHashes = new ArrayList<>();
        private List<Hash> interestingHashes = new ArrayList<>();

        private final String salt;
        private final boolean useKeyStretching;

        public HashCollection(String salt, boolean useKeyStretching) {
            this.salt = salt;
            this.useKeyStretching = useKeyStretching;
        }

        public Hash getNextInterestingHashForIndex(int index) {
            addHashesIfNeeded(index);

            for (Hash interestingHash : interestingHashes) {
                if (interestingHash.index >= index) {
                    return interestingHash;
                }
            }

            int originalSize = interestingHashes.size();
            while (interestingHashes.size() == originalSize) {
                addHashForIndex(index);
                index++;
            }
            return interestingHashes.get(originalSize);
        }

        public List<Hash> getInterestingHashesInRange(int lowIndex, int highIndex) {
            addHashesIfNeeded(highIndex);

            return interestingHashes.stream()
                    .filter(hash -> lowIndex <= hash.index && hash.index <= highIndex)
                    .collect(Collectors.toList());
        }

        private void addHashesIfNeeded(int index) {
            if (calculatedHashes.size() < index) {
                for (int i = calculatedHashes.size(); i < index; i++) {
                    addHashForIndex(i);
                }
            }
        }

        private void addHashForIndex(int index) {
            Hash hash = calculateHash(salt, index);
            calculatedHashes.add(hash);
            if (hash.tripleChar != 0) {
                interestingHashes.add(hash);
            }
        }

        private Hash calculateHash(String input, int index) {
            String hash = getHashForString(input + index);
            if (useKeyStretching) {
                for (int i = 0; i < 2016; i++) {
                    hash = getHashForString(hash);
                }
            }
            return new Hash(hash, index);
        }

        private static String getHashForString(String string) {
            byte[] inputBytes = string.getBytes(StandardCharsets.UTF_8);
            byte[] hashedBytes = MD5.digest(inputBytes);
            return DatatypeConverter.printHexBinary(hashedBytes).toLowerCase();
        }
    }

    private static class Hash {
        private final String hash;
        private final int index;

        private final char tripleChar;

        public Hash(String hash, int index) {
            this.hash = hash;
            this.index = index;

            this.tripleChar = getTripleChar(hash);
        }

        private static char getTripleChar(String hash) {
            char[] chars = hash.toCharArray();
            for (int i = 0; i <= hash.length() - 3; i++) {
                if (chars[i] == chars[i + 1] && chars[i + 1] == chars[i + 2]) {
                    return chars[i];
                }
            }
            return 0;
        }

        public boolean hasQuintupleChar(char c) {
            char[] chars = new char[5];
            Arrays.fill(chars, c);
            String quintuple = new String(chars);
            return hash.contains(quintuple);
        }
    }
}
