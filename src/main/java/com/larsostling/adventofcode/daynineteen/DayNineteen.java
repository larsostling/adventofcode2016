package com.larsostling.adventofcode.daynineteen;

import com.larsostling.adventofcode.Puzzle;

public class DayNineteen implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        Elf elf = createElves(Integer.parseInt(input));
        while (elf.nextElf != elf) {
            elf.nextElf = elf.nextElf.nextElf;
            elf = elf.nextElf;
        }
        return String.valueOf(elf.number);
    }

    @Override
    public String solvePartTwo(String input) {
        int numberOfElves = Integer.parseInt(input);
        Elf elf = createElves(numberOfElves);
        Elf elfBeforeElfToRemove = rotateElves(elf, numberOfElves / 2 - 1);
        while (numberOfElves > 1) {
            elfBeforeElfToRemove.nextElf = elfBeforeElfToRemove.nextElf.nextElf;
            if (numberOfElves % 2 == 1) {
                elfBeforeElfToRemove = elfBeforeElfToRemove.nextElf;
            }
            elf = elf.nextElf;
            numberOfElves--;
        }
        return String.valueOf(elf.number);
    }

    private static Elf createElves(int numberOfElves) {
        Elf firstElf = new Elf(1);
        Elf currentElf = firstElf;
        for (int i = 2; i <= numberOfElves; i++) {
            currentElf.nextElf = new Elf(i);
            currentElf = currentElf.nextElf;
        }
        currentElf.nextElf = firstElf;
        return firstElf;
    }

    private static Elf rotateElves(Elf elf, int steps) {
        for (int i = 0; i < steps; i++) {
            elf = elf.nextElf;
        }
        return elf;
    }

    private static class Elf {
        private final int number;
        private Elf nextElf;

        public Elf(int number) {
            this.number = number;
        }
    }
}
