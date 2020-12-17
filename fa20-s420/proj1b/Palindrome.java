public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        } else if (word.length() <= 1) {
            return true;
        }
        Deque<Character> target = wordToDeque(word);
        while (target.size() > 1) {
            if (target.removeFirst() != target.removeLast()) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        } else if (word.length() <= 1) {
            return true;
        }
        Deque<Character> target = wordToDeque(word);
        while (target.size() > 1) {
            if (!cc.equalChars(target.removeFirst(), target.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
