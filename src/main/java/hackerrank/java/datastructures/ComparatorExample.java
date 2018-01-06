package hackerrank.java.datastructures;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/java-comparator/problem
 */
public class ComparatorExample {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        Player[] player = new Player[n];
        Checker checker = new Checker();

        for (int i = 0; i < n; i++) {
            player[i] = new Player(scan.next(), scan.nextInt());
        }
        scan.close();

        Arrays.sort(player, checker);
        for (int i = 0; i < player.length; i++) {
            System.out.printf("%s %s\n", player[i].name, player[i].score);
        }
    }
}

class Player {
    String name;
    int score;

    Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
}

class Checker<T extends Player> implements Comparator<T> {
    @Override
    public int compare(Player p1, Player p2) {
        int scoreCompare = Integer.compare(p2.score, p1.score);
        if(scoreCompare == 0)
        {
            return p1.name.compareTo(p2.name);
        }
        return scoreCompare;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
