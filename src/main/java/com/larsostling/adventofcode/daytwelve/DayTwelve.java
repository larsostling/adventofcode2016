package com.larsostling.adventofcode.daytwelve;

import com.larsostling.adventofcode.Puzzle;

import java.util.HashMap;
import java.util.Map;

public class DayTwelve implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        return handleInstructions(new Computer(0, 0, 0, 0), input);
    }

    @Override
    public String solvePartTwo(String input) {
        return handleInstructions(new Computer(0, 0, 1, 0), input);
    }

    private static String handleInstructions(Computer computer, String input) {
        String[] instructions = input.split("\n");
        for (int i = 0; i < instructions.length; i++) {
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
                        i += Integer.parseInt(parts[2]) - 1;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown instruction");
            }
        }

        return String.valueOf(computer.getValueForRegister("a"));
    }

    private static class Computer {
        private final Map<String, Register> registerMap;

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

        public int getValueForRegister(String register) {
            return registerMap.get(register).getValue();
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
