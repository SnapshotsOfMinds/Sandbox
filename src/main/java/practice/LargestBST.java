package practice;

public class LargestBST {
    public static void main(String... args) {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();
        Node node5 = new Node();
        Node node6 = new Node();
        Node node7 = new Node();
        Node node8 = new Node();
        Node node9 = new Node();
        Node node10 = new Node();

        node1.data = 1;
        node2.data = 2;
        node3.data = 3;
        node4.data = 4;
        node5.data = 5;
        node6.data = 6;
        node7.data = 7;
        node8.data = 8;
        node9.data = 9;
        node10.data = 10;

        node5.left = node6;
        node5.right = node8;
        node6.left = node2;
        node6.right = node4;
        node2.left = node1;
        node2.right = node3;
        node8.left = node7;
        node8.right = node10;
        node10.left = node9;

        int largestBSTSize = largestBST(node5);
        System.out.println("Size of largest BST  is " + largestBSTSize);
    }

    public static int largestBST(Node root) {
        MinMax m = largest(root);
        return m.size;
    }

    private static MinMax largest(Node root) {
        //if root is null return min as Integer.MAX and max as Integer.MIN
        if (root == null) {
            return new MinMax();
        }

        //postorder traversal of tree. First visit left and right then
        //use information of left and right to calculate largest BST.
        MinMax leftMinMax = largest(root.left);
        MinMax rightMinMax = largest(root.right);

        MinMax m = new MinMax();

        //if either of left or right subtree says its not BST or the data
        //of this node is not greater/equal than max of left and less than min of right
        //then subtree with this node as root will not be BST.
        //Return false and max size of left and right subtree to parent
        if (leftMinMax.isBST == false || rightMinMax.isBST == false || (leftMinMax.max > root.data || rightMinMax.min <= root.data)) {
            m.isBST = false;
            m.size = Math.max(leftMinMax.size, rightMinMax.size);
            return m;
        }

        //if we reach this point means subtree with this node as root is BST.
        //Set isBST as true. Then set size as size of left + size of right + 1.
        //Set min and max to be returned to parent.
        m.isBST = true;
        m.size = leftMinMax.size + rightMinMax.size + 1;

        //if root.left is null then set root.data as min else
        //take min of left side as min
        m.min = root.left != null ? leftMinMax.min : root.data;

        //if root.right is null then set root.data as max else
        //take max of right side as max.
        m.max = root.right != null ? rightMinMax.max : root.data;

        return m;
    }

    /**
     * Object of this class holds information which child passes back
     * to parent node.
     */
    static class MinMax {
        int min;
        int max;
        boolean isBST;
        int size;

        MinMax() {
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;
            isBST = true;
            size = 0;
        }
    }

    static class Node {
        int data;
        Node left;
        Node right;
    }
}