import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Class that builds a tree that follows the rules of a red black tree.
 * */
public class RedBlackTree<T extends Comparable<T>>  extends BSTRotation<T> {
    //protected BSTNode<T> root;

    /**
     * Checks if a new red node in the RedBlackTree causes a red property violation
     * by having a red parent. If this is not the case, the method terminates without
     * making any changes to the tree. If a red property violation is detected, then
     * the method repairs this violation and any additional red property violations
     * that are generated as a result of the applied repair operation.
     * @param newRedNode a newly inserted red node, or a node turned red by previous repair
     */
    protected void ensureRedProperty(RBTNode<T> newRedNode) {
        // saves parent node to variable
        RBTNode<T> parent = newRedNode.getUp();

        // if parent is null then there can be no violation
        if (parent==null){
            return;
        }
        // saves grandparent node to variable
        RBTNode<T> grandParent = newRedNode.getUp().getUp();
        // creates aunt variable
        RBTNode<T> aunt = null;

        // no issue can occur if parent is not red
        if (!parent.isRed){
            return;
        }

        // if the new node is the second node being added then there's no grandparent so you just
        // flip colors
        if (grandParent == null) {
            parent.flipColor();
            this.root = parent;
            ensureRedProperty(parent);
        }
        // if aunt is on left side
        if (grandParent.getLeft()!=null && grandParent.getLeft()!=parent){
            aunt = grandParent.getLeft();
            // case 1 where aunt is red
            if (aunt.isRed) {
                aunt = grandParent.getLeft();
                parent.flipColor();
                aunt.flipColor();
                grandParent.flipColor();
                ensureRedProperty(grandParent);

            }// case 3 where aunt is black
            else if (!grandParent.getLeft().isRed) {
                rotate(newRedNode, parent);
                rotate(newRedNode, grandParent);
                grandParent.flipColor();
                newRedNode.flipColor();
                ensureRedProperty(grandParent);

            }
        }
        // if aunt is on right side
        else if (grandParent.getRight()!=null && grandParent.getRight()!=parent){
            aunt = grandParent.getRight();
            // case 1 where aunt is red
            if (grandParent.getRight().isRed) {
                aunt = grandParent.getRight();
                parent.flipColor();
                aunt.flipColor();
                grandParent.flipColor();
                ensureRedProperty(grandParent);
            }// case 3 where aunt is black
            else if (!grandParent.getRight().isRed) {
                rotate(newRedNode, parent);
                rotate(newRedNode, grandParent);
                grandParent.flipColor();
                newRedNode.flipColor();
                ensureRedProperty(grandParent);
            }
        } // if aunt is null
        else if (grandParent.getRight()==null || grandParent.getLeft()==null){
            case2Helper(newRedNode,parent,grandParent);
        }
    }

    private void case2Helper(RBTNode<T> newRedNode, RBTNode<T> parent, RBTNode<T> grandParent) {
        // right-right situation
        if (parent.getRight()==newRedNode && grandParent.getRight() != null && grandParent.getRight()==parent){
            rotate(parent,grandParent);
            parent.flipColor();
            grandParent.flipColor();
        } // left-left situation
        else if(parent.getLeft()==newRedNode && grandParent.getLeft() != null && grandParent.getLeft()==parent){
            rotate(parent,grandParent);
            parent.flipColor();
            grandParent.flipColor();
        } // right-left situation
        else if (parent.getLeft() == newRedNode && grandParent.getRight() != null && grandParent.getRight() == parent) {
            rotate(newRedNode, parent);
            rotate(newRedNode, grandParent);
            newRedNode.flipColor();
            grandParent.flipColor();
        } // left-right situation
        else if (parent.getRight() == newRedNode && grandParent.getLeft() != null && grandParent.getLeft() == parent) {
            rotate(newRedNode, parent);
            rotate(newRedNode, grandParent);
            newRedNode.flipColor();
            grandParent.flipColor();
        }

        // if the node being added has no parent after rotation, make it the root
        if (newRedNode.getUp() == null) {
            this.root = newRedNode;
        }
    }

    /**
     * 1. overrides the insert method inherited from BinarySearchTree.
     * 2. uses BinarySearchTree’s insertHelper method to insert a node
     * with a the new value into the tree.
     * 3. ensures that any node inserted into the tree is a red node of
     * type RBTNode.
     * 4. ensures that ensureRedProperty is called for every newly
     * inserted red node (with the exception of the root node) to
     * identify and repair a potential red property violation.
     * 5. sets the color of the root node to black after the insertion
     * of a new red node and any potential red property repair
     * operations to ensure a black root node.
     */
    @Override
    public void insert(Comparable data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException();
        }
        // Creates new node with data given
        RBTNode<T> newNode = new RBTNode<T>((T) data);
        // if tree is empty then newNode becomes the root
        if (root == null) {
            root = newNode;
        } // if tree isn't empty then we recursively search through the tree and insert node in correct
        // spot
        else {
            insertHelper(newNode, (RBTNode<T>) root);
            ensureRedProperty(newNode);
        }
        ((RBTNode<T>)this.root).isRed = false;

    }


    /**
     * Performs the naive binary search tree insert algorithm to recursively insert the provided
     * newNode (which has already been initialized with a data value) into the provided tree/subtree.
     * When the provided subtree is null, this method does nothing.
     */
    protected void insertHelper(RBTNode<T> newNode, RBTNode<T> subtree) {
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
            // method is recursively called to go right again
            insertHelper(newNode, subtree.getRight());
        }

    }
    /**
     * Tester that tests if there's a situation with a red aunt
     * */
    @Test
    public void testCase1(){
        RedBlackTree<T> test1 = new RedBlackTree<>();
        test1.insert("J");
        test1.insert("F");
        test1.insert("W");
        test1.insert("A");
        test1.insert("H");
        test1.insert("Y");
        test1.insert("G");

        String expected = "[ J(b), F(r), W(b), A(b), H(b), Y(r), G(r) ]";
        String actual = test1.root.toLevelOrderString();
        assertEquals(expected,actual,test1.toString());

    }
    /**
     * Tester that tests if there's a situation with a null aunt from the quiz question 3
     * */
    @Test
    public void testCase2() {
        RedBlackTree<T> test2 = new RedBlackTree<>();

        test2.insert("S");
        test2.insert("J");
        test2.insert("W");
        test2.insert("N");
        test2.insert("U");
        test2.insert("Y");
        test2.insert("L");
        String expected = "[ S(b), L(b), W(b), J(r), N(r), U(r), Y(r) ]";
        String actual = test2.root.toLevelOrderString();
        assertEquals(expected, actual, test2.toString());
    }
    /**
     * Complex test case that tests multiple cases
     * */
    @Test
    public void testCase3(){
        RedBlackTree<T> test3 = new RedBlackTree<>();
        test3.insert("E");
        test3.insert("C");
        test3.insert("H");
        test3.insert("A");
        test3.insert("G");
        test3.insert("F");
        test3.insert("S");
        test3.insert("N");
        String expected = "[ E(b), C(b), G(r), A(r), F(b), N(b), H(r), S(r) ]";
        String actual = test3.root.toLevelOrderString();
        assertEquals(expected, actual, test3.toString());

    }
}
