package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT, 0, 0);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // add hexagon of size s;
        addHexagon(4, 15, 0, world);
        addHexagon(4, 15, 8, world);
        addHexagon(4, 15, 16, world);
        addHexagon(4, 15, 24, world);
        addHexagon(4, 15, 32, world);
        addHexagon(4, 15, 40, world);
        addHexagon(4, 15, 48, world);
        // draws the world to the screen
        ter.renderFrame(world);
    }

    private static void addHexagon(int size, int startX, int startY, TETile[][] world) {
        int offsetX = size - 1;
        int xWidth = size;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < xWidth; x++) {
                world[startX + offsetX + x][startY + y] = Tileset.GRASS;
            }
            offsetX -= 1;
            xWidth += 2;
        }

        xWidth -= 2;
        offsetX += 1;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < xWidth; x++) {
                world[startX + offsetX + x][startY + size + y] = Tileset.GRASS;
            }
            offsetX += 1;
            xWidth -= 2;
        }
    }

}
