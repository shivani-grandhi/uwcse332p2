package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;
/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    private class AVLNode extends BSTNode {
        private int height;

        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
        }
    }

    public AVLTree() {
        super();
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        V oldVal = find(key);
        this.root = insHelper((AVLNode)this.root, key, value);
        return oldVal;
    }

    private AVLNode insHelper(AVLNode node, K key, V value) {
        if (node == null) {
            this.size++;
            return new AVLNode(key, value);
        }
        int direction = Integer.signum(key.compareTo(node.key));
        if (direction < 0) {
            node.children[0] = insHelper((AVLNode)node.children[0], key, value);
        } else if (direction > 0) {
            node.children[1] = insHelper((AVLNode)node.children[1], key, value);
        } else {
            node.value = value;
        }
        return balance(node);
    }

    private AVLNode balance(AVLNode node) {
        if(node == null) {
            return node;
        }
        if(heightDifference(node)> 1) {
            if (heightDifference((AVLNode)node.children[0])>0) {
                node = rotateLeft(node);
            } else {
                node = doubleWithL(node);
            }
        } else if(heightDifference(node) < -1) {
            if (heightDifference((AVLNode)node.children[1]) < 0) {
                node = rotateRight(node);
            } else {
                node = doubleWithR(node);
            }
        }
        node.height = Math.max(height((AVLNode)node.children[0]),height((AVLNode)node.children[1]))+1;
        return node;
    }
    private int heightDifference(BSTNode node) {
        return height((AVLNode)node.children[0]) - height((AVLNode)node.children[1]);
    }

    private AVLNode rotateRight(AVLNode node) {
        AVLNode temp = (AVLNode)node.children[1];
        node.children[1] = temp.children[0];
        temp.children[0] = node;
        node.height = Math.max(height((AVLNode)node.children[0]),height((AVLNode)node.children[1]))+1;
        temp.height = Math.max(height((AVLNode)temp.children[1]),height(node))+1;
        return temp;
    }
    private AVLNode rotateLeft(AVLNode node) {
        AVLNode temp = (AVLNode)node.children[0];
        node.children[0] = temp.children[1];
        temp.children[1] = node;
        node.height = Math.max(height((AVLNode)node.children[0]),height((AVLNode)node.children[1]))+1;
        temp.height = Math.max(height((AVLNode)temp.children[0]),height(node))+1;
        return temp;
    }

    private int height(AVLNode node) {
        if(node == null) {
            return -1;
        }
        return node.height;
    }
    private AVLNode doubleWithL(AVLNode node) {
        node.children[0] = rotateRight((AVLNode)node.children[0]);
        return rotateLeft(node);
    }
    private AVLNode doubleWithR(AVLNode node) {
        node.children[1] = rotateLeft((AVLNode)node.children[1]);
        return rotateRight(node);
    }
}