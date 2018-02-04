package practice;

public class DetermineBST {
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

        System.out.println(isBST(node1, Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    static boolean isBST(Node root, int min, int max) {
        if (root == null) {
            return true;
        }
        if (root.data <= min || root.data > max) {
            return false;
        }

        return isBST(root.left, min, root.data) && isBST(root.right, root.data, max);
    }

    static class Node {
        int data;
        Node left;
        Node right;
    }
}
