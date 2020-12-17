package bearmaps.proj2c;

import bearmaps.proj2ab.DoubleMapPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private int numStatesExplored;
    private double explorationTime;
    private Map<Vertex, List<Object>> vertexListMap;
    private ExtrinsicMinPQ<Vertex> PQ;
    private List<Vertex> solution;
    private double solutionWeight;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        vertexListMap = new HashMap<>();
        PQ = new DoubleMapPQ<>();
        solution = new ArrayList<>();
        PQ.add(start, 0 + input.estimatedDistanceToGoal(start, end));
        addToHM(start, 0, start);
        Vertex p;

        while (explorationTime < timeout) {
            Stopwatch sw = new Stopwatch();
            if (PQ.size() == 0) {
                outcome = SolverOutcome.UNSOLVABLE;
                return;
            }

            if (PQ.getSmallest().equals(end)) {
                Vertex ptr = end;
                while (!ptr.equals(start)) {
                    solution.add(0, ptr);
                    ptr = edgeTo(ptr);
                }
                solution.add(0, start);
                solutionWeight = distTo(end);
                outcome = SolverOutcome.SOLVED;
                return;
            }

            p = PQ.removeSmallest();
            numStatesExplored++;
            for (WeightedEdge<Vertex> e : input.neighbors(p)) {
                relax(e, input.estimatedDistanceToGoal(e.to(), end));
            }
            explorationTime = explorationTime + sw.elapsedTime();
        }
        outcome = SolverOutcome.TIMEOUT;
    }

    private void addToHM(Vertex v, double distTo, Vertex edgeTo) {
        List<Object> values = new ArrayList<>();
        values.add(distTo);
        values.add(edgeTo);
        vertexListMap.put(v, values);
    }

    private double distTo(Vertex v) {
        return (double) vertexListMap.get(v).get(0);
    }

    private Vertex edgeTo(Vertex v) {
        return (Vertex) vertexListMap.get(v).get(1);
    }

    private void setDistTo(Vertex v, double d) {
        vertexListMap.get(v).set(0, d);
    }

    private void setEdgeTo(Vertex v, Vertex g) {
        vertexListMap.get(v).set(1, g);
    }


    private void relax(WeightedEdge<Vertex> e, double h) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();
        double distP = distTo(p);

        if (!vertexListMap.containsKey(q)) {
            addToHM(q, Double.POSITIVE_INFINITY, null);
        }

        double distQ = distTo(q);

        if (distP + w < distQ) {
            setDistTo(q, distP + w);
            setEdgeTo(q, p);
            if (PQ.contains(q)) {
                PQ.changePriority(q, distP + w + h);
            } else {
                PQ.add(q, distP + w + h);
            }
        }
    }

    public SolverOutcome outcome() {
        return outcome;
    }

    public List<Vertex> solution() {
        return solution;
    }

    public double solutionWeight() {
        return solutionWeight;
    }

    public int numStatesExplored() {
        return numStatesExplored;
    }

    public double explorationTime() {
        return explorationTime;
    }
}
