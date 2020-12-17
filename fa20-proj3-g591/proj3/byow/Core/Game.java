package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

import java.util.*;

public class Game {
    private long seed;
    private TETile[][] tileWorld;
    private TETile avatar, monster, floor, flower, magicSeed, stuckMonster, armedAvatar;
    private EdgeWeightedGraph graph;
    private Random random;
    private HashMap<Integer, Point> vertexToPoint;
    private HashMap<Point, Integer> pointToVertex;
    public boolean gameOver, monstersStuck;
    private Point avatarP;
    private List<Point> monsterPoints;
    private List<Point> flowerPoints;
    private List<Point> seedPoints;
    public int movesLeftwithGlue;
    public int numFlowersLeft;
    public int numSeedsLeft;
    public int numMonstersLeft;
    private static final int AVATAR_ID = Constants.AVATAR_ID;
    private static final int MONSTER_ID = Constants.MONSTER_ID;
    private static final int FLOWER_ID = Constants.FLOWER_ID;
    private static final int MAGIC_ID = Constants.MAGIC_ID;
    private static final int MONSTER_STUCK_ID = Constants.MONSTER_STUCK_ID;
    private static final int OFFSET = Constants.GRID_OFFSET;
    private static final int WIDTH = Constants.GRID_WIDTH;
    private static final int HEIGHT = Constants.GRID_HEIGHT;
    private static final int NUM_MONSTER = 3;
    private static final int NUM_FLOWERS = 4;
    private static final int NUM_SEEDS = 20;

    public Game(boolean newGame, long s, boolean gameO, boolean mStuck, TETile[][] tw, TETile ava, TETile mon, TETile monS, TETile flr, TETile flow, TETile ms, TETile armor) {
        random = new Random(s);
        seed = s;
        tileWorld = tw;
        avatar = ava;
        armedAvatar = armor;
        monster = mon;
        stuckMonster = monS;
        floor = flr;
        flower = flow;
        magicSeed = ms;
        gameOver = gameO;
        monstersStuck = mStuck;
        monsterPoints = new LinkedList<>();
        flowerPoints = new LinkedList<>();
        seedPoints = new LinkedList<>();
        if (newGame) {
            monstersStuck = false;
            numFlowersLeft = NUM_FLOWERS;
            numSeedsLeft = NUM_SEEDS;
            numMonstersLeft = NUM_MONSTER;
            movesLeftwithGlue = 0;
            spawnAvatar();
            spawnMonsters();
            spawnFlowers();
            spawnMagicSeeds();
            runDijkstra();
        }
    }

    public void runDijkstra() {
        mapVerticesToPoints();
        graph = new EdgeWeightedGraph(vertexToPoint.size());
        addEdges(graph);
    }

    public void spawnAvatar() {
        avatarP = getRandTilePosition(floor);
        setEntityTile(avatarP, AVATAR_ID);
    }

    public void spawnMonsters() {
        for (int monsterNum = 0; monsterNum < NUM_MONSTER; monsterNum++) {
            Point monsterPos = getRandTilePosition(floor);
            monsterPoints.add(monsterPos);
            setEntityTile(monsterPos, MONSTER_ID);
        }
    }

    private void spawnMagicSeeds() {
        for (int seedNum = 0; seedNum < NUM_SEEDS; seedNum++) {
            Point seedPos = getRandTilePosition(floor);
            seedPoints.add(seedPos);
            setEntityTile(seedPos, MAGIC_ID);
        }
    }

    private void spawnFlowers() {
        for (int flowerNum = 0; flowerNum < NUM_FLOWERS; flowerNum++) {
            Point flowerPos = getRandTilePosition(floor);
            flowerPoints.add(flowerPos);
            setEntityTile(flowerPos, FLOWER_ID);
        }
    }

