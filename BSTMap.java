package map;
/*
 * T * To change this template file, choose Tools | Templates
o change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 /**
 *
 * @author Dinara Assan
 * @param <K>
 */


public class BSTMap<K extends Comparable, V> implements Map<K, V> {

    private TreeNode<KeyValuePair<K, V>> root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public void define(K key, V value) {
        TreeNode<KeyValuePair<K, V>> node = new TreeNode(new KeyValuePair(key, value));
        if (size == 0) {
            root = node;
            size++;
        } else {
            TreeNode<KeyValuePair<K, V>> temp = root;
            while (temp != null) {
                if (temp.getValue().getKey().compareTo(key) < 0) {
                    if (temp.getLeft() == null) {
                        temp.setLeft(node);
                        size++;
                        break;
                    } else {
                        temp = temp.getLeft();
                    }
                } else if (temp.getValue().getKey().compareTo(key) > 0) {
                    if (temp.getRight() == null) {
                        temp.setRight(node);
                        size++;
                        break;
                    } else {
                        temp = temp.getRight();
                    }
                } else {
                    temp.setValue(node.getValue());
                    break;
                }
            }
        }
    }

    @Override
    public V getValue(K key) {
        V result = null;

        TreeNode<KeyValuePair<K, V>> temp = root;
        while (temp != null) {
            if (temp.getValue().getKey().compareTo(key) < 0) {
                temp = temp.getLeft();
            } else if (temp.getValue().getKey().compareTo(key) > 0) {
                temp = temp.getRight();
            } else {
                result = temp.getValue().getValue();
                break;
            }
        }
        return result;
    }

    private void switchChild(TreeNode<KeyValuePair<K, V>> parent, TreeNode<KeyValuePair<K, V>> oldChild, TreeNode<KeyValuePair<K, V>> newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.getLeft() == oldChild) {
            parent.setLeft(newChild);
        } else {
            parent.setRight(newChild);
        }
    }

    private KeyValuePair<K, V> cutSmallestNode(TreeNode<KeyValuePair<K, V>> parent, TreeNode<KeyValuePair<K, V>> node) {
        if (node.getLeft() != null) {
            return cutSmallestNode(node, node.getLeft());
        }

        switchChild(parent, node, node.getRight());

        return node.getValue();
    }

    @Override
    public V remove(K key) {
        return removeHelper(null, root, key);
    }

    private V removeHelper(TreeNode<KeyValuePair<K, V>> parent, TreeNode<KeyValuePair<K, V>> node, K key) {
        if (node == null) {
            return null;
        }
        if (node.getValue().getKey().compareTo(key) < 0) {
            return removeHelper(node, node.getLeft(), key);
        }
        if (node.getValue().getKey().compareTo(key) > 0) {
            return removeHelper(node, node.getRight(), key);
        }
        V result = node.getValue().getValue();
        size--;
        if (node.getRight() == null) {
            switchChild(parent, node, node.getLeft());
        } else {
            node.setValue(cutSmallestNode(node, node.getRight()));
        }
        return result;
    }

    @Override
    public KeyValuePair<K, V> removeAny() throws Exception {
        if (size == 0) {
            throw new Exception("The Map is empty");
        }

        KeyValuePair<K, V> result = null;

        TreeNode<KeyValuePair<K, V>> parent = null, it = root;
        while (it != null) {
            if (it.getLeft() != null) {
                parent = it;
                it = it.getLeft();
            } else if (it.getRight() != null) {
                parent = it;
                it = it.getRight();
            } else {
                result = it.getValue();
                switchChild(parent, it, null);
                it = null;
            }
        }
        size--;
        return result;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    public String toString() {
        return toStringHelper(root);
    }

    private String toStringHelper(TreeNode<KeyValuePair<K, V>> node) {
        if (node == null) {
            return "";
        }
        return toStringHelper(node.getLeft())
                + node.getValue() + "\n"
                + toStringHelper(node.getRight());
    }
}
