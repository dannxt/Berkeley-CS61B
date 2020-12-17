package es.datastructur.synthesizer;
import org.junit.Test;
import java.util.Iterator;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> o = new ArrayRingBuffer<>(5);
        o.enqueue(3);
        o.enqueue(4);
        o.enqueue(5);
        int a = o.dequeue(); // test dequeue()
        assertEquals(a, 3);
        o.enqueue(6);
        o.enqueue(7);
        o.enqueue(8);
        int m = o.peek(); // test peek()
        assertEquals(4, m);
        assertTrue(o.isFull());
        assertEquals(o.capacity(), 5);
        assertEquals(o.fillCount(), 5); // asserts that the array is full
        int b = o.peek();
        assertEquals(b, 4); //another test peek()
        o.dequeue();
        o.dequeue();
        o.dequeue();
        o.dequeue();
        o.enqueue(100);
        o.dequeue();
        int c = o.peek();
        assertEquals(100, c); // ensures that the pointers for first and last are moving circularly
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);
        arb.enqueue(100);
        assertTrue(arb.equals(o)); // tests the equal method
        arb.enqueue(20);
        assertFalse(arb.equals(o)); // tests to see they are equal with different sizes
        arb.dequeue();
        arb.dequeue();
        assertFalse(o.equals(arb)); // checks to see if a null list will present an error
        Iterator<Integer> g = o.iterator();
        assertTrue(g.hasNext()); // assert that hasNext works if item is in list
        int h = g.next();
        assertEquals(h,100); // checks to see if the correct item is being returned
        assertFalse(g.hasNext()); //checks to see that calling next on o iterator with no items left will return false.
    }
}
