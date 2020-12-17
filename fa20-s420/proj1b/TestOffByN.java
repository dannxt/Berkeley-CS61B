import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offBy5 = new OffByN(5);
    static CharacterComparator offBy0 = new OffByN(0);

    @Test
    public void testEqualChars() {
        assertFalse(offBy5.equalChars('a', 'g'));
        assertFalse(offBy5.equalChars('g', 'a'));
        assertFalse(offBy5.equalChars('a', 'a'));
        assertFalse(offBy5.equalChars('a', 'A'));
        assertFalse(offBy5.equalChars('a', 'F'));
        assertTrue(offBy5.equalChars('a', 'f'));
        assertFalse(offBy5.equalChars('b', 'f'));
        assertFalse(offBy5.equalChars('%', '&'));
        assertFalse(offBy5.equalChars('%', '$'));
        assertFalse(offBy5.equalChars('%', '#'));
        assertFalse(offBy5.equalChars('!', '#'));

        assertTrue(offBy0.equalChars('a', 'a'));
        assertFalse(offBy0.equalChars('a', 'A'));
    }
}
