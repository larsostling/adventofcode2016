package com.larsostling.adventofcode.dayfifteen;

import com.larsostling.adventofcode.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;

public class DayFifteen implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        Sculpture sculpture = Arrays.stream(input.split("\n"))
                .map(DayFifteen::parseDisc)
                .collect(Sculpture.newCollector());

        return String.valueOf(sculpture.getFirstCapsuleTime());
    }

    @Override
    public String solvePartTwo(String input) {
        Sculpture sculpture = Arrays.stream(input.split("\n"))
                .map(DayFifteen::parseDisc)
                .collect(Sculpture.newCollector());

        sculpture.addDisc(new Disc(11, 0));

        return String.valueOf(sculpture.getFirstCapsuleTime());
    }

    private static Disc parseDisc(String input) {
        String[] parts = input.substring(0, input.length() - 1).split(" ");
        return new Disc(Integer.parseInt(parts[3]), Integer.parseInt(parts[11]));
    }

    private static class Disc {
        private final int positions;
        private final int startPosition;

        public Disc(int positions, int startPosition) {
            this.positions = positions;
            this.startPosition = startPosition;
        }
    }

    private static class Sculpture {
        private final List<Disc> discs = new ArrayList<>();

        public static Collector<Disc, Sculpture, Sculpture> newCollector() {
            return Collector.of(Sculpture::new,
                    Sculpture::addDisc,
                    Sculpture::merge,
                    Function.identity());
        }

        public void addDisc(Disc disc) {
            discs.add(disc);
        }

        public Sculpture merge(Sculpture otherSculpture) {
            discs.addAll(otherSculpture.discs);
            return this;
        }

        public int getFirstCapsuleTime() {
            int startingTime = 0;
            while (true) {
                if (passesAllDiscs(startingTime)) {
                    return startingTime;
                }
                startingTime++;
            }
        }

        private boolean passesAllDiscs(int startingTime) {
            for (int i = 0; i < discs.size(); i++) {
                Disc disc = discs.get(i);
                if ((disc.startPosition + startingTime + i + 1) % disc.positions != 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