    public void startMonsterChase() {
        for (int monsterNum = 0; monsterNum < NUM_MONSTER; monsterNum++) {
            Point monsterP = monsterPoints.get(monsterNum);
            if (!monsterP.equals(avatarP)) {
                Point moveToPos = getNextDijkstraPosition(monsterP);
                if (flowerPoints.contains(moveToPos)) {
                    removeFlower(moveToPos);
                } else if (seedPoints.contains(moveToPos)) {
                    removeSeed(moveToPos);
                }
                setEntityTile(monsterP, moveToPos, MONSTER_ID);
                monsterPoints.set(monsterNum, moveToPos);
            }
        }
    }

    public boolean hasMonsterEatenAvatar() {
        for (Point monsterP : monsterPoints) {
            if (monsterP.equals(avatarP)) {
                gameOver = true;
                break;
            }
        }
        return gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void reduceMonsterGlue() {
        movesLeftwithGlue = movesLeftwithGlue - 1;
        if (movesLeftwithGlue == -1) {
            movesLeftwithGlue = 0;
            monstersStuck = false;
        } else if (movesLeftwithGlue == 0) {
            for (Point monsterP : monsterPoints) {
                setEntityTile(monsterP, MONSTER_ID);
            }
        }
    }

    /*
     * Dijkstra Algorithm Implementation
     */
    private void mapVerticesToPoints() {
        vertexToPoint = new HashMap<>();
        pointToVertex = new HashMap<>();
        int vertexNum = 0;
        for (int i = 0; i < Constants.GRID_WIDTH; i++) {
            for (int j = 0; j < Constants.GRID_HEIGHT; j++) {
                if (!tileWorld[i][j].equals(Tileset.WALL)
                        && !tileWorld[i][j].equals(Tileset.NOTHING)
                        && !tileWorld[i][j].equals(Tileset.UI)) {
                    Point p = new Point(i, j);
                    vertexToPoint.put(vertexNum, p);
                    pointToVertex.put(p, vertexNum);
                    vertexNum = vertexNum + 1;
                }
            }
        }
    }

    private void addEdges(EdgeWeightedGraph g) {
        for (int v : vertexToPoint.keySet()) {
            Point bottom = vertexToPoint.get(v).getBottomPoint();
            if (canMoveTo(bottom)) {
                g.addEdge(new Edge(v, pointToVertex.get(bottom), 1.0));
            }
            Point left = vertexToPoint.get(v).getLeftPoint();
            if (canMoveTo(left)) {
                g.addEdge(new Edge(v, pointToVertex.get(left), 1.0));
            }
            Point right = vertexToPoint.get(v).getRightPoint();
            if (canMoveTo(right)) {
                g.addEdge(new Edge(v, pointToVertex.get(right), 1.0));
            }
            Point top = vertexToPoint.get(v).getTopPoint();
            if (canMoveTo(top)) {
                g.addEdge(new Edge(v, pointToVertex.get(top), 1.0));
            }
        }
    }

    private Point getNextDijkstraPosition(Point currPos) {
        DijkstraUndirectedSP solver = new DijkstraUndirectedSP(graph, pointToVertex.get(currPos));
        Iterable<Edge> solution = solver.pathTo(pointToVertex.get(avatarP));
        return vertexToPoint.get(solution.iterator().next().other(pointToVertex.get(currPos)));
    }

    private List<Point> getDijkstraPathList(Point currPos) {
        List<Point> result = new ArrayList<>();
        DijkstraUndirectedSP solver = new DijkstraUndirectedSP(graph, pointToVertex.get(currPos));
        Iterable<Edge> solution = solver.pathTo(pointToVertex.get(avatarP));
        Point cur = currPos;
        for (Edge edge : solution) {
            Point s = vertexToPoint.get(edge.other(pointToVertex.get(cur)));
            result.add(s);
            cur = s;
        }
        return result;
    }

    public void setDijkstraPathTile(Point currPos, TETile tile) {
        for (Point p : getDijkstraPathList(currPos)) {
            if (!p.equals(avatarP)
                    && !monsterPoints.contains(p)
                    && !flowerPoints.contains(p)
                    && !seedPoints.contains(p)) {
                tileWorld[p.getX()][p.getY()] = tile;
            }
        }
    }

    public void setDijkstraNearestSeedTile(TETile tile) {
        List<Point> pathToNearestSeed = getDijkstraPathList(seedPoints.get(0));
        for (Point s : seedPoints) {
            if (getDijkstraPathList(s).size() < pathToNearestSeed.size()) {
                pathToNearestSeed = getDijkstraPathList(s);
            }
        }
        for (Point p : pathToNearestSeed) {
            if (!p.equals(avatarP)
                    && !monsterPoints.contains(p)
                    && !flowerPoints.contains(p)
                    && !seedPoints.contains(p)) {
                tileWorld[p.getX()][p.getY()] = tile;
            }
        }
    }

    public boolean canMoveTo(Point p) {
        return !tileWorld[p.getX()][p.getY()].equals(Tileset.WALL)
                && !tileWorld[p.getX()][p.getY()].equals(Tileset.NOTHING)
                && !tileWorld[p.getX()][p.getY()].equals(Tileset.UI);
    }

    public void glueMonsters() {
        monstersStuck = true;
        movesLeftwithGlue = movesLeftwithGlue + 10;
        for (Point monsterP : monsterPoints) {
            setEntityTile(monsterP, MONSTER_STUCK_ID);
        }
    }

    private Point getRandTilePosition(TETile tile) {
        int x = RandomUtils.uniform(random, OFFSET, WIDTH - OFFSET);
        int y = RandomUtils.uniform(random, OFFSET, HEIGHT - OFFSET);
        while (tileWorld[x][y] != tile) {
            x = RandomUtils.uniform(random, OFFSET, WIDTH - OFFSET);
            y = RandomUtils.uniform(random, OFFSET, HEIGHT - OFFSET);
        }
        return new Point(x, y);
    }

    public void setEntityTile(Point entityP, int entityID) {
        TETile tile = switch (entityID) {
            case AVATAR_ID -> avatar;
            case MONSTER_ID -> monster;
            case FLOWER_ID -> flower;
            case MAGIC_ID -> magicSeed;
            case MONSTER_STUCK_ID -> stuckMonster;
            default -> throw new IllegalStateException("Unexpected value: " + entityID);
        };
        tileWorld[entityP.getX()][entityP.getY()] = tile;
    }

    public void setEntityTile(Point oldPos, Point newPos, int entityID) {
        TETile tile = switch (entityID) {
            case AVATAR_ID -> avatar;
            case MONSTER_ID -> monster;
            case FLOWER_ID -> flower;
            case MAGIC_ID -> magicSeed;
            case MONSTER_STUCK_ID -> stuckMonster;
            default -> throw new IllegalStateException("Unexpected value: " + entityID);
        };
        tileWorld[oldPos.getX()][oldPos.getY()] = Tileset.FLOOR;
        tileWorld[newPos.getX()][newPos.getY()] = tile;
    }

    public long getSeed() {
        return seed;
    }

    public TETile[][] getTileWorld() {
        return tileWorld;
    }

    public Point getAvatarPos() {
        return avatarP;
    }

    public void setAvatarPos(Point p) {
        avatarP = p;
    }

    public void removeFlower(Point p) {
        numFlowersLeft = numFlowersLeft - 1;
        flowerPoints.remove(p);
    }

    public int getNumFlowersLeft() {
        return numFlowersLeft;
    }

    public int getNumSeedsLeft() {
        return numSeedsLeft;
    }

    public int getNumMonstersLeft() {
        return numMonstersLeft;
    }

    public boolean isMonstersStuck() {
        return monstersStuck;
    }

    public int getMovesLeftWithGlue() {
        return movesLeftwithGlue;
    }

    public List<Point> getFlowerPoints() {
        return flowerPoints;
    }

    public List<Point> getSeedPoints() {
        return seedPoints;
    }

    public List<Point> getMonsterPoints() {
        return monsterPoints;
    }

    public void removeSeed(Point p) {
        numSeedsLeft = numSeedsLeft - 1;
        seedPoints.remove(p);
    }
}
