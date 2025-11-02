package graph;

import graph.dagsp.DAGShortestPaths;
import graph.dagsp.DAGLongestPaths;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class DAGSPTests {

    @Test
    public void testShortestOnDAG() {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < 3; i++) adj.add(new ArrayList<>());
        adj.get(0).add(new int[]{1, 2});
        adj.get(0).add(new int[]{2, 5});
        adj.get(1).add(new int[]{2, 1});

        DAGShortestPaths sp = new DAGShortestPaths(adj, 0);
        int[] d = sp.getDistances();
        assertEquals(0, d[0]);
        assertEquals(2, d[1]);
        assertEquals(3, d[2]);
    }

    @Test
    public void testLongestOnDAG() {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < 3; i++) adj.add(new ArrayList<>());
        adj.get(0).add(new int[]{1, 2});
        adj.get(0).add(new int[]{2, 1});
        adj.get(1).add(new int[]{2, 1});

        DAGLongestPaths lp = new DAGLongestPaths(adj, 0);
        assertEquals(3, lp.getLongestDistance());
        List<Integer> path = lp.getCriticalPath();
        assertEquals(Arrays.asList(0, 1, 2), path);
    }
}
