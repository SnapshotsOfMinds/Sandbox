package hackerrank.java.advanced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * https://www.hackerrank.com/challenges/java-vistor-pattern/problem
 */
public class VisitorPattern {
    private static int[] values;
    private static Color[] colors;
    private static Map<Integer, Set<Integer>> map;

    public static void main(String... args) {
        Tree root = solve();
        SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
        ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
        FancyVisitor vis3 = new FancyVisitor();

        root.accept(vis1);
        root.accept(vis2);
        root.accept(vis3);

        int res1 = vis1.getResult();
        int res2 = vis2.getResult();
        int res3 = vis3.getResult();

        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }

    public static Tree solve() {
        try (Scanner sc = new Scanner(System.in)) {
            int numNodes = Integer.parseInt(sc.nextLine());
            values = new int[numNodes];
            colors = new Color[numNodes];
            map = new HashMap<>();
            for (int i = 0; i < numNodes; i++) {
                values[i] = sc.nextInt();
            }
            for (int i = 0; i < numNodes; i++) {
                colors[i] = sc.nextInt() == 0 ? Color.RED : Color.GREEN;
            }

            for (int i = 0; i < numNodes - 1; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();

                /* Edges are undirected: Add 1st direction */
                Set<Integer> uNeighbors = map.get(u);
                if (uNeighbors == null) {
                    uNeighbors = new HashSet<>();
                    map.put(u, uNeighbors);
                }
                uNeighbors.add(v);

                /* Edges are undirected: Add 2nd direction */
                Set<Integer> vNeighbors = map.get(v);
                if (vNeighbors == null) {
                    vNeighbors = new HashSet<>();
                    map.put(v, vNeighbors);
                }
                vNeighbors.add(u);
            }

            if (numNodes == 1) {
                return new TreeLeaf(values[0], colors[0], 0);
            }

            /* Create Tree */
            TreeNode root = new TreeNode(values[0], colors[0], 0);
            addChildren(root, 1);
            return root;
        }
    }

    private static void addChildren(TreeNode parent, Integer parentNum) {
        /* Get HashSet of children and loop through them */
        for (Integer treeNum : map.get(parentNum)) {
            map.get(treeNum).remove(parentNum); // removes the opposite arrow direction

            /* Add child */
            Set<Integer> grandChildren = map.get(treeNum);
            boolean childHasChild = (grandChildren != null && !grandChildren.isEmpty());
            Tree tree;
            if (childHasChild) {
                tree = new TreeNode(values[treeNum - 1], colors[treeNum - 1], parent.getDepth() + 1);
            } else {
                tree = new TreeLeaf(values[treeNum - 1], colors[treeNum - 1], parent.getDepth() + 1);
            }
            parent.addChild(tree);

            /* Recurse if necessary */
            if (tree instanceof TreeNode) {
                addChildren((TreeNode) tree, treeNum);
            }
        }
    }
}

class SumInLeavesVisitor extends TreeVis {
    int sum = 0;

    public int getResult() {
        return sum;
    }

    public void visitNode(TreeNode node) {
        // do nothing
    }

    public void visitLeaf(TreeLeaf leaf) {
        sum += leaf.getValue();
    }
}

class ProductOfRedNodesVisitor extends TreeVis {
    long product = 1;
    private final int M = 1000000007;

    public int getResult() {
        return (int) product;
    }

    public void visitNode(TreeNode node) {
        if (node.getColor() == Color.RED) {
            product = (product * node.getValue()) % M;
        }
    }

    public void visitLeaf(TreeLeaf leaf) {
        if (leaf.getColor() == Color.RED) {
            product = (product * leaf.getValue()) % M;
        }
    }
}

class FancyVisitor extends TreeVis {
    int greenValue = 0;
    int evenValue = 0;
    public int getResult() {
        return greenValue - evenValue;
    }

    public void visitNode(TreeNode node) {
        if(node.getDepth() % 2 == 0 || node.getDepth() == 0) {
            evenValue += node.getValue();
        }
    }

    public void visitLeaf(TreeLeaf leaf) {
        if(leaf.getColor() == Color.GREEN) {
            greenValue += leaf.getValue();
        }
    }
}

enum Color {
    RED, GREEN
}

abstract class Tree {
    private int value;
    private Color color;
    private int depth;

    public Tree(int value, Color color, int depth) {
        this.value = value;
        this.color = color;
        this.depth = depth;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public int getDepth() {
        return depth;
    }

    public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {
    private ArrayList<Tree> children = new ArrayList<>();

    public TreeNode(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitNode(this);

        for (Tree child : children) {
            child.accept(visitor);
        }
    }

    public void addChild(Tree child) {
        children.add(child);
    }
}

class TreeLeaf extends Tree {
    public TreeLeaf(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitLeaf(this);
    }
}

abstract class TreeVis {
    public abstract int getResult();

    public abstract void visitNode(TreeNode node);

    public abstract void visitLeaf(TreeLeaf leaf);
}