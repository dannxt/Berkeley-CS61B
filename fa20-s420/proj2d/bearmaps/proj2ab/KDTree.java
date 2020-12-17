package bearmaps.proj2ab;
import java.util.List;

public class KDTree implements PointSet {
    private class Node {
        private Point point;
        private Node left;
        private Node right;
        private int depth;

        Node(Point p, int d) {
            point = p;
            depth = d;
        }

        /* Returns the dimension compared for current Node */
        private int getDimension() {
            if ((depth + 1) % MAX_DIMENSION == 0) {
                return MAX_DIMENSION;
            }
            return (depth + 1) % MAX_DIMENSION;
        }
    }

    private Node root;
    private static final int MAX_DIMENSION = 2;

    public KDTree(List<Point> pList) {
        for (Point p : pList) {
            addNode(p);
        }
    }

    /* Add node to KDTree */
    private void addNode(Point p) {
        root = addNodeHelper(p, root, -1);
    }

    private Node addNodeHelper(Point p, Node n, int depth) {
        if (n == null) {
            return new Node(p, depth + 1);
        }

        if (n.point.equals(p)) {
            return n;
        }

        int cmpX = Double.compare(n.point.getX(), (p.getX()));
        int cmpY = Double.compare(n.point.getY(), (p.getY()));

        if ((n.getDimension() == MAX_DIMENSION && cmpY > 0)
                || (n.getDimension() != MAX_DIMENSION && cmpX > 0)) {
            n.left = addNodeHelper(p, n.left, depth + 1);
        } else {
            n.right = addNodeHelper(p, n.right, depth + 1);
        }
        return n;
    }

    @Override
    public Point nearest(double x, double y) {
        Point pQuery = new Point(x, y);
        return nearestHelper(pQuery, root, root.point);
    }

    private Point nearestHelper(Point pQuery, Node n, Point bestP) {
        Node goodSide;
        Node badSide;

        if (n == null) {
            return bestP;
        }

        if (Point.distance(pQuery, n.point) < Point.distance(pQuery, bestP)) {
            bestP = n.point;
        }

        int dimension = n.getDimension();
        int cmpX = Double.compare(n.point.getX(), (pQuery.getX()));
        int cmpY = Double.compare(n.point.getY(), (pQuery.getY()));

        if ((dimension == MAX_DIMENSION && cmpY > 0)
                || (dimension != MAX_DIMENSION && cmpX > 0)) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }

        bestP = nearestHelper(pQuery, goodSide, bestP);

        if (dimension % 2 == 0) {
            if (Math.pow(n.point.getY() - pQuery.getY(), 2) < Point.distance(pQuery, bestP)) {
                bestP = nearestHelper(pQuery, badSide, bestP);
            }
        } else {
            if (Math.pow(n.point.getX() - pQuery.getX(), 2) < Point.distance(pQuery, bestP)) {
                bestP = nearestHelper(pQuery, badSide, bestP);
            }
        }
        return bestP;
    }

    public static void main(String[] args) {

    }
}
