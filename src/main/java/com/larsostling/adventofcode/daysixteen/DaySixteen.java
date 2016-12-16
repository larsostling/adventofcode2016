package com.larsostling.adventofcode.daysixteen;

import com.larsostling.adventofcode.Puzzle;

public class DaySixteen implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        String data = generateData(input, 272);
        return calculateCheckSum(data);
    }

    @Override
    public String solvePartTwo(String input) {
        String data = generateData(input, 35651584);
        return calculateCheckSum(data);
    }

    private static String generateData(String input, int size) {
        String data = input;
        while (data.length() < size) {
            String a = data;
            String b = flipCharacters(reverseString(a));
            data = a + '0' + b;
        }
        return data.substring(0, size);
    }

    private static String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    private static String flipCharacters(String input) {
        char[] inputChars = input.toCharArray();
        char[] outputChars = new char[inputChars.length];
        for (int i = 0; i < inputChars.length; i++) {
            outputChars[i] = inputChars[i] == '0' ? '1' : '0';
        }
        return new String(outputChars);
    }

    private static String calculateCheckSum(String input) {
        char[] checksumChars = input.toCharArray();
        while (checksumChars.length % 2 == 0) {
            char[] outputChars = new char[checksumChars.length / 2];
            for (int i = 0; i < checksumChars.length; i += 2) {
                outputChars[i / 2] = checksumChars[i] == checksumChars[i + 1] ? '1' : '0';
            }
            checksumChars = outputChars;
        }
        return new String(checksumChars);
    }
}
