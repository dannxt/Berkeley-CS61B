package hw2;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPercolation {
    /* Test the Open and isOpen method */
    @Test
    public void testIsOpen() {
        Percolation test = new Percolation(2);
        test.open(0, 0);
        Assert.assertFalse(test.percolates());
        test.open(0, 1);
        Assert.assertFalse(test.percolates());
        test.open(1, 1);
        Assert.assertTrue(test.percolates());
    }
}
