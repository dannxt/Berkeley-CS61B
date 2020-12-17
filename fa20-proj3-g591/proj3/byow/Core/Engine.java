package byow.Core;

import byow.InputTypes.InputType;
import byow.InputTypes.KeyboardInput;
import byow.InputTypes.StringInput;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class Engine {
    TERenderer ter = new TERenderer();
    private InputType inputType;
    //    private File worldFolder;
    private Game currentGame;
    private Point mousePos;
    private TETile avatar;
    private TETile avatarShielded;
    private boolean isPathShown;
    private boolean isHintShown;
    private boolean displayWorld;
    private static final TETile MONSTER = Tileset.MONSTER;
    private static final TETile STUCK_MONSTER = Tileset.STUCK_MONSTER;
    private static final TETile FLOOR = Tileset.FLOOR;
    private static final TETile FLOWER = Tileset.FLOWER;
    private static final TETile SEED = Tileset.SEED;
    private static final TETile PATH = Tileset.PATH;
    private static final TETile HINT = Tileset.HINT;
    private static final String NUMBERS = "0123456789";
    private static final String MOVEMENT_KEYS = "WASD";
    private static final int AVATAR_ID = Constants.AVATAR_ID;
    private static final int MONSTER_ID = Constants.MONSTER_ID;
    private static final int FLOWER_ID = Constants.FLOWER_ID;
    private static final int MAGIC_ID = Constants.MAGIC_ID;
    private static final int MONSTER_STUCK_ID = Constants.MONSTER_STUCK_ID;
    private static final int WIDTH = Constants.GRID_WIDTH;
    private static final int HEIGHT = Constants.GRID_HEIGHT;
    private static final int T_UI_WIDTH = Constants.T_UI_WIDTH;
    private static final int B_UI_WIDTH = Constants.B_UI_WIDTH;
    private static final int B_UI_HEIGHT = Constants.B_UI_HEIGHT;

    public Engine() {
        avatar = Tileset.AVATAR1;
        avatarShielded = Tileset.AVATAR_SHIELDED1;
        isPathShown = false;
        isHintShown = false;
    }

    /*
     * Keyboard input initialization
     */
    public void interactWithKeyboard() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        inputType = new KeyboardInput();
        displayWorld = true;
        runMainMenu();
    }

    /*
     * Menus related methods
     * Includes drawing and running main menu, seed menu, avatar menu, etc
     */
    private void runMainMenu() {
        if (displayWorld) {
            StdDraw.clear(Color.BLACK);
            drawMainMenu();
            StdDraw.show();
        }
        while (inputType.hasNextKey()) {
            char input = inputType.getNextKey();
            switch (input) {
                case 'V':
                    runAvatarMenu();
                    break;
                case 'N':
                    long seed = runSeedMenu();
                    runInstructions();
                    runWorld(seed, true, false, false);
                    startGame();
                    break;
                case 'L':
                    loadGame();
                    break;
                case 'Q':
                    quitGame();
                    break;
                default:
                    break;
            }
        }
    }

    private void runInstructions() {
        if (displayWorld) {
            StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.RED);
            Font font = new Font("Monaco", Font.BOLD, 30);
            StdDraw.setFont(font);
            StdDraw.text(0.5, 0.8, "Collect all flowers before the monsters catch you...");
            StdDraw.text(0.5, 0.6, "Eat a magic seed to temporarily glue monsters in place!");
            font = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(font);
            StdDraw.text(0.5, 0.4, "Press space to begin");
            StdDraw.show();
            while (true) {
                char input = inputType.getNextKey();
                if (Character.isWhitespace(input)) {
                    break;
                }
            }
        }
    }

    private void drawMainMenu() {
        if (displayWorld) {
            // Game Title
            StdDraw.setPenColor(Color.RED);
            Font font = new Font("Monaco", Font.BOLD, 30);
            StdDraw.setFont(font);
            StdDraw.text(0.5, 0.8, "CS61B: The Game");

            // Main menu current Avatar
            StdDraw.setPenColor(Color.WHITE);
            font = new Font("Monaco", Font.BOLD, 12);
            StdDraw.setFont(font);
            StdDraw.text(0.5, 0.52, "Current Avatar");
            font = new Font("Monaco", Font.BOLD, 130);
            StdDraw.setFont(font);
            StdDraw.text(0.5, 0.6, Character.toString(avatar.character()));

            // Menu text
            StdDraw.setPenColor(Color.RED);
            font = new Font("Monaco", Font.BOLD, 15);
            StdDraw.setFont(font);
            StdDraw.text(0.5, 0.4, "Select Avatar (V)");
            StdDraw.text(0.5, 0.35, "New Game (N)");
            StdDraw.text(0.5, 0.3, "Load Game (L)");
            StdDraw.text(0.5, 0.25, "Quit (Q)");
        }
    }

    private long runSeedMenu() {
        if (displayWorld) {
            StdDraw.clear(Color.BLACK);
            drawMainMenu();
            StdDraw.text(0.5, 0.2, "Enter Seed:");
            StdDraw.show();
        }
        StringBuilder seedInputDisplayed = new StringBuilder();
        long seed = 0;

        while (inputType.hasNextKey()) {
            char input = inputType.getNextKey();
            if (isANumber(input)) {
                seedInputDisplayed.append(input);
                if (displayWorld) {
                    StdDraw.clear(Color.BLACK);
                    drawMainMenu();
                    StdDraw.text(0.5, 0.2, "Enter Seed:");
                    StdDraw.text(0.5, 0.15, seedInputDisplayed.toString());
                    StdDraw.text(0.5, 0.1, "Press S to Confirm");
                    StdDraw.show();
                }
            }
            if (input == 'S' && seedInputDisplayed.toString().length() > 0) {
                seed = Long.parseLong(seedInputDisplayed.toString());
                break;
            }
        }
        return seed;
    }

    private void runAvatarMenu() {
        if (displayWorld) {
            HashMap<Integer, TETile> avatarHM = new HashMap<>();
            avatarHM.put(0, Tileset.AVATAR1);
            avatarHM.put(1, Tileset.AVATAR2);
            avatarHM.put(2, Tileset.AVATAR3);
            avatarHM.put(10, Tileset.AVATAR_SHIELDED1);
            avatarHM.put(11, Tileset.AVATAR_SHIELDED2);
            avatarHM.put(12, Tileset.AVATAR_SHIELDED3);
            int cursorPos = 0;
            StdDraw.clear(Color.BLACK);
            drawAvatarMenu();
            drawAvatarMenuCursor(cursorPos);
            StdDraw.show();
            while (inputType.hasNextKey()) {
                // keycode 32: spacebar
                if (StdDraw.isKeyPressed(32)) {
                    avatar = avatarHM.get(cursorPos);
                    avatarShielded = avatarHM.get(cursorPos + 10);
                    runMainMenu();
                    break;
                } else {
                    char input = inputType.getNextKey();
                    switch (input) {
                        case 'A':
                            cursorPos = Math.max(0, cursorPos - 1);
                            break;
                        case 'D':
                            cursorPos = Math.min(2, cursorPos + 1);
                            break;
                        default:
                            break;
                    }
                    StdDraw.clear(Color.BLACK);
                    drawAvatarMenu();
                    drawAvatarMenuCursor(cursorPos);
                    StdDraw.show();
                }
            }
        }
    }

    private void drawAvatarMenuCursor(int cursorPos) {
        StdDraw.setPenColor(Color.RED);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        if (cursorPos == 0) {
            StdDraw.rectangle(0.25, 0.52, 0.05, 0.1);
        } else if (cursorPos == 1) {
            StdDraw.rectangle(0.5, 0.52, 0.05, 0.1);
        } else {
            StdDraw.rectangle(0.75, 0.52, 0.05, 0.1);
        }
    }

    private void drawAvatarMenu() {
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 130);
        StdDraw.setFont(font);
        StdDraw.text(0.25, 0.5, "♛");
        StdDraw.text(0.5, 0.5, "웃");
        StdDraw.text(0.75, 0.5, "❽");
        StdDraw.setPenColor(Color.RED);
        font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.8, "Avatar Selection");
        font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.3, "Choose your avatar using navigation keys");
        StdDraw.text(0.5, 0.25, "Press space to confirm");
    }

    /**
     * World drawing, rendering and UI related methods
     * UI that is NOT menu related, such as UI showing mouseover in-game tiles
     */
    private void runWorld(long seed, boolean isNew, boolean isGameOver, boolean isMonsterStuck) {
        WorldGeneration world = new WorldGeneration(seed);
        if (displayWorld) {
            ter.initialize(WIDTH, HEIGHT);
            System.out.println("World is rendered with seed: " + seed);
        }
        currentGame = new Game(isNew, seed, isGameOver, isMonsterStuck, world.getMTileWorld(),
                avatar, MONSTER, STUCK_MONSTER, FLOOR, FLOWER, SEED, avatarShielded);

    }

    private void renderGameWorld(Game game) {
        if (displayWorld) {
            StdDraw.clear();
            setEmptyGameUITiles();
            ter.renderFrame(game.getTileWorld());
            drawMouseoverUI(StdDraw.mouseX(), StdDraw.mouseY());
            drawGameStatusUI();
            StdDraw.show();
        }
    }

    private void drawMouseoverUI(double mouseX, double mouseY) {
        int x = (int) mouseX;
        int y = (int) mouseY;
        if (mouseX < WIDTH && mouseY < HEIGHT) {
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(B_UI_WIDTH / 2.0, B_UI_HEIGHT / 1.61,
                    "This is " + currentGame.getTileWorld()[x][y].description());
        }
    }

    private void setEmptyGameUITiles() {
        TETile[][] world = currentGame.getTileWorld();
        TETile uIBlock = Tileset.UI;
        // top UI
        for (int i = 0; i < T_UI_WIDTH; i++) {
            world[i][HEIGHT - 1] = uIBlock;
        }
        for (int j = 0; j < HEIGHT; j++) {
            world[0][j] = uIBlock;
            world[B_UI_WIDTH - 1][j] = uIBlock;
        }

        // bottom UI
        for (int i = 0; i < B_UI_WIDTH; i++) {
            world[i][0] = uIBlock;
            world[i][4] = uIBlock;
        }
        for (int j = 0; j < B_UI_HEIGHT; j++) {
            world[0][j] = uIBlock;
            world[B_UI_WIDTH - 1][j] = uIBlock;
        }
    }

    private void drawGameStatusUI() {
        if (currentGame.isMonstersStuck()) {
            StdDraw.setPenColor(Color.WHITE);
            Font font = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(font);
            StdDraw.setPenColor(Color.green.darker());
            StdDraw.text(WIDTH / 2.0, HEIGHT / 1.05, "Monster Stuck!");
            font = new Font("Monaco", Font.BOLD, 12);
            StdDraw.setFont(font);
            StdDraw.text(WIDTH / 2.0, HEIGHT / 1.08, "Bonus Moves Left: " + currentGame.getMovesLeftWithGlue());
        }
        // Flower UI
        StdDraw.setPenColor(Color.MAGENTA);
        Font font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.text(B_UI_WIDTH / 1.49, B_UI_HEIGHT / 1.7, Character.toString(FLOWER.character()));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(B_UI_WIDTH / 1.39, B_UI_HEIGHT / 1.61, "Flowers left:");
        StdDraw.text(B_UI_WIDTH / 1.305, B_UI_HEIGHT / 1.61, Integer.toString(currentGame.getNumFlowersLeft()));

        // Seed UI
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.text(B_UI_WIDTH / 1.255, B_UI_HEIGHT / 1.59, Character.toString(SEED.character()));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(B_UI_WIDTH / 1.17, B_UI_HEIGHT / 1.61, "Seeds available:");
        StdDraw.text(B_UI_WIDTH / 1.093, B_UI_HEIGHT / 1.61, Integer.toString(currentGame.getNumSeedsLeft()));

        // Instruction UI
        StdDraw.setPenColor(Color.WHITE);
        font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.text(B_UI_WIDTH / 8.0, B_UI_HEIGHT / 1.61, "Move: W, A, S, D     Hints: O, P");
    }

    /*
     * Game initialization and operations: starting, loading, saving and quitting game methods
     * Also includes helper methods that deal with avatar creation and positioning
     */
    private void startGame() {
        while (inputType.hasNextKey()) {
            char input = inputType.getNextKey();
            if (hasMouseMoved()) {
                renderGameWorld(currentGame);
            }
            if (isSavingKey(input)) {
                saveGame();
                quitGame();
                break;
            }
            if (currentGame.isGameOver()) {
                break;
            } else if (isMovementKey(input)) {
                moveAvatar(input);
                checkGameStatus();
                renderGameWorld(currentGame);
                if (!currentGame.isMonstersStuck()) {
                    currentGame.startMonsterChase();
                    checkGameStatus();
                    renderGameWorld(currentGame);
                }
            }
            if (isPathKey(input)) {
                TETile tile;
                if (!isPathShown) {
                    tile = PATH;
                    isPathShown = true;
                } else {
                    tile = FLOOR;
                    isPathShown = false;
                }
                for (Point monsterP : currentGame.getMonsterPoints()) {
                    currentGame.setDijkstraPathTile(monsterP, tile);
                }
                renderGameWorld(currentGame);
            }
            if (isHintKey(input)) {
                TETile tile;
                if (!isPathShown) {
                    tile = HINT;
                    isPathShown = true;
                } else {
                    tile = FLOOR;
                    isPathShown = false;
                }
                currentGame.setDijkstraNearestSeedTile(tile);
                renderGameWorld(currentGame);
            }
        }
    }

    private void runGameOverOptions() {
        if (displayWorld) {
            if (currentGame.getNumFlowersLeft() == 0) {
                drawWonGame();
            } else {
                drawGameOver();
            }
            while (inputType.hasNextKey()) {
                char input = inputType.getNextKey();
                switch (input) {
                    case 'M':
                        StdDraw.clear();
                        drawMainMenu();
                        StdDraw.show();
                        runMainMenu();
                        break;
                    case 'Q':
                        quitGame();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void checkGameStatus() {
        if (currentGame.hasMonsterEatenAvatar()) {
            System.out.println("Game is over!");
            runGameOverOptions();
        }
        if (currentGame.getNumFlowersLeft() == 0) {
            currentGame.gameOver = true;
            runGameOverOptions();
        }
    }

    private void drawGameOver() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.RED);
        Font font = new Font("Monaco", Font.BOLD, 100);
        StdDraw.setFont(font);
        StdDraw.text(.5, .5, "GAME OVER!");
        font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.3, "Back to Main (M)");
        StdDraw.text(0.5, 0.25, "Quit Game (Q)");
        StdDraw.show();
    }

    private void loadGame() {  //Source: TA Itai Smith
        File worldFile = new File("./saved.txt");
        if (!worldFile.exists()) {
            System.out.println("No Game to Load");
            if (displayWorld) {
                quitGame();
            }
        } else {
            String worldInfo = readContentsAsString(worldFile);
            String[] worldInfoSplit = worldInfo.split(",");
            Iterator<String> itr = Arrays.stream(worldInfoSplit).iterator();
            boolean isGameOver = Boolean.parseBoolean(itr.next());
            boolean isMonterStuck = Boolean.parseBoolean(itr.next());
            long seed = Long.parseLong(itr.next());
            if (displayWorld) {
                ter = new TERenderer();
            }
            runWorld(seed, false, isGameOver, isMonterStuck);
            Point avatarPosition = new Point(Integer.parseInt(itr.next()),
                    Integer.parseInt(itr.next()));
            currentGame.setAvatarPos(avatarPosition);
            currentGame.setEntityTile(avatarPosition, AVATAR_ID);
            currentGame.numMonstersLeft = Integer.parseInt(itr.next());
            for (int i = 0; i < currentGame.getNumMonstersLeft(); i++) {
                int x = Integer.parseInt(itr.next());
                int y = Integer.parseInt(itr.next());
                Point place = new Point(x, y);
                int id;
                if (currentGame.isMonstersStuck()) {
                    id = MONSTER_STUCK_ID;
                } else {
                    id = MONSTER_ID;
                }
                currentGame.setEntityTile(place, id);
                currentGame.getMonsterPoints().add(place);
            }
            currentGame.numFlowersLeft = Integer.parseInt(itr.next());
            for (int i = 0; i < currentGame.getNumFlowersLeft(); i++) {
                int x = Integer.parseInt(itr.next());
                int y = Integer.parseInt(itr.next());
                Point place = new Point(x, y);
                currentGame.setEntityTile(place, FLOWER_ID);
                currentGame.getFlowerPoints().add(place);
            }
            currentGame.numSeedsLeft = Integer.parseInt(itr.next());
            for (int i = 0; i < currentGame.getNumSeedsLeft(); i++) {
                int x = Integer.parseInt(itr.next());
                int y = Integer.parseInt(itr.next());
                Point place = new Point(x, y);
                currentGame.setEntityTile(place, MAGIC_ID);
                currentGame.getSeedPoints().add(place);
            }
            currentGame.monstersStuck = Boolean.parseBoolean(itr.next());
            currentGame.movesLeftwithGlue = Integer.parseInt(itr.next());
            currentGame.runDijkstra();
            startGame();
            System.out.println("Game Loaded!");
        }
    }

    private void quitGame() {
        if (displayWorld) {
            System.exit(0);
            System.out.println("Game Quit!");
        }
    }

    private void saveGame() { //Source: TA Itai Smith
        File worldFile = new File("saved.txt");
        if (!worldFile.exists()) {
            try {
                worldFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String result = gameInfoToString();
        writeContents(worldFile, result);
        System.out.println("Game Save!");
    }

    private String gameInfoToString() {
        String info = "";
        info = info + currentGame.isGameOver() + ",";
        info = info + currentGame.isMonstersStuck() + ",";
        info = info + currentGame.getSeed() + ",";
        info = info + currentGame.getAvatarPos().getX() + ",";
        info = info + currentGame.getAvatarPos().getY() + ",";
        info = info + currentGame.getNumMonstersLeft() + ",";
        for (Point p : currentGame.getMonsterPoints()) {
            info = info + p.getX() + ",";
            info = info + p.getY() + ",";
        }
        info = info + currentGame.getNumFlowersLeft() + ",";
        for (Point p : currentGame.getFlowerPoints()) {
            info = info + p.getX() + ",";
            info = info + p.getY() + ",";
        }
        info = info + currentGame.getNumSeedsLeft() + ",";
        for (Point p : currentGame.getSeedPoints()) {
            info = info + p.getX() + ",";
            info = info + p.getY() + ",";
        }
        info = info + currentGame.monstersStuck + ",";
        info = info + currentGame.getMovesLeftWithGlue();
        return info;
    }

    /*
     * Keyboard and Mouse input related methods
     */
    private boolean hasMouseMoved() {
        if (displayWorld) {
            Point currPos = new Point((int) StdDraw.mouseX(), (int) StdDraw.mouseY());
            if (!currPos.equals(mousePos)) {
                mousePos = currPos;
                return true;
            }
        }
        return false;
    }

    private void moveAvatar(char input) {
        Point newPos = null;
        Point oldPos = currentGame.getAvatarPos();

        switch (input) {
            case 'W':
                newPos = oldPos.getTopPoint();
                break;
            case 'A':
                newPos = oldPos.getLeftPoint();
                break;
            case 'S':
                newPos = oldPos.getBottomPoint();
                break;
            case 'D':
                newPos = oldPos.getRightPoint();
                break;
            default:
                break;
        }

        if (currentGame.canMoveTo(newPos)) {
            if (currentGame.monstersStuck) {
                currentGame.reduceMonsterGlue();
                renderGameWorld(currentGame);
            }
            if (thingThere(newPos, FLOWER)) {
                collectFlower(newPos);
            }
            if (thingThere(newPos, SEED)) {
                currentGame.removeSeed(newPos);
                currentGame.glueMonsters();
            }
            currentGame.setAvatarPos(newPos);
            currentGame.setEntityTile(oldPos, newPos, AVATAR_ID);
        }
    }


    private boolean thingThere(Point p, TETile type) {
        return currentGame.getTileWorld()[p.getX()][p.getY()].equals(type);
    }

    private void collectFlower(Point p) {
        currentGame.removeFlower(p);
    }

    private void drawWonGame() {
        if (displayWorld) {
            StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.GREEN.darker());
            Font font = new Font("Monaco", Font.BOLD, 100);
            StdDraw.setFont(font);
            StdDraw.text(.5, .5, "YOU WON!");
            font = new Font("Monaco", Font.BOLD, 15);
            StdDraw.setFont(font);
            StdDraw.text(0.5, 0.3, "Back to Main (M)");
            StdDraw.text(0.5, 0.25, "Quit Game (Q)");
            StdDraw.show();
        }
    }

    private boolean isANumber(char input) {
        return NUMBERS.contains(Character.toString(input));
    }

    private boolean isMovementKey(char input) {
        return MOVEMENT_KEYS.contains(Character.toString(input));
    }

    private boolean isSavingKey(char input) {
        if (input == ':') {
            while (inputType.hasNextKey()) {
                char nextInput = inputType.getNextKey();
                if (nextInput == 'Q') {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPathKey(char input) {
        return input == 'P';
    }

    private boolean isHintKey(char input) {
        return input == 'O';
    }

    /*
     * Java file system methods
     * Deals with reading from and writing into save file, saved.txt
     */

    /*
     * Source: https://www.codejava.net/java-se/file-io/how-to-read-and-write-text-file-in-java
     */
    private String readContentsAsString(File file) {
        String line = "";
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            line = bufferedReader.readLine();
            reader.close();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
            return line;
        }
    }

    /*
     * Source: https://www.w3schools.com/java/java_files_create.asp
     */
    private void writeContents(File file, String text) {
        try {
            FileWriter myWriter = new FileWriter("saved.txt", false);
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        inputType = new StringInput(input);
        displayWorld = false;
        runMainMenu();
        return currentGame.getTileWorld();
    }
//    private void printWorld(TETile[][] world) {
//        for (int i = HEIGHT - 1; i > 0; i--) {
//            for (int j = 0; j < WIDTH - 1; j++) {
//                if ( j == (WIDTH - 2)) {
//                    System.out.println(world[j][i].character());
//                } else {
//                    System.out.print(world[j][i].character());
//                }
//            }
//        }
//    }
}
