import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about SLList getLast method.
 */
public class TimeSLList {
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

    private static void testSLList(SLList<Integer> testList, List<Integer> Ns, List<Double> times, List<Integer> opCounts, int maxN) {
        int N = 0;

        while (N < maxN) {
            testList.addLast(0);
            N += 1;
        }
        Stopwatch sw = new Stopwatch();
        int op = 0;
        while (op < 10000) {
            testList.getLast();
            op += 1;
        }
        double timeInSeconds = sw.elapsedTime();
        Ns.add(maxN);
        times.add(timeInSeconds);
        opCounts.add(10000);
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        SLList<Integer> testList = new SLList<>();
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        testSLList(testList, Ns, times, opCounts, 1000);
        testSLList(testList, Ns, times, opCounts, 2000);
        testSLList(testList, Ns, times, opCounts, 4000);
        testSLList(testList, Ns, times, opCounts, 8000);
        testSLList(testList, Ns, times, opCounts, 16000);
        testSLList(testList, Ns, times, opCounts, 32000);
        testSLList(testList, Ns, times, opCounts, 64000);;
        printTimingTable(Ns, times, opCounts);
    }

}
