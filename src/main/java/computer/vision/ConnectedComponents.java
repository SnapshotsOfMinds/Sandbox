package computer.vision;

import java.util.ArrayList;
import java.util.List;

/**
 * The labeling algorithm used to label parts of digits. Part of {@link RecognizeDigit}
 */
public class ConnectedComponents {
    private int[][] returnedOutput;

    /**
     * Constructor to run the algorithm two times.
     */
    ConnectedComponents(int[][] input) {
        runAlgorithm(input);
    }

    /**
     * The algorithm to run.
     *
     * @param input the current input array
     */
    private void runAlgorithm(int[][] input) {
        int[] parent = new int[30];
        int currentLabel = 1;
        int[][] output = new int[input.length][input[0].length];

        //Initialize output array
        for (int row = 0; row < output.length; row++) {
            for (int col = 0; col < output.length; col++) {
                output[row][col] = 0;
            }
        }

        //Initialize parent array
        for (int x = 0; x < parent.length; x++) {
            parent[0] = 0;
        }

        //The main labeling algorithm
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input.length; col++) {
                if (input[row][col] == 1) {
                    ArrayList<Integer> priorNeighbors = priorNeighbors(row, col, output);
                    if (priorNeighbors.isEmpty()) {
                        output[row][col] = currentLabel;
                        currentLabel++;
                    } else {
                        int minimum = min(priorNeighbors);
                        output[row][col] = minimum;
                        for (Integer x : priorNeighbors) {
                            if (x != minimum) {
                                union(minimum, x, parent);
                            }
                        }
                    }
                }
            }
        }

        //Second pass to change labels based on the roots
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input.length; col++) {
                if (input[row][col] == 1) {
                    output[row][col] = findRoot(output[row][col], parent);
                }
            }
        }

        returnedOutput = output;
    }

    /**
     * Accessor method for RecognizeDigit to use to obtain the labeled array
     *
     * @return the output of the algorithm
     */
    public int[][] getOutput() {
        return returnedOutput;
    }

    /**
     * Picks the minimum neighbor based on the other neighbors
     *
     * @param neighbors the list of neighbors for that point
     * @return the minimum
     */
    private int min(List<Integer> neighbors) {
        int currentMin = 0;
        for (Integer neighbor : neighbors) {
            if (neighbor < currentMin || currentMin == 0) {
                currentMin = neighbor;
            }
        }
        return currentMin;
    }

    /**
     * Find the neighbors of the current node
     *
     * @param row The row of the current point
     * @param col The column of the current point
     * @param output The output array to check
     * @return The list containing the nearest neighbors
     */
    private ArrayList<Integer> priorNeighbors(int row, int col, int[][] output) {
        ArrayList<Integer> nearestNeighbors = new ArrayList<>();

        //Point (0,0) and the top most row
        if (row == 0) {
            if (col == 0) {
                return nearestNeighbors;
            } else {
                if (output[row][col - 1] > 0) {
                    nearestNeighbors.add(output[row][col - 1]);
                }
            }
        }
        //The left most column
        else if (col == 0) {
            if (output[row - 1][col] > 0 && !nearestNeighbors.contains(output[row - 1][col])) {
                nearestNeighbors.add(output[row - 1][col]);
            }
            if (output[row - 1][col + 1] > 0 && !nearestNeighbors.contains(output[row - 1][col + 1])) {
                nearestNeighbors.add(output[row - 1][col + 1]);
            }
        }
        //The right most column
        else if (col == output.length - 1) {
            if (output[row][col - 1] > 0 && !nearestNeighbors.contains(output[row][col - 1])) {
                nearestNeighbors.add(output[row][col - 1]);
            }
            if (output[row - 1][col - 1] > 0 && !nearestNeighbors.contains(output[row - 1][col - 1])) {
                nearestNeighbors.add(output[row - 1][col - 1]);
            }
            if (output[row - 1][col] > 0 && !nearestNeighbors.contains(output[row - 1][col])) {
                nearestNeighbors.add(output[row - 1][col]);
            }
        }
        //Everything else
        else {
            if (output[row][col - 1] > 0 && !nearestNeighbors.contains(output[row][col - 1])) {
                nearestNeighbors.add(output[row][col - 1]);
            }
            if (output[row - 1][col - 1] > 0 && !nearestNeighbors.contains(output[row - 1][col - 1])) {
                nearestNeighbors.add(output[row - 1][col - 1]);
            }
            if (output[row - 1][col] > 0 && !nearestNeighbors.contains(output[row - 1][col])) {
                nearestNeighbors.add(output[row - 1][col]);
            }
            if (output[row - 1][col + 1] > 0 && !nearestNeighbors.contains(output[row - 1][col + 1])) {
                nearestNeighbors.add(output[row - 1][col + 1]);
            }
        }
        return nearestNeighbors;
    }

    /**
     * Find the root of the current node
     *
     * @param x The node to find the root of
     * @param parent The parent array
     * @return the root
     */
    private int findRoot(int x, int[] parent) {
        int j = x;
        while (parent[j] != 0) {
            j = parent[j];
        }
        return j;
    }

    /**
     * Based on the root of one node, see if the root of another node needs to change
     *
     * @param x The first root to check
     * @param y The root to be compared
     * @param parent The parent array
     */
    private void union(int x, int y, int[] parent) {
        int j = findRoot(x, parent);
        int k = findRoot(y, parent);
        if (j != k) {
            parent[k] = j;
        }
    }
}

