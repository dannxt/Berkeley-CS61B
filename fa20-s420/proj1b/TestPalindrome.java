import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    /* Test the isPalindrome method without cc */
    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
        assertFalse(palindrome.isPalindrome(null));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertFalse(palindrome.isPalindrome("Racecar"));
        assertFalse(palindrome.isPalindrome("rbcecar"));
        assertFalse(palindrome.isPalindrome("radecar"));
        assertFalse(palindrome.isPalindrome("racedar"));
        assertFalse(palindrome.isPalindrome("racecbr"));
        assertFalse(palindrome.isPalindrome("racecas"));
    }

    /* Test the isPalindrome method with cc */
    @Test
    public void testisPalindromeObo() {
        OffByOne obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("a", obo));
        assertTrue(palindrome.isPalindrome("", obo));
        assertFalse(palindrome.isPalindrome(null, obo));
        assertTrue(palindrome.isPalindrome("sbdecar", obo));
        assertTrue(palindrome.isPalindrome("racedbs", obo));
        assertFalse(palindrome.isPalindrome("racecar", obo));
        assertFalse(palindrome.isPalindrome("sacedbs", obo));
        assertFalse(palindrome.isPalindrome("rbcedbs", obo));
        assertFalse(palindrome.isPalindrome("radedbs", obo));
    }
}
