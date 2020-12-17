import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestRedBlackFloorSet {
    @Test
    public void randomizedTest() {
        AListFloorSet AL = new AListFloorSet();
        RedBlackFloorSet RB = new RedBlackFloorSet();

        for (int i = 0; i < 1000000; i++) {
            double d = StdRandom.uniform(-5000, 5000);
            AL.add(d);
            RB.add(d);
        }
        for (int j = 0; j < 100000; j++) {
            double dd = StdRandom.uniform(-5000, 5000);
            assertEquals(AL.floor(dd), RB.floor(dd), 0.0001);
        }
    }
}
