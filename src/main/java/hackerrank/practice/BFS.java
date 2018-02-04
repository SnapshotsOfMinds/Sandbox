package hackerrank.practice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/linkedin-practice-graph-theory-bfs/problem
 */
public class BFS {
    public static Queue<Integer> toBeVisited = new LinkedList<>();

    public static void main(String... args) {
        try (Scanner sc = new Scanner(System.in)) {
            int queries = sc.nextInt();
            while (queries-- > 0) {
                int nodes = sc.nextInt();
                int edges = sc.nextInt();
                int[][] matrix = new int[nodes + 1][nodes + 1];

                for (int i = 0; i < edges; i++) {
                    int u = sc.nextInt();
                    int v = sc.nextInt();
                    matrix[u][v] = 6;
                    matrix[v][u] = 6;
                }
                int start = sc.nextInt();
                compute(matrix, start, nodes);
            }
        }
    }

    private static void compute(int[][] matrix, int start, int nodes) {
        boolean visited[] = new boolean[nodes + 1];
        visited[start] = true;
        for (int i = 1; i <= nodes; i++) {
            if (matrix[start][i] != 0 && i != start) {
                toBeVisited.offer(i);
            }
        }

        int cost = 12;
        ArrayList<Integer> poppedlist = new ArrayList<>();
        while (!toBeVisited.isEmpty()) {
            int child = toBeVisited.poll();
            visited[child] = true;
            for (int i = 1; i <= nodes; i++) {

                if (matrix[child][i] != 0 && matrix[start][i] == 0 && !visited[i]) {
                    poppedlist.add(i);
                    matrix[start][i] = cost;
                }
            }
            if (toBeVisited.isEmpty()) {
                cost += 6;
                for (int t = 0; t < poppedlist.size(); t++) {
                    toBeVisited.offer(poppedlist.get(t));
                }
                poppedlist.clear();
            }
        }

        for (int i = 1; i <= nodes; i++) {
            if (i != start) {
                if (matrix[start][i] == 0) {
                    System.out.print("-1 ");
                } else {
                    System.out.print(matrix[start][i] + " ");
                }
            }
        }
        System.out.println();
    }
}
