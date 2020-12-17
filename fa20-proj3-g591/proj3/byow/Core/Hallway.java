package byow.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hallway {
    private Point startPoint;
    private char direction;
    private int length;
    private Random random;
    private List<Point> points;
    private int MAX_HALLWAY_LENGTH;
    private int MIN_HALLWAY_LENGTH;
    private static final int HALLWAY_LENGTH_FACTOR = 3;

    public Hallway(Point startP, char d, Random r) {
        points = new ArrayList<>();
        startPoint = startP;
        direction = d;
        random = r;
        switch (direction) {
            case 'N':
                MAX_HALLWAY_LENGTH = (Constants.GRID_HEIGHT
                        - startPoint.getY()) / HALLWAY_LENGTH_FACTOR;
                break;
            case 'E':
                MAX_HALLWAY_LENGTH = (Constants.GRID_WIDTH
                        - startPoint.getX()) / HALLWAY_LENGTH_FACTOR;
                break;
            case 'S':
                MAX_HALLWAY_LENGTH = startPoint.getY() / HALLWAY_LENGTH_FACTOR;
                break;
            case 'W':
                MAX_HALLWAY_LENGTH = startPoint.getX() / HALLWAY_LENGTH_FACTOR;
                break;
            default:
                break;
        }
        MIN_HALLWAY_LENGTH = Math.min(MAX_HALLWAY_LENGTH - 1, 3);
        length = RandomUtils.uniform(random, MIN_HALLWAY_LENGTH, MAX_HALLWAY_LENGTH);
        createPoints(startP, d, length);
    }

    private void createPoints(Point startP, char d, int l) {
        switch (d) {
            case 'N':
                for (int i = 0; i < l; i++) {
                    Point p = new Point(startP.getX(), startP.getY() + i);
                    points.add(p);
                }
                break;
            case 'E':
                for (int i = 0; i < l; i++) {
                    Point p = new Point(startP.getX() + i, startP.getY());
                    points.add(p);
                }
                break;
            case 'S':
                for (int i = 0; i < l; i++) {
                    Point p = new Point(startP.getX(), startP.getY() - i);
                    points.add(p);
                }
                break;
            case 'W':
                for (int i = 0; i < l; i++) {
                    Point p = new Point(startP.getX() - i, startP.getY());
                    points.add(p);
                }
                break;
            default:
                break;
        }
    }

    public char getDirection() {
        return direction;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        int s = points.size();
        if (s == 0) {
            return startPoint;
        }
        return points.get(s - 1);
    }

    public int getLength() {
        return length;
    }
}
