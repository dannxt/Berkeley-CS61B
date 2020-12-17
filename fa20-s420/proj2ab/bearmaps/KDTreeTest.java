package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    /* Create data for insertion into tree*/
    private List<Point> createPointList(int N) {
        List<Point> pointL = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Point p = new Point((Math.random() * 2000) - 1000, (Math.random() * 2000) - 1000);
            pointL.add(p);
        }
        return pointL;
    }

    @Test
    public void testCorrectness() {
        int N = 10000;
        List<Point> dataL = createPointList(N);
        NaivePointSet nPList = new NaivePointSet(dataL);
        KDTree testTree = new KDTree(dataL);

        for (int i = 0; i < N; i++) {
            double x = (Math.random() * 2000) - 1000;
            double y = (Math.random() * 2000) - 1000;
            Point pQuery = new Point(x, y);

            Point bestNP = nPList.nearest(x, y);
            Point bestKD = testTree.nearest(x, y);
            double expected = Point.distance(bestNP, pQuery);
            double actual = Point.distance(bestKD, pQuery);
            assertEquals(expected, actual, 0.00000001);
        }
        System.out.println("****** Correctness Test Passed!! ******");
        System.out.println(" ");
    }

    @Test
    public void constructorTimingTable() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        constructorTestHelper(Ns, times, opCounts, 31250);
        constructorTestHelper(Ns, times, opCounts, 62500);
        constructorTestHelper(Ns, times, opCounts, 125000);
        constructorTestHelper(Ns, times, opCounts, 250000);
        constructorTestHelper(Ns, times, opCounts, 500000);
        constructorTestHelper(Ns, times, opCounts, 1000000);
        constructorTestHelper(Ns, times, opCounts, 2000000);
        System.out.println("Timing table for Kd-Tree Construction");
        printTimingTable(Ns, times, opCounts);
        System.out.println(" ");
    }

    private void constructorTestHelper(List<Integer> Ns, List<Double> times, List<Integer> opCounts, int N) {
        List<Point> dataL = createPointList(N);
        Stopwatch sw = new Stopwatch();
        new KDTree(dataL);
        double elapsedTime = sw.elapsedTime();
        Ns.add(N);
        times.add(elapsedTime);
        opCounts.add(N);
    }

    @Test
    public void nearestKDTest() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        nearestKDTestHelper(Ns, times, opCounts, 31250);
        nearestKDTestHelper(Ns, times, opCounts, 62500);
        nearestKDTestHelper(Ns, times, opCounts, 125000);
        nearestKDTestHelper(Ns, times, opCounts, 250000);
        nearestKDTestHelper(Ns, times, opCounts, 500000);
        nearestKDTestHelper(Ns, times, opCounts, 1000000);
        System.out.println("Timing table for Kd-Tree Nearest");
        printTimingTable(Ns, times, opCounts);
        System.out.println(" ");
    }

    private void nearestKDTestHelper(List<Integer> Ns, List<Double> times, List<Integer> opCounts, int N) {
        List<Point> dataL = createPointList(N);
        KDTree testTree = new KDTree(dataL);
        int ops = 1000000;

        Ns.add(N);
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < ops; i++) {
            double x = (Math.random() * 2000) - 1000;
            double y = (Math.random() * 2000) - 1000;
            testTree.nearest(x, y);
        }
        double elapsedTime = sw.elapsedTime();
        times.add(elapsedTime);
        opCounts.add(ops);
    }

    @Test
    public void nearestNaiveTest() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        nearestNaiveTestHelper(Ns, times, opCounts, 125);
        nearestNaiveTestHelper(Ns, times, opCounts, 250);
        nearestNaiveTestHelper(Ns, times, opCounts, 500);
        nearestNaiveTestHelper(Ns, times, opCounts, 1000);
        nearestNaiveTestHelper(Ns, times, opCounts, 2000);
        System.out.println("Timing table for Naive Nearest");
        printTimingTable(Ns, times, opCounts);
        System.out.println(" ");
    }

    private void nearestNaiveTestHelper(List<Integer> Ns, List<Double> times, List<Integer> opCounts, int N) {
        List<Point> dataL = createPointList(N);
        NaivePointSet testSet = new NaivePointSet(dataL);
        int ops = 1000000;

        Ns.add(N);
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < ops; i++) {
            double x = (Math.random() * 2000) - 1000;
            double y = (Math.random() * 2000) - 1000;
            testSet.nearest(x, y);
        }
        double elapsedTime = sw.elapsedTime();
        times.add(elapsedTime);
        opCounts.add(ops);
    }
}

