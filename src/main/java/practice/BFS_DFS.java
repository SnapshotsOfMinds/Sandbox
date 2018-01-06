package practice;

import java.util.LinkedList;
import java.util.Queue;

public class BFS_DFS {
    public static void main(String[] args) {
        new BFS_DFS();
    }

    private int vertices; // number of vertices in the graph
    private boolean[][] graph; // the graph as an adjacency matrix
    // G[i][j] is true if there is an edge from i to j

    private BFS_DFS() {
        setupGraph();

        // perform a DFS on the graph
        DFS();

        System.out.println("\n------------------------------\n");

        // perform a BFS on the graph
        BFS();
    }

    // initial setup of the graph
    private void setupGraph() {
        // set up a graph with 8 vertices that looks like:
		/*
			0 --- 1        5---6
			| \    \       |  /
			|  \    \      | /
			2   3----4     7

			Notice this graph has 2 components
		*/

        vertices = 8;
        graph = new boolean[vertices][vertices];

        graph[0][1] = graph[1][0] = true; // notice that for each edge G[i][j] == G[j][i]
        graph[0][2] = graph[2][0] = true;    // this means that the graph is undirected
        graph[0][3] = graph[3][0] = true;
        graph[1][4] = graph[4][1] = true;
        graph[3][4] = graph[4][3] = true;
        graph[5][6] = graph[6][5] = true;
        graph[5][7] = graph[7][5] = true;
        graph[6][7] = graph[7][6] = true;
    }

    // perform a DFS on the graph G
    private void DFS() {
        boolean[] visited = new boolean[vertices]; // a visited array to mark which vertices have been visited while doing the DFS
        int numComponents = 0; // the number of components in the graph

        // do the DFS from each node not already visited
        for (int i = 0; i < vertices; ++i) {
            if (!visited[i]) {
                ++numComponents;
                System.out.printf("Starting a DFS for component %d starting at node %d\n", numComponents, i);

                DFS(i, visited);
            }
        }

        System.out.println();
        System.out.printf("Finished with DFS - found %d components.\n", numComponents);
    }

    // perform a DFS starting at node at (works recursively)
    private void DFS(int location, boolean[] visited) {
        System.out.printf("At node %d in the DFS\n", location);

        // mark that we are visiting this node
        visited[location] = true;

        // recursively visit every node connected to this that we have not already visited
        for (int i = 0; i < vertices; ++i) {
            if (graph[location][i] && !visited[i]) {
                System.out.printf("Going to node %d...", i);
                DFS(i, visited);
            }
        }

        System.out.printf("Done processing node %d\n", location);
    }

    // perform a BFS on the graph G
    private void BFS() {
        boolean[] visited = new boolean[vertices]; // a visited array to mark which vertices have been visited while doing the BFS

        int numComponents = 0; // the number of components in the graph

        // do the BFS from each node not already visited
        for (int i = 0; i < vertices; ++i) {
            if (!visited[i]) {
                ++numComponents;
                System.out.printf("Starting a BFS for component %d starting at node %d\n", numComponents, i);

                BFS(i, visited);
            }
        }

        System.out.println();
        System.out.printf("Finished with BFS - found %d components.\n", numComponents);
    }

    // perform a BFS starting at node start
    private void BFS(int start, boolean[] visited) {
        Queue<Integer> nodesToProcess = new LinkedList<>(); // A queue to process nodes

        // start with only the start node in the queue and mark it as visited
        nodesToProcess.offer(start);
        visited[start] = true;

        // continue searching the graph while still nodes in the queue
        while (!nodesToProcess.isEmpty()) {
            int location = nodesToProcess.poll(); // get the head of the queue
            System.out.printf("At node %d in the BFS\n", location);

            // add any unseen nodes to the queue to process, then mark them as visited so they don't get re-added
            for (int i = 0; i < vertices; ++i) {
                if (graph[location][i] && !visited[i]) {
                    nodesToProcess.offer(i);
                    visited[i] = true;

                    System.out.printf("Adding node %d to the queue in the BFS\n", i);
                }
            }

            System.out.printf("Done processing node %d\n", location);
        }

        System.out.printf("Finished with the BFS from start node %d\n", start);
    }
}
