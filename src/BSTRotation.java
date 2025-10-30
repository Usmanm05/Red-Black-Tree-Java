
public class BSTRotation<T> extends BinarySearchTree {
    protected BSTNode root;

    /**
     * Performs the rotation operation on the provided nodes within this tree. When the provided child
     * is a left child of the provided parent, this method will perform a right rotation. When the
     * provided child is a right child of the provided parent, this method will perform a left
     * rotation. When the provided nodes are not related in one of these ways, this method will either
     * throw a NullPointerException: when either reference is null, or otherwise will throw an
     * IllegalArgumentException.
     *
     * @param child  is the node being rotated from child to parent position
     * @param parent is the node being rotated from parent to child position
     * @throws NullPointerException     when either passed argument is null
     * @throws IllegalArgumentException when the provided child and parent nodes are not initially
     *                                  (pre-rotation) related that way
     */
    protected void rotate(BSTNode<T> child, BSTNode<T> parent)
            throws NullPointerException, IllegalArgumentException {
        // child and parent can't be null because rotation wouldn't work
        if (child == null || parent == null) {
            throw new NullPointerException();
        }
        // if doesn't match requirement then rotation wouldn't be right
        if (parent.getRight()!=child && parent.getLeft()!=child) {
            throw new IllegalArgumentException();
        }
        // child is right child of parent so we rotate left
        if ( parent.getRight()!=null && parent.getRight().equals(child)) {
            rotateLeft(child, parent);
        }
        // child is left child of parent so we rotate right
        if (parent.getLeft()!=null && parent.getLeft().equals(child)) {
            rotateRight(child, parent);
        }
    }

    /**
     * Private helper method that rotates our nodes to the left.
     *
     * @param child  is the node being rotated from child to parent position
     * @param parent is the node being rotated from parent to child position
     */
    private void rotateLeft(BSTNode<T> child, BSTNode<T> parent) {
        BSTNode originalChild = child;
        BSTNode originalParent = parent;
        BSTNode originalChildLeft = child.getLeft();
        // sets the child to be in the position of the originalParent
        child.setUp(originalChild);
        // if the original parent doesn't have a parent then the root is child
        if (originalParent.getUp() == null) {
            root = child;
        } // if the original parent does have a parent we check if our subtree is to the left or right
        // of the original grandparent
        else {
            if (originalParent.getUp().getRight() == parent) {
                parent.getUp().setRight(child);
            } else {
                parent.getUp().setLeft(child);
            }
        }

        // sets child's reference's left child to be the originalParent
        child.setLeft(originalParent);
        // sets parent's reference's right child to be the originalChild's left child
        parent.setRight(originalChildLeft);

        // if the originalChild's left child is not null then we set its parent to be the
        // originalParent
        if (originalChildLeft != null) {
            originalChildLeft.setUp(originalParent);
        }
        // set parent's parent to be the originalChild
        parent.setUp(originalChild);
    }

    /**
     * Private helper method that rotates our nodes to the right.
     *
     * @param child  is the node being rotated from child to parent position
     * @param parent is the node being rotated from parent to child position
     */
    private void rotateRight(BSTNode<T> child, BSTNode<T> parent) {
        BSTNode originalChild = child;
        BSTNode originalParent = parent;
        BSTNode originalChildRight = child.getRight();
        // sets the child to be in the position of the originalParent
        child.setUp(originalParent.getUp());
        // if the original parent doesn't have a parent then the root is child
        if (originalParent.getUp() == null) {
            root = child;
        } // if the original parent does have a parent we check if our subtree is to the left or right
        // of the original grandparent
        else {
            if (originalParent.getUp().getLeft() == parent) {
                parent.getUp().setLeft(child);
            } else {
                parent.getUp().setRight(child);
            }
        }

        // sets child's reference's right child to be the originalParent
        child.setRight(originalParent);
        // sets parent's reference's left child to be the originalChild's right child
        parent.setLeft(originalChildRight);

        // if the originalChild's right child is not null then we set its parent to be the
        // originalParent
        if (originalChildRight != null) {
            originalChildRight.setUp(originalParent);
        }
        // set parent's parent to be the originalChild
        parent.setUp(originalChild);


    }

    /**
     * Tester method that tests for a left rotation of 3 levels.
     */
    public static boolean test1() {
        // creates an integer based BSTRotation for a left rotation
        BSTRotation<Integer> leftRotation = new BSTRotation<Integer>();
        // creates an integer based BinarySearchTree
        BinarySearchTree<Integer> newTree = new BinarySearchTree<Integer>();
        newTree.insert(85);
        newTree.insert(54);
        newTree.insert(97);
        newTree.insert(32);
        newTree.insert(72);
        newTree.insert(89);
        newTree.insert(109);
        // rotates the right child of the root and the root to the left
        leftRotation.rotate(newTree.root.getRight(), newTree.root);
        // check to ensure the tree is correct after rotation
        if (!leftRotation.root.toLevelOrderString().equals("[ 97, 85, 109, 54, 89, 32, 72 ]")){
            return false;
        }
        return true;
    }

    /**
     * Tester method that tests for a right rotation of 3 levels.
     */

    public static boolean test2() {
        // creates an integer based BSTRotation for a right rotation
        BSTRotation<Integer> rightRotation = new BSTRotation<Integer>();
        // creates an integer based BinarySearchTree
        BinarySearchTree<Integer> newTree = new BinarySearchTree<Integer>();
        newTree.insert(57);
        newTree.insert(34);
        newTree.insert(8);
        newTree.insert(89);
        newTree.insert(54);
        newTree.insert(92);
        newTree.insert(75);
        // rotates the root's left child's left child and the root's left child to the right
        rightRotation.rotate(newTree.root.getLeft().getLeft(), newTree.root.getLeft());
        // check to ensure the tree is correct after rotation
        if (!newTree.root.toLevelOrderString().equals("[ 57, 8, 89, 34, 75, 92, 54 ]")) {
            System.out.println(rightRotation.root.getData());
            return false;
        }
        return true;
    }

    /**
     * Tester method that tests for the exceptions to be thrown in the rotation method.
     */
    public static boolean test3() {
        // creates an integer based BSTRotation for a right rotation
        BSTRotation<Integer> rightRotation = new BSTRotation<Integer>();
        // creates an integer based BinarySearchTree
        BinarySearchTree<Integer> newTree = new BinarySearchTree<Integer>();
        newTree.insert(75);
        newTree.insert(34);
        newTree.insert(95);
        newTree.insert(103);
        newTree.insert(12);
        // tries to rotate null values to see if null pointer exception is thrown
        try {
            rightRotation.rotate(null, null);
            return false;
        } catch (NullPointerException e) {
        } catch (Exception e) {
            return false;
        }
        // tries to rotate two values that don't have a parent child relation to ensure illegal
        // argument exception is thrown
        try {
            rightRotation.rotate(newTree.root, newTree.root.getRight().getRight());
            return false;
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Test 1: " + test1());
        System.out.println("Test 2: " + test2());
        System.out.println("Test 3: " + test3());
    }
}
