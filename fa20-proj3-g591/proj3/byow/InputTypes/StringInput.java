package byow.InputTypes;

/**
 * @source Josh Hug
 *  Referenced from InputDemo
 */
public class StringInput implements InputType {
    private char[] inputCharArray;
    private int index;

    public StringInput(String s) {
        index = 0;
        inputCharArray = s.toUpperCase().toCharArray();
    }

    public char getNextKey() {
        char c = inputCharArray[index];
        index += 1;
        System.out.print(c);
        return c;
    }

    public boolean hasNextKey() {
        return index < inputCharArray.length;
    }
}

