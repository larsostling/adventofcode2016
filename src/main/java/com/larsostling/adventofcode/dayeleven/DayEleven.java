package com.larsostling.adventofcode.dayeleven;

import com.larsostling.adventofcode.Puzzle;

import java.util.*;
import java.util.stream.Collectors;

public class DayEleven implements Puzzle {
    @Override
    public String solvePartOne(String input) {
        return solvePuzzle(input.split("\n"));
    }

    @Override
    public String solvePartTwo(String input) {
        String[] parts = input.split("\n");
        parts[0] = parts[0] + " an elerium generator, an elerium-compatible microchip, "
                + "a dilithium generator and a dilithium-compatible microchip";
        return solvePuzzle(parts);
    }

    private static String solvePuzzle(String[] input) {
        State initialState = parseInitialState(input);

        Set<StateKey> visitedStateKeys = new HashSet<>();

        Optional<State> finalState = Optional.empty();
        Queue<State> stateQueue = new ArrayDeque<>();
        stateQueue.add(initialState);

        while (!finalState.isPresent() && !stateQueue.isEmpty()) {
            State currentState = stateQueue.remove();

            if (visitedStateKeys.add(currentState.getStateKey())) {
                for (State state : currentState.newStates()) {
                    if (state.isFinalState()) {
                        finalState = Optional.of(state);
                        break;
                    } else if (!visitedStateKeys.contains(state.getStateKey())) {
                        stateQueue.add(state);
                    }
                }
            }
        }

        return String.valueOf(finalState.get().countPreviousStates());
    }

    private static State parseInitialState(String[] input) {
        List<Floor> floors = Arrays.stream(input)
                .map(DayEleven::parseFloor)
                .collect(Collectors.toList());

        return new State(floors, 0, Optional.empty());
    }

