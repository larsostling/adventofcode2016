package com.larsostling.adventofcode.dayfive;

import com.larsostling.adventofcode.Puzzle;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DayFive implements Puzzle {

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
        return IntStream.iterate(0, i -> i + 1)
                .mapToObj(i -> calculateHash(input, i))
                .filter(hash -> hash.startsWith("00000"))
                .limit(8)
                .map(hash -> String.valueOf(hash.charAt(5)))
                .collect(Collectors.joining(""));
    }

    @Override
    public String solvePartTwo(String input) {
        CodeAccumulator accumulator = new CodeAccumulator();

        int i = 0;
        while (!accumulator.isFull()) {
            String hash = calculateHash(input, i);

            if (hash.startsWith("00000")) {
                accumulator.addNumber(Character.getNumericValue(hash.charAt(5)), hash.charAt(6));
            }

            i++;
        }

        return accumulator.toString();
    }

    private static String calculateHash(String input, int i) {
        byte[] inputBytes = (input + i).getBytes(StandardCharsets.UTF_8);
        byte[] hashedBytes = MD5.digest(inputBytes);
        return DatatypeConverter.printHexBinary(hashedBytes).toLowerCase();
    }

    private static class CodeAccumulator {

        private char[] code = { 0, 0, 0, 0, 0, 0, 0, 0 };

        public void addNumber(int position, char character) {
            if (position < code.length && code[position] == 0) {
                code[position] = character;
            }
        }

        public boolean isFull() {
            for (char c : code) {
                if (c == 0) {
                    return false;
                }
            }
            return true;
        }

        public String toString() {
            return new String(code);
        }
    }
}
