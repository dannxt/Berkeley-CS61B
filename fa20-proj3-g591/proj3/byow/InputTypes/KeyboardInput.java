package byow.InputTypes;

import edu.princeton.cs.introcs.StdDraw;

/**
 * @source Josh Hug
 * Referenced from InputDemo
 */
public class KeyboardInput implements InputType {

    public char getNextKey() {
        char inputKey = 0;
        if (StdDraw.hasNextKeyTyped()) {
            inputKey = Character.toUpperCase(StdDraw.nextKeyTyped());
            System.out.println(inputKey);
        }
        return inputKey;
    }

    public boolean hasNextKey() {
        return true;
    }
}
