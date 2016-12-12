package com.larsostling.adventofcode.dayeight;

import com.larsostling.adventofcode.Puzzle;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collector;

public class DayEight implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        return String.valueOf(Arrays.stream(input.split("\n"))
                .map(DayEight::parseInstruction)
                .collect(Display.newCollector(6, 50))
                .countLitPixels());
    }

    @Override
    public String solvePartTwo(String input) {
        return Arrays.stream(input.split("\n"))
                .map(DayEight::parseInstruction)
                .collect(Display.newCollector(6, 50))
                .printPixels();
    }

    private static Instruction parseInstruction(String input) {
        if (input.startsWith("rect ")) {
            String[] dimensions = input.substring(5).split("x");
            return new AddRectangleInstruction(Integer.parseInt(dimensions[1]), Integer.parseInt(dimensions[0]));
        } else if (input.startsWith("rotate row y=")) {
            String[] numbers = input.substring(13).split(" by ");
            return new RotateRowInstruction(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
        } else if (input.startsWith("rotate column x=")) {
            String[] numbers = input.substring(16).split(" by ");
            return new RotateColumnInstruction(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
        } else {
            throw new IllegalArgumentException("Unknown instruction");
        }
    }

    private static interface Instruction {
        void apply(Display display);
    }

    private static class AddRectangleInstruction implements Instruction {
        private final int rows;
        private final int columns;

        public AddRectangleInstruction(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
        }

        @Override
        public void apply(Display display) {
            display.addRectangle(rows, columns);
        }
    }

    private static class RotateRowInstruction implements Instruction {
        private final int row;
        private final int steps;

        private RotateRowInstruction(int row, int steps) {
            this.row = row;
            this.steps = steps;
        }

        @Override
        public void apply(Display display) {
            display.rotateRow(row, steps);
        }
    }

    private static class RotateColumnInstruction implements Instruction {
        private final int column;
        private final int steps;

        private RotateColumnInstruction(int column, int steps) {
            this.column = column;
            this.steps = steps;
        }

        @Override
        public void apply(Display display) {
            display.rotateColumn(column, steps);
        }
    }

    private static class Display {
        private final int rows;
        private final int columns;
        private final int[][] pixels;

        private Display(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
            this.pixels = new int[rows][columns];
        }

        public void instruct(Instruction instruction) {
            instruction.apply(this);
        }

        public void addRectangle(int rows, int columns) {
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    pixels[row][column] = 1;
                }
            }
        }

        public void rotateRow(int row, int steps) {
            pixels[row] = rotateArray(pixels[row], steps);
        }

        public void rotateColumn(int column, int steps) {
            int[] columnArray = new int[rows];
            for (int row = 0; row < pixels.length; row++) {
                columnArray[row] = pixels[row][column];
            }
            columnArray = rotateArray(columnArray, steps);
            for (int row = 0; row < pixels.length; row++) {
                pixels[row][column] = columnArray[row];
            }
        }

        private int[] rotateArray(int[] array, int steps) {
            int[] result = new int[array.length];
            System.arraycopy(array, 0, result, steps, array.length - steps);
            System.arraycopy(array, array.length - steps, result, 0, steps);
            return result;
        }

        public static Collector<Instruction, Display, Display> newCollector(int rows, int columns) {
            return Collector.of(() -> new Display(rows, columns),
                    Display::instruct,
                    Display::merge,
                    Function.identity());
        }

        public Display merge(Display otherDisplay) {
            throw new UnsupportedOperationException("No merging allowed");
        }

        public int countLitPixels() {
            int result = 0;
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    if (pixels[row][column] == 1) {
                        result++;
                    }
                }
            }
            return result;
        }

        public String printPixels() {
            StringBuilder resultBuilder = new StringBuilder("\n");
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    if (pixels[row][column] == 1) {
                        resultBuilder.append('#');
                    } else {
                        resultBuilder.append('.');
                    }
                }
                resultBuilder.append('\n');
            }
            return resultBuilder.toString();
        }
    }
}
