import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    public Iterator<K> iterator() {
        return null;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    };

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    };

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    };

    private V getHelper(K key, Node n) {
        if (n == null) {
            return null;
        }

        if (key.compareTo(n.key) < 0) {
            return getHelper(key, n.left);
        } else if (key.compareTo(n.key) > 0) {
            return getHelper(key, n.right);
        } else {
            return n.value;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    };

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    };

    private Node putHelper(K key, V value, Node n) {
        if (n == null) {
            size += 1;
            return new Node(key, value);
        }

        if (key.compareTo(n.key) < 0) {
            n.left = putHelper(key, value, n.left);
        } else if (key.compareTo(n.key) > 0) {
            n.right = putHelper(key, value, n.right);
        } else {
            n.value = value;
        }
        return n;
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    };

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}