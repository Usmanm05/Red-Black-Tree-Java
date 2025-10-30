/**
 * Class that builds a BinarySearchTree and implements the SortedCollection interface
 * */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {
    // root node of the tree
    protected BSTNode<T> root;

    // size of the tree
    private int size;

    /**
     * Constructor to create a new empty tree
     */
    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Inserts a new data value into the sorted collection.
     *
     * @param data the new value being insterted
     * @throws NullPointerException if data argument is null, we do not allow null values to be stored
     *                              within a SortedCollection
     */
    @Override
    public void insert(Comparable data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException();
        }
        // Creates new node with data given
        BSTNode<T> newNode = new BSTNode<T>((T) data);
        // if tree is empty then newNode becomes the root
        if (root == null) {
            root = newNode;
        } // if tree isn't empty then we recursively search through the tree and insert node in correct
        // spot
        else {
            insertHelper(newNode, root);
        }
    }

    /**
     * Performs the naive binary search tree insert algorithm to recursively insert the provided
     * newNode (which has already been initialized with a data value) into the provided tree/subtree.
     * When the provided subtree is null, this method does nothing.
     */
    protected void insertHelper(BSTNode<T> newNode, BSTNode<T> subtree) {
        // method does nothing if subtree is null
        if (subtree == null) {
            //System.out.println("Wrong");
            return;
        }
        // compares newNode to the current nodes left child
        if (newNode.getData().compareTo(subtree.getData()) < 0) {
            // if there's space in left child then newNode is added there
            if (subtree.getLeft() == null) {
                subtree.setLeft(newNode);
                newNode.setUp(subtree);
            }
            // method is recursively called to go left again
            insertHelper(newNode, subtree.getLeft());

        } // compares newNode to the current nodes right child
        else if (newNode.getData().compareTo(subtree.getData()) > 0) {
            // if there's space in right child then newNode is added there
            if (subtree.getRight() == null) {
                subtree.setRight(newNode);
                newNode.setUp(subtree);
            }
            // method is rec
            insertHelper(newNode, subtree.getRight());
        }

    }

    /**
     * Check whether data is stored in the tree.
     *
     * @param data the value to check for in the collection
     * @return true if the collection contains data one or more times, and false otherwise
     */
    @Override
    public boolean contains(Comparable data) {
        // if data is null then can't be in collection
        if (data == null) {
            return false;
        }
        BSTNode<T> newNode = new BSTNode<T>((T) data);
        return containsHelper(newNode, root);
    }

    /**
     * Private helper method that check whether data is stored in the tree.
     *
     * @param newNode the node to be added to the collection
     * @param subtree the subtree we're looking through
     * @return true if the collection contains data one or more times, and false otherwise
     */
    private boolean containsHelper(BSTNode<T> newNode, BSTNode<T> subtree) {
        // if node is null then can't be contained
        if (subtree == null) {
            return false;
        }
        // if datas match then data is stored in the tree
        if (newNode.getData().compareTo(subtree.getData()) == 0) {
            return true;
        } // if data is smaller than subtree then continue going left
        else if (newNode.getData().compareTo(subtree.getData()) < 0) {
            return containsHelper(newNode, subtree.getLeft());
        } // if data is larger than subtree then continue going right
        else {
            return containsHelper(newNode, subtree.getRight());
        }
    }

    /**
     * Counts the number of values in the collection, with each duplicate value being counted
     * separately within the value returned.
     *
     */
    public int size() {
        return sizeHelper(root);
    }

    /**
     * Helper method that counts the number of values in the collection, with each duplicate value
     * being counted separately within the value returned.
     */
    private int sizeHelper(BSTNode<T> root) {
        // root is null so tree is empty
        if (root == null) {
            return 0;
        }
        // recursively calls helper method to go through each node in the left and right subtrees
        return 1 + sizeHelper(root.getLeft()) + sizeHelper(root.getRight());
    }

    /**
     * Checks if the collection is empty.
     */
    public boolean isEmpty() {
        if (root == null) {
            return true;
        }
        return false;
    }

    /**
     * Removes all values and duplicates from the collection.
     */
    public void clear() {
        root = null;
    }


    /**
     * Test method that tests an integer based BinarySearchTree that inserts nodes in order from root,
     * left, then right child.
     */
    public boolean test1(BinarySearchTree<Integer> newTree) {
        newTree.insert(50);
        newTree.insert(20);
        newTree.insert(70);
        // tests if size is correct
        if (newTree.size() != 3 || newTree.isEmpty()) {
            System.out.println(newTree.isEmpty());
            return false;
        }
        // checks if contains method works correctly
        if (!newTree.contains(50) || !newTree.contains(20) || !newTree.contains(70)) {
            return false;
        }
        // checks that nodes are in right place
        if (newTree.root.getData() != 50 || newTree.root.getLeft().getData() != 20
                || newTree.root.getRight().getData() != 70) {
            return false;
        }
        // checks if clear method works correctly
        newTree.clear();
        if (newTree.size() != 0 || !newTree.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * Test method that tests a string based BinarySearchTree that inserts nodes in order from root,
     * left, then left again.
     */
    public boolean test2(BinarySearchTree<String> newTree) {
        newTree.insert("Miles");
        newTree.insert("Fred");
        newTree.insert("Blake");
        // tests if size is correct
        if (newTree.size() != 3 || newTree.isEmpty()) {
            return false;
        }
        // checks if contains method works correctly
        if (!newTree.contains("Miles") || !newTree.contains("Fred") || !newTree.contains("Blake")) {
            return false;
        }
        // checks that nodes are in right place
        if (!newTree.root.getData().equals("Miles") || !newTree.root.getLeft().getData().equals("Fred")
                || !newTree.root.getLeft().getLeft().getData().equals("Blake")) {
            return false;
        }
        // checks to ensure nothing is added to the right child
        if (newTree.root.getRight() != null) {
            return false;
        }
        // checks if clear method works correctly
        newTree.clear();
        if (newTree.size() != 0 || !newTree.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Test method that tests an integer based BinarySearchTree that inserts nodes from root, right,
     * then left child.
     */
    public boolean test3(BinarySearchTree<Integer> newTree) {
        newTree.insert(34);
        newTree.insert(90);
        newTree.insert(5);
        // tests if size is correct
        if (newTree.size() != 3 || newTree.isEmpty()) {
            return false;
        }
        // checks if contains method works correctly
        if (!newTree.contains(34) || !newTree.contains(90) || !newTree.contains(5)) {
            return false;
        }
        // checks that nodes are in right place
        if (newTree.root.getData() != 34 || newTree.root.getLeft().getData() != 5
                || newTree.root.getRight().getData() != 90) {
            return false;
        }
        // checks if clear method works correctly
        newTree.clear();
        if (newTree.size() != 0 || !newTree.isEmpty()) {
            return false;
        }
        return true;
    }


    public static void main(String args[]) {
        BinarySearchTree<Integer> tree1 = new BinarySearchTree<Integer>();
        BinarySearchTree<String> tree2 = new BinarySearchTree<String>();
        BinarySearchTree<Integer> tree3 = new BinarySearchTree<Integer>();
        System.out.println("Test 1: " + tree1.test1(tree1));
        System.out.println("Test 2: " + tree2.test2(tree2));
        System.out.println("Test 3: " + tree3.test3(tree3));
    }
}
