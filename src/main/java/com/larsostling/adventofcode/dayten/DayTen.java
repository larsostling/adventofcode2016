package com.larsostling.adventofcode.dayten;

import com.larsostling.adventofcode.Puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayTen implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        RobotFactory robotFactory = new RobotFactory();
        for (String instruction : input.split("\n")) {
            robotFactory.handleInstruction(instruction);
        }
        return String.valueOf(robotFactory.getRobotIdForChips(17, 61));
    }

    @Override
    public String solvePartTwo(String input) {
        RobotFactory robotFactory = new RobotFactory();
        for (String instruction : input.split("\n")) {
            robotFactory.handleInstruction(instruction);
        }
        return String.valueOf(robotFactory.multiplyChipsForOutput(0, 1, 2));
    }

    private static class RobotFactory {
        private Map<Integer, Output> outputMap = new HashMap<>();
        private Map<Integer, Robot> robotMap = new HashMap<>();

        public void handleInstruction(String instruction) {
            if (instruction.startsWith("value ")) {
                String[] numbers = instruction.substring(6).split(" goes to bot ");
                handOverChip(ReceiverType.ROBOT, Integer.parseInt(numbers[1]), Integer.parseInt(numbers[0]));
            } else if (instruction.startsWith("bot ")) {
                String[] parts = instruction.split(" ");
                getRobotById(Integer.parseInt(parts[1]))
                        .setReceivers(ReceiverType.forString(parts[5]), Integer.parseInt(parts[6]),
                                ReceiverType.forString(parts[10]), Integer.parseInt(parts[11]));
            } else {
                throw new IllegalArgumentException("Unknown instruction");
            }
        }

        public void handOverChip(ReceiverType receiverType, int receiverId, int chip) {
            if (receiverType == ReceiverType.OUTPUT) {
                getOutputById(receiverId).addChip(chip);
            } else if (receiverType == ReceiverType.ROBOT) {
                getRobotById(receiverId).giveChip(chip);
            } else {
                throw new IllegalArgumentException("Unknown receiver type");
            }
        }

        private Output getOutputById(int outputId) {
            if (!outputMap.containsKey(outputId)) {
                outputMap.put(outputId, new Output(outputId));
            }

            return outputMap.get(outputId);
        }

        private Robot getRobotById(int robotId) {
            if (!robotMap.containsKey(robotId)) {
                robotMap.put(robotId, new Robot(this, robotId));
            }

            return robotMap.get(robotId);
        }

        public int getRobotIdForChips(int lowChip, int highChip) {
            for (Robot robot : robotMap.values()) {
                if (robot.lowChip == lowChip  && robot.highChip == highChip) {
                    return robot.robotId;
                }
            }

            throw new IllegalArgumentException("No robot found");
        }

        public int multiplyChipsForOutput(int... chipIds) {
            int result = 1;
            for (int chipId : chipIds) {
                result *= getOutputById(chipId).chips.get(0);
            }
            return result;
        }
    }

    private static class Output {
        private final int outputId;

        private List<Integer> chips = new ArrayList<>();

        private Output(int outputId) {
            this.outputId = outputId;
        }

        public void addChip(int chipId) {
            chips.add(chipId);
        }
    }

    private static class Robot {
        private final int robotId;
        private final RobotFactory factory;

        private int lowChip = -1;
        private int highChip = -1;
        private ReceiverType lowReceiverType;
        private int lowReceiver = -1;
        private ReceiverType highReceiverType;
        private int highReceiver = -1;

        public Robot(RobotFactory factory, int robotId) {
            this.factory = factory;
            this.robotId = robotId;
        }

        public void giveChip(int chip) {
            if (lowChip == -1) {
                lowChip = chip;
            } else if (chip < lowChip) {
                highChip = lowChip;
                lowChip = chip;
            } else {
                highChip = chip;
            }

            proceedWhenReady();
        }

        public void setReceivers(ReceiverType lowReceiverType, int lowReceiver,
                                 ReceiverType highReceiverType, int highReceiver) {
            this.lowReceiverType = lowReceiverType;
            this.lowReceiver = lowReceiver;
            this.highReceiverType = highReceiverType;
            this.highReceiver = highReceiver;

            proceedWhenReady();
        }

        public void proceedWhenReady() {
            if (lowChip != -1 && highChip != -1 && lowReceiver != -1 && highReceiver != -1) {
                factory.handOverChip(lowReceiverType, lowReceiver, lowChip);
                factory.handOverChip(highReceiverType, highReceiver, highChip);
            }
        }
    }

    private static enum ReceiverType {
        OUTPUT, ROBOT;

        public static ReceiverType forString(String string) {
            if (string.equals("output")) {
                return OUTPUT;
            } else if (string.equals("bot")) {
                return ROBOT;
            } else {
                throw new IllegalArgumentException("Unknown receiver type");
            }
        }
    }
}
