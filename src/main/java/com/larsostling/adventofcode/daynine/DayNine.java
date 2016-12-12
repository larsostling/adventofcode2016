package com.larsostling.adventofcode.daynine;

import com.larsostling.adventofcode.Puzzle;

public class DayNine implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        return String.valueOf(decompressString(input).length());
    }

    @Override
    public String solvePartTwo(String input) {
        return String.valueOf(calculateDecompressedLength(input));
    }

    private static String decompressString(String input) {
        StringBuilder resultBuilder = new StringBuilder();
        int index = 0;
        char[] chars = input.toCharArray();

        while (index < chars.length) {
            if (chars[index] == '(') {
                MapperBlock mapperBlock = parseMapperBlock(chars, index);
                index += mapperBlock.size;

                String stringToRepeat = new String(chars, index, mapperBlock.characters);
                for (int i = 0; i < mapperBlock.repetitions; i++) {
                    resultBuilder.append(stringToRepeat);
                }

                index += mapperBlock.characters;
            } else {
                resultBuilder.append(chars[index]);
                index++;
            }
        }

        return resultBuilder.toString();
    }

    private long calculateDecompressedLength(String input) {
        long result = 0;
        int index = 0;

        char[] chars = input.toCharArray();

        while (index < chars.length) {
            if (chars[index] == '(' ) {
                MapperBlock mapperBlock = parseMapperBlock(chars, index);
                index += mapperBlock.size;

                String stringToRepeat = new String(chars, index, mapperBlock.characters);
                result += mapperBlock.repetitions * calculateDecompressedLength(stringToRepeat);

                index += mapperBlock.characters;
            } else {
                result++;
                index++;
            }
        }

        return result;
    }

    private static MapperBlock parseMapperBlock(char[] chars, int index) {
        int mapperIndex = index + 1;

        StringBuilder buffer = new StringBuilder();
        while (chars[mapperIndex] != 'x' ) {
            buffer.append(chars[mapperIndex]);
            mapperIndex++;
        }
        int characters = Integer.parseInt(buffer.toString());
        mapperIndex++;

        buffer = new StringBuilder();
        while (chars[mapperIndex] != ')' ) {
            buffer.append(chars[mapperIndex]);
            mapperIndex++;
        }
        int repetitions = Integer.parseInt(buffer.toString());
        mapperIndex++;

        return new MapperBlock(mapperIndex - index, characters, repetitions);
    }

    private static class MapperBlock {
        private final int size;
        private final int characters;
        private final int repetitions;

        public MapperBlock(int size, int characters, int repetitions) {
            this.size = size;
            this.characters = characters;
            this.repetitions = repetitions;
        }
    }
}
