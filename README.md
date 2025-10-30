# Red-Black Tree — Java

An implementation of a self-balancing binary search tree in Java that maintains logarithmic height through coloring and rotation rules.  
This project demonstrates a deep understanding of data structures, recursion, and tree balancing algorithms commonly used in systems and database indexing.

---

## Overview
Red-Black Tree is a type of balanced binary search tree that ensures efficient O(log n) complexity for insert and search operations by enforcing structural
invariants through color flips and rotations.

This implementation includes:
- Generic node and tree classes supporting `Comparable<T>` types  
- Automatic rebalancing on insertion  
- Rotation and recoloring logic for all cases (LL, LR, RL, RR)  
- Invariant verification and level-order printing for debugging  

---

## 🧩 Features
- **Generic Design:** Supports any comparable data type.  
- **Self-Balancing BST:** Ensures logarithmic height for all operations.  
- **Rotations & Color Flips:** Corrects structural violations recursively.  
- **Extensive Testing:** JUnit coverage for insertion and edge cases.  
- **Readable Debug Output:** Level-order tree printing with color visualization.  

---

## ⚙️ Time Complexity
| Operation | Time Complexity | Description |
|------------|-----------------|--------------|
| Insert     | O(log n)        | Rebalances after each insertion |
| Search     | O(log n)        | Standard BST search traversal |
| Traverse   | O(n)            | In-order or level-order |


## 💻 Example Usage
```java
public class Demo {
    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();

        tree.insert(10);
        tree.insert(5);
        tree.insert(20);
        tree.insert(15);
        tree.insert(25);

        System.out.println("Contains 15? " + tree.contains(15)); // true
        System.out.println("Tree Size: " + tree.size());   // 5
    }
}
