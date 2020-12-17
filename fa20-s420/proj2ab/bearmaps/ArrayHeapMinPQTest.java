package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    ExtrinsicMinPQ<Double> nPQ = new NaiveMinPQ<>();
    ExtrinsicMinPQ<Double> aPQ = new ArrayHeapMinPQ<>();
    HashSet<Double> uniquesK = new HashSet<>();
    HashSet<Double> uniquesV = new HashSet<>();

    public void addData(int num) {
        int N = num;
        int total = 0;
        double k = 0;
        double p = 0;

        while (!uniquesK.contains(k) && !uniquesV.contains(p) && total < N) {
            nPQ.add(k, p);
            aPQ.add(k, p);
            uniquesK.add(k);
            uniquesV.add(p);
            total++;
            k = (StdRandom.uniform(-100000000, 100000000));
            p = (StdRandom.uniform(-100000000, 100000000));
        }
    }

    public void addOnlyN() {
        int N = 999999999;
        int total = 0;
        double k = 0;
        double p = 0;

        while (!uniquesK.contains(k) && !uniquesV.contains(p) && total < N) {
            nPQ.add(k, p);
            uniquesK.add(k);
            uniquesV.add(p);
            total++;
            k = (StdRandom.uniform(-100000000, 100000000));
            p = (StdRandom.uniform(-1000, 1000));
        }
    }

    public void addOnlyA() {
        int N = 999999999;
        int total = 0;
        double k = 0;
        double p = 0;

        while (!uniquesK.contains(k) && !uniquesV.contains(p) && total < N) {
            aPQ.add(k, p);
            uniquesK.add(k);
            uniquesV.add(p);
            total++;
            k = (StdRandom.uniform(-100000000, 100000000));
            p = (StdRandom.uniform(-1000, 1000));
        }
    }

    public void checkEquality(ExtrinsicMinPQ<Double> a, ExtrinsicMinPQ<Double> b) {
        while (a.size() != 0) {
            Double expected = a.removeSmallest();
            Double actual = b.removeSmallest();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testAddTiming() {
        long startN = System.currentTimeMillis();
        addOnlyN();
        long endN = System.currentTimeMillis();
        System.out.println("addOnlyN time: " + (endN - startN) / 1000.0 + " seconds.");

        long startA = System.currentTimeMillis();
        addOnlyA();
        long endA = System.currentTimeMillis();
        System.out.println("addOnlyA time: " + (endA - startA) / 1000.0 + " seconds.");
    }

    @Test
    public void testAddCorrectness() {
        addData(1000000);
        while (nPQ.size() != 0) {
            Double expected = nPQ.removeSmallest();
            Double actual = aPQ.removeSmallest();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testContains() {
        addData(1000000);
        double k = (StdRandom.uniform(-100000000, 100000000));
        assertEquals(nPQ.contains(k), aPQ.contains(k));
    }

    @Test
    public void testGetSmallest() {
        addData(1000000);
        while (aPQ.size() != 0) {
            Double expected = nPQ.getSmallest();
            Double actual = aPQ.getSmallest();
            assertEquals(expected, actual);
            aPQ.removeSmallest();
            nPQ.removeSmallest();
        }
    }

    @Test
    public void testSize() {
        addData(1000000);
        int expected = nPQ.size();
        int actual = nPQ.size();
        aPQ.removeSmallest();
        nPQ.removeSmallest();
        aPQ.getSmallest();
        nPQ.getSmallest();
        aPQ.add(121313.0031231, -1);
        nPQ.add(121313.0031231, -1);
        assertEquals(expected, actual);
    }

    @Test
    public void testChangePriorityCorrectness() {
        addData(1000000);
        for (int i = 0; i < 9999999; i++) {
            double k = (StdRandom.uniform(-100000000, 100000000));
            double p = (StdRandom.uniform(-100000000, 100000000));
            if (uniquesK.contains(k) && uniquesV.contains(p)) {
                aPQ.changePriority(k, p);
                nPQ.changePriority(k, p);
                checkEquality(aPQ, nPQ);
            }
        }
    }

    @Test
    public void testChangePriorityTiming1M() {
        addData(10000000);
        long startN = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            double k = (StdRandom.uniform(-1000000, 1000000));
            double p = (StdRandom.uniform(-100000000, 100000000));
            if (uniquesK.contains(k) && !uniquesV.contains(p)) {
                nPQ.changePriority(k, p);
            }
        }
        long endN = System.currentTimeMillis();
        System.out.println("changePriorityN 1M time: " + (endN - startN) / 1000.0 + " seconds.");

        long startA = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            double k = (StdRandom.uniform(-1000000, 1000000));
            double p = (StdRandom.uniform(-100000000, 100000000));
            if (uniquesK.contains(k) && !uniquesV.contains(p)) {
                aPQ.changePriority(k, p);
            }
        }
        long endA = System.currentTimeMillis();
        System.out.println("changePriorityA 1M time: " + (endA - startA) / 1000.0 + " seconds.");
    }

    @Test
    public void testChangePriorityTiming100M() {
        addData(1000000000);
        long startN = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            double k = (StdRandom.uniform(-1000000, 1000000));
            double p = (StdRandom.uniform(-100000000, 100000000));
            if (uniquesK.contains(k) && !uniquesV.contains(p)) {
                nPQ.changePriority(k, p);
            }
        }
        long endN = System.currentTimeMillis();
        System.out.println("changePriorityN 100M time: " + (endN - startN) / 1000.0 + " seconds.");

        long startA = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            double k = (StdRandom.uniform(-1000000, 1000000));
            double p = (StdRandom.uniform(-100000000, 100000000));
            if (uniquesK.contains(k) && !uniquesV.contains(p)) {
                aPQ.changePriority(k, p);
            }
        }
        long endA = System.currentTimeMillis();
        System.out.println("changePriorityA 100M time: " + (endA - startA) / 1000.0 + " seconds.");
    }

    @Test
    public void testChangePriorityTiming10000M() {
        addData(1000000000);
        long startN = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            double k = (StdRandom.uniform(-1000000, 1000000));
            double p = (StdRandom.uniform(-100000000, 100000000));
            if (uniquesK.contains(k) && !uniquesV.contains(p)) {
                nPQ.changePriority(k, p);
            }
        }
        long endN = System.currentTimeMillis();
        System.out.println("changePriorityN 10000M time: " + (endN - startN) / 1000.0 + " seconds.");

        long startA = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            double k = (StdRandom.uniform(-1000000, 1000000));
            double p = (StdRandom.uniform(-100000000, 100000000));
            if (uniquesK.contains(k) && !uniquesV.contains(p)) {
                aPQ.changePriority(k, p);
            }
        }
        long endA = System.currentTimeMillis();
        System.out.println("changePriorityA 10000M time: " + (endA - startA) / 1000.0 + " seconds.");
    }
}
