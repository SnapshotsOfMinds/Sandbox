package hackerrank.practice;

import java.util.Stack;

/**
 * https://www.hackerrank.com/challenges/tree-preorder-traversal/problem
 */
public class PreOrderTraversal {
    /**
     * <pre>
     * Uses tree
     *      1
     *       \
     *        2
     *         \
     *          5
     *        /  \
     *       3    6
     *        \
     *         4
     * </pre>
     */
    public static void main(String... args) {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();
        Node node5 = new Node();
        Node node6 = new Node();

        node1.data = 1;
        node1.left = null;
        node1.right = node2;

        node2.data = 2;
        node2.left = null;
        node2.right = node5;

        node3.data = 3;
        node3.left = null;
        node3.right = node4;

        node4.data = 4;
        node4.left = null;
        node4.right = null;

        node5.data = 5;
        node5.left = node3;
        node5.right = node6;

        node6.data = 6;
        node6.left = null;
        node6.right = null;

        preOrder(node1);
        System.out.println();
        preOrderLoop(node1);
        System.out.println();
        morrisTraversal(node1);
    }

    static void preOrder(Node root) {
        System.out.print(root.data + " ");
        if (root.left != null) {
            preOrder(root.left);
        }
        if (root.right != null) {
            preOrder(root.right);
        }
    }

    static void morrisTraversal(Node root) {
        Node pre;
        if (root == null) {
            return;
        }
        Node curr = root;
        while (curr != null) {
            if (curr.left == null) {
                System.out.print(curr.data + " ");
                curr = curr.right;
            } else {
                pre = curr.left;
                while (pre.right != null && pre.right != curr) {
                    pre = pre.right;
                }
                if (pre.right == null) {
                    pre.right = curr;
                    System.out.print(curr.data + " ");
                    curr = curr.left;
                } else {
                    pre.right = null;
                    curr = curr.right;
                }
            }
        }
    }

    static void preOrderLoop(Node root) {
        // Base Case
        if (root == null) {
            return;
        }

        // Create an empty stack and push root to it
        Stack<Node> nodeStack = new Stack<>();
        nodeStack.push(root);

        /* Pop all items one by one. Do following for every popped item
         a) print it
         b) push its right child
         c) push its left child
         Note that right child is pushed first so that left is processed first */
        while (nodeStack.empty() == false) {

            // Pop the top item from stack and print it
            Node node = nodeStack.peek();
            System.out.print(node.data + " ");
            nodeStack.pop();

            // Push right and left children of the popped node to stack
            if (node.right != null) {
                nodeStack.push(node.right);
            }
            if (node.left != null) {
                nodeStack.push(node.left);
            }
        }
    }

    static class Node {
        int data;
        Node left;
        Node right;
    }
}
