import java.util.LinkedList;
import java.util.Queue;

public class BST <T extends Comparable<T>> {

    // number of nodes in bst
    private int nodeCount = 0;

    // root node
    private Node root = null;

    private class Node {
        T data;
        Node left, right;
        public Node(Node left, Node right, T elem) {
            this.data = elem;
            this.left = left;
            this.right = right;
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return nodeCount;
    }

    // returning true if element was added successfully
    public boolean add(T elem) {

        // if element already exists in bst
        if (contains(elem)) {
            return false;
        }
        // otherwise add element to bst
        else {
            root = add(root, elem);
            nodeCount++;
            return true;
        }
    }

    // private method to recursively add element in the bst
    private Node add(Node node, T elem) {

        // base case: find a leaf node
        if(node == null) {
            node = new Node(null, null, elem);
        } else {
            // place lower elements value in the left subtree
            if(elem.compareTo(node.data) < 0) {
                node.left = add(node.left, elem);
            } else {
                node.right = add(node.right, elem);
            }
        }
        return node;
    }

    public boolean remove(T elem) {
        if(contains(elem)) {
            root = remove(root, elem);
            nodeCount--;
            return true;
        }
        return false;
    }

    private Node remove(Node node, T elem) {
        if(node == null)
            return null;

        int cmp = elem.compareTo(node.data);

        // Dig into left subtree, the value we are looking for is smaller than the current value
        if (cmp < 0) {
            node.left = remove(node.left, elem);
        }

        // Dig into right subtree, the value we are looking for is bigger than the current value
        else if (cmp > 0) {
            node.right = remove(node.right, elem);
        }

        // found the node we wish to remove
        else {

            // this is the case with only a right subtree or no subtree at all. In this situation
            // just swap node we wish to remove with its right child
            if (node.left == null) {
                Node rightChild = node.right;

                node.data = null;
                node = null;

                return rightChild;
            }

            // this is the case with only a left subtree or not subtree ar all. In this situation
            // just swap node we wish to remove with its left child
            else if (node.right == null) {
                Node leftChild = node.left;

                node.data = null;
                node = null;

                return leftChild;
            }

            // this is the case when there is both left and right subtrees.
            // when removing a node from bst with two links the successor of the node being removed
            // can either be the largest value in the left subtree or the smallest value in the right
            // subtree. In this implementation we have decided to find the smallest value in the right subtree
            // which can be found by traversing as far left as possible in the right subtree.
            else {

                // find the left most node in right subtree
                Node tmp = digLeft(node.right);

                // swap the data
                node.data = tmp.data;

                // go into right subtree and remove the leftmost node we found and swapped data with. This prevents
                // us from having two nodes in our tree with the same value.
                node.right = remove(node.right, tmp.data);
            }
        }
        return node;
    }

    // helper
    private Node digLeft(Node node) {
        Node curr = node;
        while (curr.left != null)
            curr = curr.left;
        return curr;
    }

    private Node digRight(Node node) {
        Node curr = node;
        while(curr.right != null)
            curr = curr.right;
        return curr;
    }

    public boolean contains(T elem) {
        return contains(root, elem);
    }

    private boolean contains(Node node, T elem) {
        if (node == null)
            return false;

        int cmp = elem.compareTo(node.data);

        if (cmp < 0)
            return contains(node.left, elem);

        else if (cmp > 0)
            return contains(node.right, elem);

        else
            return true;
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if(node == null)
            return 0;

        return Math.max(height(node.left), height(node.right)) + 1;
    }

    public void preorder(Node node) {
        if (node == null)
            return;

        System.out.print(node.data + " ");
        preorder(node.left);
        preorder(node.right);
    }

    public void inorder(Node node) {
        if (node == null)
            return;

        inorder(node.left);
        System.out.println(node.data + " ");
        inorder(node.right);
    }

    public void postorder(Node node) {
        if (node == null)
            return;

        postorder(node.left);
        postorder(node.right);
        System.out.println(node.data + " ");
    }

    public void levelOrder(Node node) {
        Queue<Node> q = new LinkedList<>();
         q.add(node);

         while(!q.isEmpty()) {
             Node curr = q.poll();
             System.out.print(curr + " ");

             if (curr.left != null)
                 q.add(curr.left);

             if (curr.right != null)
                 q.add(curr.right);
         }
    }
    //test
}
