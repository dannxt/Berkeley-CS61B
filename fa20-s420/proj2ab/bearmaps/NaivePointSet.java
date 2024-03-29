package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> points;

    public NaivePointSet(List<Point> p) {
        points = new ArrayList<>();
        for (Point i : p) {
            points.add(i);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Point queryP = new Point(x, y);
        Point bestP = points.get(0);
        for (Point p : points) {
            if (Point.distance(queryP, p) < Point.distance(queryP, bestP)) {
                bestP = p;
            }
        }
        return bestP;

    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        Point p4 = new Point(3.01, 4.01);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3, p4));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.01
        System.out.println(ret.getY()); // evaluates to 4.01
    }
}
