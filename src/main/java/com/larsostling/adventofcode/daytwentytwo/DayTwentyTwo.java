package com.larsostling.adventofcode.daytwentytwo;

import com.larsostling.adventofcode.Puzzle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayTwentyTwo implements Puzzle {

    @Override
    public String solvePartOne(String input) {
        List<Node> nodes = Arrays.stream(input.split("\n"))
                .skip(2)
                .map(DayTwentyTwo::parseNode)
                .collect(Collectors.toList());
        return String.valueOf(countViablePairs(nodes));
    }

    @Override
    public String solvePartTwo(String input) {
        List<Node> nodes = Arrays.stream(input.split("\n"))
                .skip(2)
                .map(DayTwentyTwo::parseNode)
                .collect(Collectors.toList());
        NodeMatrix nodeMatrix = createMatrix(nodes);
        Node emptyNode = nodes.stream().filter(Node::isEmpty).findFirst().get();
        int steps = 0;
        int x = emptyNode.x;
        int y = emptyNode.y;

        // Move hole up until you reach the wall
        while (y > 0 && !nodeMatrix.getNode(x, y - 1).isHighCapacityNode()) {
            steps++;
            y--;
        }

        // Move hole left until the wall opens
        while (y > 0 && nodeMatrix.getNode(x, y - 1).isHighCapacityNode()) {
            steps++;
            x--;
        }

        // Move hole to the top right
        steps += y + (nodeMatrix.width - 1 - x);

        // Move data to the top left shifting the hole around the data for each move
        steps += (nodeMatrix.width - 2) * 5;

        return String.valueOf(steps);
    }

    private static Node parseNode(String input) {
        String[] parts = input.split(" +");
        String[] nameParts = parts[0].split("-");
        return new Node(Integer.parseInt(nameParts[1].substring(1)),
                Integer.parseInt(nameParts[2].substring(1)),
                Integer.parseInt(parts[2].substring(0, parts[2].length() - 1)),
                Integer.parseInt(parts[3].substring(0, parts[3].length() - 1)));
    }

    private static int countViablePairs(List<Node> nodes) {
        int viablePairCount = 0;
        for (int i = 0; i < nodes.size(); i++) {
            Node firstNode = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node secondNode = nodes.get(j);
                if (areViable(firstNode, secondNode)) {
                    viablePairCount++;
                }
            }
        }
        return viablePairCount;
    }

    private static boolean areViable(Node firstNode, Node secondNode) {
        return firstNode.used > 0 && firstNode.used <= secondNode.available
                || secondNode.used > 0 && secondNode.used <= firstNode.available;
    }

    private static NodeMatrix createMatrix(List<Node> nodes) {
        int maxX = nodes.stream().mapToInt(node -> node.x).max().getAsInt();
        int maxY = nodes.stream().mapToInt(node -> node.y).max().getAsInt();
        Node[][] matrix = new Node[maxX + 1][maxY + 1];
        for (Node node : nodes) {
            matrix[node.x][node.y] = node;
        }
        return new NodeMatrix(matrix, maxX + 1, maxY + 1);
    }

    private static class Node {
        private final int x;
        private final int y;
        private final int used;
        private final int available;

        public Node(int x, int y, int used, int available) {
            this.x = x;
            this.y = y;
            this.used = used;
            this.available = available;
        }

        public boolean isEmpty() {
            return used == 0;
        }

        public boolean isHighCapacityNode() {
            return used > 100;
        }
    }

    private static final class NodeMatrix {
        private final Node[][] nodes;
        private final int width;
        private final int height;

        public NodeMatrix(Node[][] nodes, int width, int height) {
            this.nodes = nodes;
            this.width = width;
            this.height = height;
        }

        public Node getNode(int x, int y) {
            return nodes[x][y];
        }
    }
}
