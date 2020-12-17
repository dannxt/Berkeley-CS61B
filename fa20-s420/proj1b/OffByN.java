public class OffByN implements CharacterComparator {
    private int _N;

    public OffByN(int n) {
        _N = n;
    }

    public boolean equalChars(char x, char y) {
        if (Math.abs(y - x) == _N) {
            return true;
        }
        return false;
    }
}
