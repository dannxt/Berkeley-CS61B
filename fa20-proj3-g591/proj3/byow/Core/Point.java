package byow.Core;

import java.util.Objects;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getTopLeftPoint() {
        return new Point(x - 1, y + 1);
    }

    public Point getTopPoint() {
        return new Point(x, y + 1);
    }

    public Point getTopRightPoint() {
        return new Point(x + 1, y + 1);
    }

    public Point getLeftPoint() {
        return new Point(x - 1, y);
    }

    public Point getRightPoint() {
        return new Point(x + 1, y);
    }

    public Point getBottomLeftPoint() {
        return new Point(x - 1, y - 1);
    }

    public Point getBottomPoint() {
        return new Point(x, y - 1);
    }

    public Point getBottomRightPoint() {
        return new Point(x + 1, y - 1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        return this.getX() == ((Point) other).getX() && this.getY() == ((Point) other).getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

