public class LinkedListDeque<T> {
    private class Node {
        private T item;
        private Node prev;
        private Node next;

        Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    /*
     * Class, instance variables and constructors
     */
    private Node sentinel;
    private int size;
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /*
     * Public Methods
     */

    /* Add item of type "T" to the front of the deque */
    public void addFirst(T item) {
        Node ptr = sentinel.next;
        sentinel.next = new Node(item, sentinel, sentinel.next);
        ptr.prev = sentinel.next;
        size += 1;
    }

    /* Add item of type "T" to the back of the deque */
    public void addLast(T item) {
        Node ptr = sentinel.prev;
        sentinel.prev = new Node(item, sentinel.prev, sentinel);
        ptr.next = sentinel.prev;
        size += 1;
    }

    /* Removes and returns the item at the front. Returns null if no item */
    public T removeFirst() {
        if (size != 0) {
            Node ptr = sentinel.next;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return ptr.item;
        }
        return null;
    }

    /* Removes and returns the item at the back. Returns null if no item */
    public T removeLast() {
        if (size != 0) {
            Node ptr = sentinel.prev;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return ptr.item;
        }
        return null;
    }

    public T get(int index) {
        Node ptr = sentinel.next;
        while (index > 0) {
            ptr = ptr.next;
            index -= 1;
        }
        return ptr.item;
    }

    /* get item at the given index, recursively */
    public T getRecursive(int index) {
        return getRecursiveHelper(index, sentinel);
    }
    private T getRecursiveHelper(int index, Node s) {
        if (index == 0) {
            return s.next.item;
        }
        s = s.next;
        return getRecursiveHelper(index - 1, s);
    }

    /* Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /* Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space */
    /* Once all the items have been printed, print out a new line. */
    public void printDeque() {
        Node current = sentinel.next;
        while (current != sentinel) {
            System.out.print(current.item + " ");
            current = current.next;
        }
        System.out.println(" ");
    }
}
