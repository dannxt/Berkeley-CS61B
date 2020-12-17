package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class Grid {
    private boolean isMasterGrid;
    private Random random;
    private TETile[][] tileWorld;
    private Room newRoom;

    public Grid(boolean isMaster, Random r) {
        tileWorld = new TETile[Constants.GRID_WIDTH][Constants.GRID_HEIGHT];
        isMasterGrid = isMaster;
        random = r;
        drawBlankWorld();
        drawRoomWithHalls();
    }

    private void drawRoomWithHalls() {
        newRoom = new Room(random);
        drawRoom();
        drawHallwayPassage();
        drawHallwayEntrance();
    }

    private void drawRoom() {
        int uLPx = newRoom.getUpperLeftCorner().getX();
        int uLPy = newRoom.getUpperLeftCorner().getY();
        int lRPx = newRoom.getLowerRightCorner().getX();
        int lRPy = newRoom.getLowerRightCorner().getY();

        for (int x = uLPx + 1; x <= lRPx - 1; x++) {
            for (int y = uLPy - 1; y >= lRPy + 1; y--) {
                tileWorld[x][y] = Tileset.FLOOR;
            }
        }
    }

    private void drawHallwayEntrance() {
        Set<Hallway> hallways = newRoom.getHallways();
        for (Hallway h : hallways) {
            tileWorld[h.getStartPoint().getX()][h.getStartPoint().getY()] = Tileset.FLOOR;
        }
    }

    private void drawHallwayPassage() {
        Set<Hallway> hallways = newRoom.getHallways();
        for (Hallway h : hallways) {
            for (Point p : h.getPoints()) {
                tileWorld[p.getX()][p.getY()] = Tileset.FLOOR;
            }
        }
    }

    public boolean isRoomConnected(Grid master) {
        TETile g = Tileset.FLOOR;
        TETile n = Tileset.NOTHING;
        TETile[][] world = master.tileWorld;
        for (Hallway h : newRoom.getHallways()) {
            List<Point> points = h.getPoints();

            for (Point p : points) {
                if (world[p.getX()][p.getY()].equals(g)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks if new room and the hallway to be added fits on the master grid
    public boolean checkRoomFitsOnMaster(Grid master) {
        if (!checkUpperLeftCorner(this.newRoom, master)
                || !checkUpperRightCorner(this.newRoom, master)
                || !checkLowerLeftCorner(this.newRoom, master)
                || !checkLowerRightCorner(this.newRoom, master)) {
            return false;
        }

        // Checks for Room overlap
        int uLPx = newRoom.getUpperLeftCorner().getX();
        int uLPy = newRoom.getUpperLeftCorner().getY();
        int lRPx = newRoom.getLowerRightCorner().getX();
        int lRPy = newRoom.getLowerRightCorner().getY();

        for (int x = uLPx; x <= lRPx; x++) {
            for (int y = uLPy; y >= lRPy; y--) {
                if (master.tileWorld[x][y] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    // Checks if there is nothing around the corners of the room
    private boolean checkUpperLeftCorner(Room r, Grid master) {
        int lPx = r.getUpperLeftCorner().getLeftPoint().getX();
        int lPy = r.getUpperLeftCorner().getLeftPoint().getY();
        int tPx = r.getUpperLeftCorner().getTopPoint().getX();
        int tPy = r.getUpperLeftCorner().getTopPoint().getY();

        return master.tileWorld[lPx][lPy].equals(Tileset.NOTHING)
                && master.tileWorld[tPx][tPy].equals(Tileset.NOTHING);
    }

    private boolean checkUpperRightCorner(Room r, Grid master) {
        int rPx = r.getUpperRightCorner().getRightPoint().getX();
        int rPy = r.getUpperRightCorner().getRightPoint().getY();
        int tPx = r.getUpperRightCorner().getTopPoint().getX();
        int tPy = r.getUpperRightCorner().getTopPoint().getY();

        return master.tileWorld[rPx][rPy].equals(Tileset.NOTHING)
                && master.tileWorld[tPx][tPy].equals(Tileset.NOTHING);
    }

    private boolean checkLowerLeftCorner(Room r, Grid master) {
        int lPx = r.getLowerLeftCorner().getLeftPoint().getX();
        int lPy = r.getLowerLeftCorner().getLeftPoint().getY();
        int bPx = r.getLowerLeftCorner().getBottomPoint().getX();
        int bPy = r.getLowerLeftCorner().getBottomPoint().getY();

        return master.tileWorld[lPx][lPy].equals(Tileset.NOTHING)
                && master.tileWorld[bPx][bPy].equals(Tileset.NOTHING);
    }

    private boolean checkLowerRightCorner(Room r, Grid master) {
        int rPx = r.getLowerRightCorner().getRightPoint().getX();
        int rPy = r.getLowerRightCorner().getRightPoint().getY();
        int bPx = r.getLowerRightCorner().getBottomPoint().getX();
        int bPy = r.getLowerRightCorner().getBottomPoint().getY();

        return master.tileWorld[rPx][rPy].equals(Tileset.NOTHING)
                && master.tileWorld[bPx][bPy].equals(Tileset.NOTHING);
    }

    // Transfers the new room to the master grid
    public void transferRoomToMaster(Grid master) {
        for (int x = 0; x < Constants.GRID_WIDTH; x++) {
            for (int y = 0; y < Constants.GRID_HEIGHT; y++) {
                if (!this.tileWorld[x][y].equals(Tileset.NOTHING)) {
                    master.tileWorld[x][y] = this.tileWorld[x][y];
                }
            }
        }
    }

    // Draws the blank world of NOTHING tiles before any rooms added
    private void drawBlankWorld() {
        for (int x = 0; x < Constants.GRID_WIDTH; x += 1) {
            for (int y = 0; y < Constants.GRID_HEIGHT; y += 1) {
                tileWorld[x][y] = Tileset.NOTHING;
            }
        }
    }

    public TETile[][] getTileWorld() {
        return this.tileWorld;
    }
}


/*
 *
 * Code Archive - Temporarily Unused Code
 *
 */
//    private void fillWallsWithTiles(int uLPx, int uLPy, int lRPx, int lRPy) {
//        for (int x = uLPx; x <= lRPx; x++) {
//            tileWorld[x][uLPy] = Tileset.WALL;
//            tileWorld[x][lRPy] = Tileset.WALL;
//        }
//
//        for (int y = uLPy; y >= lRPy; y--) {
//            tileWorld[uLPx][y] = Tileset.WALL;
//            tileWorld[lRPx][y] = Tileset.WALL;
//        }
//    }
