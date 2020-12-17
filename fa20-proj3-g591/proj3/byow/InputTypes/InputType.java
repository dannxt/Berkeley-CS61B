package byow.InputTypes;
/**
 * @source Josh Hug
 *  Referenced from InputDemo
 */
public interface InputType {
    /* Return the key that the user enters next using keyboard */
    char getNextKey();
    boolean hasNextKey();
}
