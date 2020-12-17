public class OffByOne implements CharacterComparator {
    public boolean equalChars(char x, char y) {

        if (Math.abs(y - x) != 1) {
            return false;
        }
        return true;
    }
}
