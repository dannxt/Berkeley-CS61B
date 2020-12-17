package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<PriorityNode> items;
    private HashMap<T, PriorityNode> itemMap;
    private int size;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        items.add(null);
        itemMap = new HashMap<>();
        size = 0;
    }

    /**
     * Note this method does not throw the proper exception,
     * otherwise it is painfully slow (linear time).
     */
    @Override
    public void add(T item, double priority) {
        if (itemMap.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        PriorityNode n = new PriorityNode(item, priority, size + 1);
        itemMap.put(item, n);
        items.add(n);
        size++;
        swim(n);
    }

    private PriorityNode getParent(PriorityNode n) {
        int idx = n.getIndex();
        if (idx == 1) {
            return null;
        }
        return items.get(idx / 2);
    }

    private PriorityNode getLeftChild(PriorityNode n) {
        int idx = n.getIndex() * 2;
        if (idx > size) {
            return null;
        }
        return items.get(idx);
    }

    private PriorityNode getRightChild(PriorityNode n) {
        int idx = n.getIndex() * 2 + 1;
        if (idx > size) {
            return null;
        }
        return items.get(idx);
    }

    private void swap(PriorityNode a, PriorityNode b) {
        int aIndex = a.getIndex();
        int bIndex = b.getIndex();
        a.setIndex(bIndex);
        b.setIndex(aIndex);
        items.set(aIndex, b);
        items.set(bIndex, a);
    }

    private void swim(PriorityNode n) {
        if (getParent(n) != null) {
            PriorityNode parent = getParent(n);
            int cmp = n.compareTo(parent);
            if (cmp < 0) {
                swap(n, parent);
                swim(n);
            }
        }
    }

    private void sink(PriorityNode n) {
        PriorityNode lc = getLeftChild(n);
        PriorityNode rc = getRightChild(n);
        if (lc != null && lc.compareTo(rc) < 0 && lc.compareTo(n) < 0) {
            swap(n, lc);
            sink(n);
        } else if (rc != null && rc.compareTo(n) < 0) {
            swap(n, rc);
            sink(n);
        }
    }

    @Override
    public boolean contains(T item) {
        return itemMap.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return items.get(1).getItem();
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode newMin = items.get(size);
        PriorityNode oldMin = items.set(1, newMin);
        itemMap.remove(oldMin.getItem(), oldMin);
        items.remove(size);
        newMin.setIndex(1);
        size--;
        sink(newMin);
        return oldMin.getItem();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!itemMap.containsKey(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        PriorityNode target = itemMap.get(item);
        target.setPriority(priority);
        PriorityNode parent = getParent(target);
        if (parent != null && parent.compareTo(target) > 0) {
            swim(target);
        }
        sink(target);
    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return size;
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private int index;
        private T item;
        private double priority;

        PriorityNode(T e, double p, int idx) {
            this.item = e;
            this.priority = p;
            this.index = idx;
        }

        T getItem() {
            return item;
        }

        int getIndex() {
            return index;
        }

        void setIndex(int idx) {
            index = idx;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double p) {
            priority = p;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }
    }

    public static void main(String[] args) {

    }
}
