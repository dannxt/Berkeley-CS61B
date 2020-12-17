import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about AList construction.
 */
public class TimeAList {
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

    private static void testAList(AList<Integer> testList, List<Integer> Ns, List<Double> times, List<Integer> opCounts, int maxN) {
        int N = 0;

        Stopwatch sw = new Stopwatch();
        while (N < maxN) {
            testList.addLast(0);
            N += 1;
        }
        double timeInSeconds = sw.elapsedTime();
        Ns.add(maxN);
        times.add(timeInSeconds);
        opCounts.add(maxN);
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        AList<Integer> testList = new AList<>();
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        testAList(testList, Ns, times, opCounts, 1000);
        testAList(testList, Ns, times, opCounts, 2000);
        testAList(testList, Ns, times, opCounts, 4000);
        testAList(testList, Ns, times, opCounts, 8000);
        testAList(testList, Ns, times, opCounts, 16000);
        testAList(testList, Ns, times, opCounts, 32000);
        testAList(testList, Ns, times, opCounts, 64000);;
        printTimingTable(Ns, times, opCounts);
    }
}
//
//        N = 0;
//        maxN = 2000;
//        Stopwatch sw2000 = new Stopwatch();
//        while (N < maxN) {
//            testList.addLast(0);
//            N += 1;
//        }
//        double timeInSeconds2000 = sw2000.elapsedTime();
//        Ns.add(maxN);
//        times.add(timeInSeconds2000);
//        opCounts.add(maxN);
//
//        N = 0;
//        maxN = 2000;
//        Stopwatch sw2000 = new Stopwatch();
//        while (N < maxN) {
//            testList.addLast(0);
//            N += 1;
//        }
//        double timeInSeconds2000 = sw2000.elapsedTime();
//        Ns.add(maxN);
//        times.add(timeInSeconds2000);
//        opCounts.add(maxN);
//
//        N = 0;
//        maxN = 4000;
//        Stopwatch sw4000 = new Stopwatch();
//        while (N < maxN) {
//            testList.addLast(0);
//            N += 1;
//        }
//        double timeInSeconds4000 = sw4000.elapsedTime();
//        Ns.add(maxN);
//        times.add(timeInSeconds4000);
//        opCounts.add(maxN);
//
//        N = 0;
//        maxN = 2000;
//        Stopwatch sw8000 = new Stopwatch();
//        while (N < maxN) {
//            testList.addLast(0);
//            N += 1;
//        }
//        double timeInSeconds8000 = sw8000.elapsedTime();
//        Ns.add(maxN);
//        times.add(timeInSeconds8000);
//        opCounts.add(maxN);