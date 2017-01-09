package com.larsostling.adventofcode.daytwentyfive;

import com.larsostling.adventofcode.Puzzle;

import java.util.HashMap;
import java.util.Map;

public class DayTwentyFive implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        int aInit = 0;
        while (!handleInstructions(new Computer(aInit, 0, 0, 0), input)) {
            aInit++;
        }
        return String.valueOf(aInit);
    }

    @Override
    public String solvePartTwo(String input) {
        return null;
    }

    private static boolean handleInstructions(Computer computer, String input) {
        String[] instructions = input.split("\n");
        for (int i = 0; i < instructions.length && computer.output.length() < 20; i++) {
            String[] parts = instructions[i].split(" ");
            switch (parts[0]) {
                case "cpy":
                    computer.copy(parts[1], parts[2]);
                    break;
                case "inc":
                    computer.increment(parts[1]);
                    break;
                case "dec":
                    computer.decrement(parts[1]);
                    break;
                case "jnz":
                    if (computer.getRegisterValueOrParse(parts[1]) != 0) {
                        i += computer.getRegisterValueOrParse(parts[2]) - 1;
                    }
                    break;
                case "tgl":
                    int offset = computer.getRegisterValueOrParse(parts[1]);
                    int position = i + offset;
                    if (position < instructions.length) {
                        instructions[position] = toggleInstruction(instructions[position]);
                    }
                    break;
                case "out":
                    computer.output(computer.getRegisterValueOrParse(parts[1]));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown instruction");
            }
        }
        return computer.generatesClockSignal();
    }

    private static String toggleInstruction(String instruction) {
        String[] parts = instruction.split(" ");
        switch (parts[0]) {
            case "cpy":
                return "jnz " + parts[1] + " " + parts[2];
            case "inc":
                return "dec " + parts[1];
            case "dec":
                return "inc " + parts[1];
            case "jnz":
                return "cpy " + parts[1] + " " + parts[2];
            case "tgl":
                return "inc " + parts[1];
            default:
                throw new IllegalArgumentException("Unknown instruction");
        }
    }

    private static class Computer {
        private final Map<String, Register> registerMap;
        private String output = "";

        public Computer(int valueA, int valueB, int valueC, int valueD) {
            Map<String, Register> registerMap = new HashMap<>();
            registerMap.put("a", new Register(valueA));
            registerMap.put("b", new Register(valueB));
            registerMap.put("c", new Register(valueC));
            registerMap.put("d", new Register(valueD));
            this.registerMap = registerMap;
        }

        public void copy(String toCopy, String toRegister) {
            registerMap.get(toRegister).setValue(getRegisterValueOrParse(toCopy));
        }

        public int getRegisterValueOrParse(String input) {
            if (registerMap.containsKey(input)) {
                return registerMap.get(input).getValue();
            } else {
                return Integer.parseInt(input);
            }

        }

        public void increment(String register) {
            registerMap.get(register).inc();
        }

        public void decrement(String register) {
            registerMap.get(register).dec();
        }

        public void output(int value) {
            output += value;
        }

        public boolean generatesClockSignal() {
            return output.endsWith("0101010101") || output.endsWith("1010101010");
        }
    }

    private static class Register {
        private int value;

        public Register(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public void inc() {
            value++;
        }

        public void dec() {
            value--;
        }
    }
}
