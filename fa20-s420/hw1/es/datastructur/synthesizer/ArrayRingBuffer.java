package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = capacity / 2;
        last = first;
        fillCount = 0;
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (rb.length == fillCount) {
            throw new RuntimeException("Ring buffer overflow");
        }

        rb[last] = x;
        if (last == rb.length - 1) {
            last = 0;
        } else {
            last += 1;
        }
        fillCount += 1;
        return;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (rb[first] == null) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T temp = rb[first];
        rb[first] = null;

        if (first == rb.length - 1) {
            first = 0;
        } else {
            first += 1;
        }
        fillCount -= 1;
        return temp;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (rb[first] == null) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    @Override
        public boolean equals(Object o) {
            if (this.getClass() != o.getClass()) {
                return false;
            }

            ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;

            if (fillCount() != other.fillCount()) {
                return false;
            }

            Iterator<T> itrO = other.iterator();
            Iterator<T> itrT = iterator();

            for (int i = 0; i < this.capacity(); i++) {
                if (itrT.next() != itrO.next()) {
                    return false;
                }
            }
            return true;
        }

    @Override
    public Iterator<T> iterator() {
        return new ArbIterator();
    }

    private class ArbIterator implements Iterator<T> {
        private int index;

        ArbIterator() {
            index = first;
        }

        public boolean hasNext() {
            return !(index == last);
        }

        public T next() {
            T returnItem = rb[index];
            if (hasNext()) {
                if (index == rb.length - 1) {
                    index = 0;
                } else {
                    index += 1;
                }
            }
            return returnItem;
        }
    }
}
