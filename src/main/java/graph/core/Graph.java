package graph.core;
import java.util.*;

public class Graph {
    private int n;
    private boolean directed;
    private List<List<int[]>> adj;

    public Graph(int n, boolean directed) {
        this.n = n;
        this.directed = directed;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new int[]{v, w});
        if (!directed) adj.get(v).add(new int[]{u, w});
    }

    public int size() { return n; }
    public List<List<int[]>> getAdj() { return adj; }
}