    private static Floor parseFloor(String input) {
        String[] parts = input.split(" ");
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < parts.length - 2; i++) {
            if ("a".equals(parts[i]) || "an".equals(parts[i])) {
                if (parts[i + 2].startsWith("generator")) {
                    items.add(new Item(ItemType.GENERATOR, Substance.forString(parts[i + 1])));
                } else if (parts[i + 2].startsWith("microchip")) {
                    items.add(new Item(ItemType.MICROCHIP, Substance.forString(parts[i + 1].split("-")[0])));
                }
            }
        }
        return new Floor(items);
    }

    private static class State {
        private final List<Floor> floors;
        private final int elevatorPosition;
        private final Optional<State> previousState;
        private final StateKey stateKey;

        public State(List<Floor> floors, int elevatorPosition, Optional<State> previousState) {
            this.floors = floors;
            this.elevatorPosition = elevatorPosition;
            this.previousState = previousState;
            this.stateKey = generateStateKey();
        }

        private StateKey generateStateKey() {
            Map<Substance, Integer> generatorFloors = new HashMap<>();
            Map<Substance, Integer> microchipFloors = new HashMap<>();

            int floorIndex = 0;
            for (Floor floor : floors) {
                for (Item item : floor.items) {
                    if (item.type == ItemType.GENERATOR) {
                        generatorFloors.put(item.substance, floorIndex);
                    } else if (item.type == ItemType.MICROCHIP) {
                        microchipFloors.put(item.substance, floorIndex);
                    }
                }
                floorIndex++;
            }

            List<ItemPair> itemPairs = new ArrayList<>();
            for (Map.Entry<Substance, Integer> entry : generatorFloors.entrySet()) {
                int generatorFloor = entry.getValue();
                int microchipFloor = microchipFloors.get(entry.getKey());
                itemPairs.add(new ItemPair(generatorFloor, microchipFloor));
            }

            return new StateKey(elevatorPosition, itemPairs);
        }

        public boolean isFinalState() {
            return elevatorPosition == floors.size() - 1 &&
                    floors.get(elevatorPosition).items.size() ==
                            floors.stream().mapToInt(floor -> floor.items.size()).sum();
        }

        public List<State> newStates() {
            Floor currentFloor = floors.get(elevatorPosition);
            List<List<Item>> movableItemCombinations = currentFloor.getMovableItemCombinations();

            List<State> newStates = new ArrayList<>();
            if (elevatorPosition < floors.size() - 1) {
                newStates.addAll(getNewStatesForElevatorPosition(elevatorPosition + 1, movableItemCombinations));
            }
            if (elevatorPosition > 0) {
                newStates.addAll(getNewStatesForElevatorPosition(elevatorPosition - 1, movableItemCombinations));
            }
            return newStates;
        }

        private List<State> getNewStatesForElevatorPosition(int newElevatorPosition,
                                                            List<List<Item>> movableItemCombinations) {
            List<State> newStates = new ArrayList<>();

            Floor currentFloor = floors.get(elevatorPosition);
            Floor nextFloor = floors.get(newElevatorPosition);

            for (List<Item> combination : movableItemCombinations) {
                Floor updatedCurrentFloor = currentFloor.removeItems(combination);
                Floor updatedNextFloor = nextFloor.addItems(combination);

                if (updatedCurrentFloor.isValid() && updatedNextFloor.isValid()) {
                    Floor[] newFloors = new Floor[floors.size()];
                    for (int i = 0; i < floors.size(); i++) {
                        if (i == elevatorPosition) {
                            newFloors[i] = updatedCurrentFloor;
                        } else if (i == newElevatorPosition) {
                            newFloors[i] = updatedNextFloor;
                        } else {
                            newFloors[i] = floors.get(i);
                        }
                    }
                    newStates.add(new State(Arrays.asList(newFloors), newElevatorPosition, Optional.of(this)));
                }
            }

            return newStates;
        }

        public int countPreviousStates() {
            return previousState.map(state -> state.countPreviousStates() + 1).orElse(0);
        }

        public StateKey getStateKey() {
            return stateKey;
        }
    }

    private static class Floor {
        private final List<Item> items;

        public Floor(List<Item> items) {
            this.items = Collections.unmodifiableList(items);
        }

        public List<List<Item>> getMovableItemCombinations() {
            List<List<Item>> combinations = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                Item firstItem = items.get(i);
                combinations.add(Arrays.asList(firstItem));
                for (int j = i + 1; j < items.size(); j++) {
                    Item secondItem = items.get(j);
                    if (isValidCombination(firstItem, secondItem)) {
                        combinations.add(Arrays.asList(firstItem, secondItem));
                    }
                }
            }
            return combinations;
        }

        private static boolean isValidCombination(Item firstItem, Item secondItem) {
            return firstItem.type == secondItem.type || firstItem.substance == secondItem.substance;
        }

        public boolean isValid() {
            Set<Substance> generatorSubstances = new HashSet<>();
            Set<Substance> microchipSubstances = new HashSet<>();

            for (Item item : items) {
                if (item.type == ItemType.GENERATOR) {
                    generatorSubstances.add(item.substance);
                } else if (item.type == ItemType.MICROCHIP) {
                    microchipSubstances.add(item.substance);
                }
            }

            if (generatorSubstances.size() > 0) {
                for (Substance microchipSubstance : microchipSubstances) {
                    if (!generatorSubstances.contains(microchipSubstance)) {
                        return false;
                    }
                }
            }

            return true;
        }

        public Floor addItems(List<Item> itemsToAdd) {
            List<Item> newItems = new ArrayList<>();
            newItems.addAll(items);
            newItems.addAll(itemsToAdd);
            return new Floor(newItems);
        }

        public Floor removeItems(List<Item> itemsToRemove) {
            List<Item> newItems = new ArrayList<>();
            newItems.addAll(items);
            newItems.removeAll(itemsToRemove);
            return new Floor(newItems);
        }
    }

    private static class StateKey {
        private final int elevatorPosition;
        private final List<ItemPair> itemPairs;

        public StateKey(int elevatorPosition, List<ItemPair> itemPairs) {
            this.elevatorPosition = elevatorPosition;
            Collections.sort(itemPairs, ItemPair.comparator());
            this.itemPairs = Collections.unmodifiableList(itemPairs);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            StateKey stateKey = (StateKey) o;

            if (elevatorPosition != stateKey.elevatorPosition) return false;
            if (!itemPairs.equals(stateKey.itemPairs)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = elevatorPosition;
            result = 31 * result + itemPairs.hashCode();
            return result;
        }
    }

    private static class Item {
        private final ItemType type;
        private final Substance substance;

        public Item(ItemType type, Substance substance) {
            this.type = type;
            this.substance = substance;
        }
    }

    private static enum ItemType {
        GENERATOR, MICROCHIP;
    }

    private static enum Substance {
        HYDROGEN, LITHIUM, POLONIUM, THULIUM, PROMETHIUM, RUTHENIUM, COBALT, ELERIUM, DILITHIUM;

        public static Substance forString(String string) {
            return Substance.valueOf(string.toUpperCase());
        }
    }

    private static class ItemPair {
        private final int generatorFloor;
        private final int microchipFloor;

        public ItemPair(int generatorFloor, int microchipFloor) {
            this.generatorFloor = generatorFloor;
            this.microchipFloor = microchipFloor;
        }

        public static Comparator<ItemPair> comparator() {
            return (firstPair, secondPair) -> {
                if (firstPair.generatorFloor == secondPair.generatorFloor) {
                    return firstPair.microchipFloor - secondPair.microchipFloor;
                } else {
                    return firstPair.generatorFloor - secondPair.generatorFloor;
                }
            };
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ItemPair itemPair = (ItemPair) o;

            if (generatorFloor != itemPair.generatorFloor) return false;
            if (microchipFloor != itemPair.microchipFloor) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = generatorFloor;
            result = 31 * result + microchipFloor;
            return result;
        }
    }
}
