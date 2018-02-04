package hackerrank.practice;

import java.util.Stack;

/**
 * https://www.hackerrank.com/challenges/tree-postorder-traversal/problem
 */
public class PostOrderTraversal {
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

        postOrder(node1);
        System.out.println();
        postOrderLoop(node1);
        System.out.println();
//        morrisTraversal(node1);
    }

    static void postOrder(Node root) {
        if (root.left != null) {
            postOrder(root.left);
        }
        if (root.right != null) {
            postOrder(root.right);
        }
        System.out.print(root.data + " ");
    }

    //TODO Implement
//    static void morrisTraversal(Node root) {
//        Node current, pre;
//
//        if (root == null) {
//            return;
//        }
//
//        current = root;
//        while (current != null) {
//            if (current.left == null) {
//                System.out.print(current.data + " ");
//                current = current.right;
//            } else {
//                /* Find the inorder predecessor of current */
//                pre = current.left;
//                while (pre.right != null && pre.right != current) {
//                    pre = pre.right;
//                }
//
//                /* Make current as right child of its inorder predecessor */
//                if (pre.right == null) {
//                    pre.right = current;
//                    current = current.left;
//                }
//
//                 /* Revert the changes made in if part to restore the
//                    original tree i.e.,fix the right child of predecssor*/
//                else {
//                    pre.right = null;
//                    current = current.right;
//                }   /* End of if condition pre->right == NULL */
//
//            } /* End of if condition current->left == NULL*/
//
//        } /* End of while */
//    }

    static void postOrderLoop(Node root) {
        // Create two stacks
        Stack<Node> s1 = new Stack<>();
        Stack<Node> s2 = new Stack<>();

        if (root == null) {
            return;
        }

        // push root to first stack
        s1.push(root);

        // Run while first stack is not empty
        while (!s1.isEmpty()) {
            // Pop an item from s1 and push it to s2
            Node temp = s1.pop();
            s2.push(temp);

            // Push left and right children of
            // removed item to s1
            if (temp.left != null) {
                s1.push(temp.left);
            }
            if (temp.right != null) {
                s1.push(temp.right);
            }
        }

        // Print all elements of second stack
        while (!s2.isEmpty()) {
            Node temp = s2.pop();
            System.out.print(temp.data + " ");
        }
    }

    static class Node {
        int data;
        Node left;
        Node right;
    }
}
