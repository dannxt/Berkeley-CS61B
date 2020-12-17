package byow.Core;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class Room {
    private Point uLCorner, uRCorner, lLCorner, lRCorner;
    private int rmHeight, rmWidth;
    private Random random;
    private static int numRooms = 0;
    private static final int ROOM_MIN_HEIGHT = 3;
    private static final int ROOM_MIN_WIDTH = 5;
    private static final int ROOM_HT_FACTOR = 3;
    private static final int ROOM_W_FACTOR = 4;
    Set<Hallway> hallwaySet;

    public Room(Random r) {
        random = r;
        rmHeight = RandomUtils.uniform(random, ROOM_MIN_HEIGHT,
                (Constants.GRID_HEIGHT - 2 * Constants.GRID_OFFSET) / ROOM_HT_FACTOR);
        rmWidth = RandomUtils.uniform(random, ROOM_MIN_WIDTH,
                (Constants.GRID_WIDTH - 2 * Constants.GRID_OFFSET) / ROOM_W_FACTOR);
        int uLPx = RandomUtils.uniform(random, Constants.GRID_OFFSET,
                Constants.GRID_WIDTH - rmWidth - Constants.GRID_OFFSET);
        int uLPy = RandomUtils.uniform(random, Constants.GRID_OFFSET + rmHeight,
                Constants.GRID_HEIGHT - Constants.GRID_OFFSET);
        uLCorner = new Point(uLPx, uLPy);
        uRCorner = new Point(uLPx + rmWidth, uLPy);
        lLCorner = new Point(uLPx, uLPy - rmHeight);
        lRCorner = new Point(uLPx + rmWidth, uLPy - rmHeight);
        createRoomHallways();
        Room.numRooms++;
    }

    public Point getUpperLeftCorner() {
        return uLCorner;
    }

    public Point getUpperRightCorner() {
        return uRCorner;
    }

    public Point getLowerLeftCorner() {
        return lRCorner;
    }

    public Point getLowerRightCorner() {
        return lRCorner;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public int getHeight() {
        return rmHeight;
    }

    public int getRmWidth() {
        return rmWidth;
    }

    private void createRoomHallways() {
        hallwaySet = new HashSet<>();
        int uLPx = this.getUpperLeftCorner().getX();
        int uLPy = this.getUpperLeftCorner().getY();
        int lRPx = this.getLowerRightCorner().getX();
        int lRPy = this.getLowerRightCorner().getY();


        Point northStartP = new Point(RandomUtils.uniform(random, uLPx + 1, lRPx), uLPy);
        Point eastStartP = new Point(lRPx, RandomUtils.uniform(random, lRPy + 1, uLPy));
        Point southStartP = new Point(RandomUtils.uniform(random, uLPx + 1, lRPx), lRPy);
        Point westStartP = new Point(uLPx, RandomUtils.uniform(random, lRPy + 1, uLPy));

        Hallway northHw = new Hallway(northStartP, 'N', random);
        Hallway eastHw = new Hallway(eastStartP, 'E', random);
        Hallway southHw = new Hallway(southStartP, 'S', random);
        Hallway westHw = new Hallway(westStartP, 'W', random);

        // Randomly selects twice from the entrances, with replacement
        List<Hallway> hallwayList = Arrays.asList(northHw, eastHw, southHw, westHw);
        for (int i = 0; i < 2; i++) {
            hallwaySet.add(hallwayList.get(RandomUtils.uniform(random, 3)));
        }
    }

    // Return a set of hallways, each randomly starting along one side of the room
    public Set<Hallway> getHallways() {
        return hallwaySet;
    }
}


