package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR1 = new TETile('♛', Color.white, new Color(41, 42, 63), "you");
    public static final TETile AVATAR2 = new TETile('웃', Color.white, new Color(41, 42, 63), "you");
    public static final TETile AVATAR3 = new TETile('❽', Color.white, new Color(41, 42, 63), "you");
    public static final TETile AVATAR_SHIELDED1 =
            new TETile('♛', Color.white, Color.cyan, "you (shielded)");
    public static final TETile AVATAR_SHIELDED2 =
            new TETile('웃', Color.white, Color.cyan, "you (shielded)");
    public static final TETile AVATAR_SHIELDED3 =
            new TETile('❽', Color.white, Color.cyan, "you (shielded)");
    public static final TETile WALL =
            new TETile('#', new Color(216, 128, 128), new Color(128, 67, 45), "wall");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile FLOOR =
            new TETile('■', new Color(41, 42, 63), new Color(41, 42, 63), "floor");
    public static final TETile WATER =
            new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER =
            new TETile('☘', Color.magenta.brighter(), new Color(41, 42, 63), "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile PATH = new TETile('•', Color.red, new Color(41, 42, 63), "monster path");
    public static final TETile HINT = new TETile('•', Color.orange, new Color(41, 42, 63), "hint path");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile MONSTER =
            new TETile('⟰', Color.red.brighter(), new Color(41, 42, 63), "monster");
    public static final TETile STUCK_MONSTER =
            new TETile('⟰', Color.green.darker(), new Color(41, 42, 63), "stuck monster");
    public static final TETile UI =
            new TETile('▒', Color.DARK_GRAY.darker(), Color.DARK_GRAY.darker(), "UI");
    public static final TETile SEED =
            new TETile('o', Color.orange, new Color(41, 42, 63), "magic seed");
}


