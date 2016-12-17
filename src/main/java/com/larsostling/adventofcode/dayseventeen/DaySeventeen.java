package com.larsostling.adventofcode.dayseventeen;

import com.larsostling.adventofcode.Puzzle;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DaySeventeen implements Puzzle {

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
        State currentState = new State(0, 0, input);

        Queue<State> stateQueue = new ArrayDeque<>();
        stateQueue.add(currentState);

        while (!currentState.isFinalState()) {
            currentState = stateQueue.remove();
            stateQueue.addAll(currentState.newStates());
        }

        return currentState.path.substring(input.length());
    }

    @Override
    public String solvePartTwo(String input) {
        State currentState = new State(0, 0, input);

        List<State> finalStates = new ArrayList<>();
        Queue<State> stateQueue = new ArrayDeque<>();
        stateQueue.add(currentState);

        while (!stateQueue.isEmpty()) {
            currentState = stateQueue.remove();
            for (State state : currentState.newStates()) {
                if (state.isFinalState()) {
                    finalStates.add(state);
                } else {
                    stateQueue.add(state);
                }
            }
        }

        return String.valueOf(finalStates.stream()
                .sorted(State::pathLengthComparator)
                .findFirst()
                .get()
                .path.length() - input.length());
    }

    private static class State {
        private final int x;
        private final int y;
        private final String path;

        public State(int x, int y, String path) {
            this.x = x;
            this.y = y;
            this.path = path;
        }

        public List<State> newStates() {
            List<State> newStates = new ArrayList<>();

            String hash = getHash(path);
            if (x > 0 && isOpenChar(hash.charAt(0))) {
                newStates.add(new State(x - 1, y, path + 'U'));
            }
            if (x < 3 && isOpenChar(hash.charAt(1))) {
                newStates.add(new State(x + 1, y, path + 'D'));
            }
            if (y > 0 && isOpenChar(hash.charAt(2))) {
                newStates.add(new State(x, y - 1, path + 'L'));
            }
            if (y < 3 && isOpenChar(hash.charAt(3))) {
                newStates.add(new State(x, y + 1, path + 'R'));
            }

            return newStates;
        }

        public boolean isFinalState() {
            return x == 3 && y == 3;
        }

        private static boolean isOpenChar(char c) {
            return c > 'a';
        }

        public static int pathLengthComparator(State stateOne, State stateTwo) {
            return stateTwo.path.length() - stateOne.path.length();
        }
    }

    private static String getHash(String string) {
        byte[] inputBytes = string.getBytes(StandardCharsets.UTF_8);
        byte[] hashedBytes = MD5.digest(inputBytes);
        return DatatypeConverter.printHexBinary(hashedBytes).toLowerCase();
    }
}
