package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class WorldGeneration {
    private Grid master;
    private Random random;

    public WorldGeneration(long s) {
        random = new Random(s);
        createMasterGrid();
        int numIterations = RandomUtils.uniform(random, 10000, 100000);
        tryAddRoomsAndHalls(15000);
        drawGridWalls();
    }

    private void createMasterGrid() {
        this.master = new Grid(true, random);
    }

    /**
     * generates a pseudo-grid with a randomly created room and adds to master if it fits,
     * repeatedly for N number of times
     */
    private void tryAddRoomsAndHalls(int N) {
        for (int i = 0; i < N; i++) {
            Grid pseudo = new Grid(false, random);
            if (pseudo.isRoomConnected(master) && pseudo.checkRoomFitsOnMaster(master)) {
                pseudo.transferRoomToMaster(master);

            }
        }
    }

    private void drawGridWalls() {
        TETile[][] tileWorld = master.getTileWorld();

        for (int x = 0; x < Constants.GRID_WIDTH; x++) {
            for (int y = 0; y < Constants.GRID_HEIGHT; y++) {
                if (tileWorld[x][y] == Tileset.FLOOR) {
                    drawingSurroundingWalls(tileWorld, x, y);
                }
            }
        }
    }

    private void drawingSurroundingWalls(TETile[][] world, int x, int y) {
        TETile n = Tileset.NOTHING;
        Point p = new Point(x, y);

        // left
        if (world[p.getLeftPoint().getX()][p.getLeftPoint().getY()].equals(n)) {
            world[p.getLeftPoint().getX()][p.getLeftPoint().getY()] = Tileset.WALL;
        }
        // right
        if (world[p.getRightPoint().getX()][p.getRightPoint().getY()].equals(n)) {
            world[p.getRightPoint().getX()][p.getRightPoint().getY()] = Tileset.WALL;
        }
        // top
        if (world[p.getTopPoint().getX()][p.getTopPoint().getY()].equals(n)) {
            world[p.getTopPoint().getX()][p.getTopPoint().getY()] = Tileset.WALL;
        }
        // top left
        if (world[p.getTopLeftPoint().getX()][p.getTopLeftPoint().getY()].equals(n)) {
            world[p.getTopLeftPoint().getX()][p.getTopLeftPoint().getY()] = Tileset.WALL;
        }
        // top right
        if (world[p.getTopRightPoint().getX()][p.getTopRightPoint().getY()].equals(n)) {
            world[p.getTopRightPoint().getX()][p.getTopRightPoint().getY()] = Tileset.WALL;
        }
        // bottom
        if (world[p.getBottomPoint().getX()][p.getBottomPoint().getY()].equals(n)) {
            world[p.getBottomPoint().getX()][p.getBottomPoint().getY()] = Tileset.WALL;
        }
        // bottom left
        if (world[p.getBottomLeftPoint().getX()][p.getBottomLeftPoint().getY()].equals(n)) {
            world[p.getBottomLeftPoint().getX()][p.getBottomLeftPoint().getY()] = Tileset.WALL;
        }
        // bottom right
        if (world[p.getBottomRightPoint().getX()][p.getBottomRightPoint().getY()].equals(n)) {
            world[p.getBottomRightPoint().getX()][p.getBottomRightPoint().getY()] = Tileset.WALL;
        }
    }

    public TETile[][] getMTileWorld() {
        return master.getTileWorld();
    }
}
