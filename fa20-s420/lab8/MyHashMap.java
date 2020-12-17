import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private class Node {
        K key;
        V value;

        Node (K k, V v) {
            key = k;
            value = v;
        }
    }

    private int size;
    private double loadFactor;
    private HashSet<K> keys;
    private LinkedList<Node>[] bucketList;

    private static final int DEFAULT_SIZE = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    /* Constructors */

    public MyHashMap(int s, double lF) {
        size = 0;
        loadFactor = lF;
        keys = new HashSet<>();
        bucketList = (LinkedList<Node>[]) new LinkedList[s];
    }

    public MyHashMap() {
        this(DEFAULT_SIZE, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int s) {
        this(s, DEFAULT_LOAD_FACTOR);
    }

    /* Iterator */
    public Iterator<K> iterator() {
        return keys.iterator();
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        size = 0;
        keys = new HashSet<>();
        bucketList = (LinkedList<Node>[]) new LinkedList[DEFAULT_SIZE];
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        Node node = getNode(key);
        if (node == null) {
            int index = bucketIndex(key);
            LinkedList<Node> bucket = bucketList[index];
            if (bucket == null) {
                bucket = new LinkedList<>();
                bucket.add(new Node(key, value));
                bucketList[index] = bucket;
            }
            size += 1;
            keys.add(key);
            if ((double) size/bucketList.length > loadFactor) {
                remakeBacketList(size * 2);
            }
        } else {
            size += 1;
            keys.add(key);
            node.value = value;
        }
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keys;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /* Returns the index of bucketList containing key */
    private int bucketIndex(K key, int listLength) {
        return Math.floorMod(key.hashCode(), listLength);
    }

    private int bucketIndex(K key) {
        return Math.floorMod(key.hashCode(), bucketList.length);
    }

    /* Returns the node corresponding to the specific key */
    private Node getNode(K key) {
        int index = bucketIndex(key);
        LinkedList<Node> bucket = bucketList[index];
        if (bucket != null) {
            for (Node node : bucket) {
                if (node.key.equals(key)) {
                    return node;
                }
            }
        }
        return null;
    }

    /* Resize the bucketList when size exceeds loadFactor */
    private void remakeBacketList(int newSize) {
        LinkedList<Node>[] newBucketList = (LinkedList<Node>[]) new LinkedList[newSize];
        for (K key : keys) {
            int index = bucketIndex(key, newBucketList.length);
            if (newBucketList[index] == null) {
                newBucketList[index] = new LinkedList<>();
            }
            newBucketList[index].add(getNode(key));
        }
        bucketList = newBucketList;
    }
}
