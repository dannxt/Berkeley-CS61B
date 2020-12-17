public class ArrayDeque<T> implements Deque<T> {
    /*
    * Class and instance variables and constructors
    */
    private T[] items;
    private int size;
    private int firstIndex;
    private int capacity;

    /* constructor with starting array length 8 */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        capacity = items.length;
        firstIndex = capacity / 2;
    }

    /*
     * Private methods
     */

    /* check and lower capacity if needed */
    private void downCapCheck() {
        double rFactor = (double) size / capacity;
        if (capacity >= 16 && rFactor < 0.25) {
            T[] newItems = (T[]) new Object[Math.max(size * 2, 8)];
            int newFirstIndex = (newItems.length / 2);
            System.arraycopy(items, firstIndex, newItems, newFirstIndex, size);
            items = newItems;
            firstIndex = newFirstIndex;
            capacity = items.length;
        }
    }

    /* check and increase capacity if needed */
    private void upCapCheck() {
        if (items[0] != null || items[capacity - 1] != null) {
            T[] newItems = (T[]) new Object[capacity * 2];
            int newFirstIndex = (newItems.length / 2);
            System.arraycopy(items, firstIndex, newItems, newFirstIndex, size);
            items = newItems;
            firstIndex = newFirstIndex;
            capacity = items.length;
        }
    }

    private void resetIndexCheck() {
        if (size == 0) {
            firstIndex = items.length / 2;
        }
    }

    /*
     * Public methods
     */

    /* Add item to the front of the deque */
    public void addFirst(T i) {
        upCapCheck();
        items[firstIndex - 1] = i;
        size += 1;
        firstIndex -= 1;
    }

    /* Add item to the back of the deque */
    public void addLast(T i) {
        upCapCheck();
        items[firstIndex + size] = i;
        size += 1;
    }

    public T removeFirst() {
        if (items[firstIndex] != null) {
            T ptr = items[firstIndex];
            items[firstIndex] = null;
            firstIndex += 1;
            size -= 1;
            resetIndexCheck();
            downCapCheck();
            return ptr;
        }
        return null;
    }

    /* Removes and returns the item at the back of the deque. */
    /* If no such item exists, returns null. */
    public T removeLast() {
        if (items[firstIndex + size - 1] != null) {
            T ptr = items[firstIndex + size - 1];
            items[firstIndex + size - 1] = null;
            size -= 1;
            resetIndexCheck();
            downCapCheck();
            return ptr;
        }
        return null;
    }

    /* Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null. Must not alter the deque!
     *
     */
    public T get(int i) {
        if (i >= size || i < 0) {
            return null;
        }
        return items[firstIndex + i];
    }

    /* Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        for (int i = firstIndex; i < size + firstIndex; i++) {
            System.out.print(items[i] + " ");
        }
        System.out.println(" ");
    }
}

