package graph;

import graph.scc.TarjanSCC;
import graph.topo.TopologicalSort;
import graph.dagsp.DAGShortestPaths;
import graph.dagsp.DAGLongestPaths;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class GraphTests {

    @Test
    public void testEmptyGraph() {
        List<List<Integer>> adj = new ArrayList<>();
        TarjanSCC scc = new TarjanSCC(adj);
        assertTrue(scc.getSCCs().isEmpty(), "Empty graph should have 0 SCCs");
    }

    @Test
    public void testSimpleCycleSCC() {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 3; i++) adj.add(new ArrayList<>());
        adj.get(0).add(1);
        adj.get(1).add(2);
        adj.get(2).add(0);

        TarjanSCC scc = new TarjanSCC(adj);
        assertEquals(1, scc.getSCCs().size(), "Cycle of 3 nodes → 1 SCC");
    }

    @Test
    public void testTopologicalSort() {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 4; i++) adj.add(new ArrayList<>());
        adj.get(0).add(1);
        adj.get(0).add(2);
        adj.get(1).add(3);
        adj.get(2).add(3);

        List<Integer> topo = TopologicalSort.sort(adj);
        assertNotNull(topo, "Topo order should not be null");
        assertEquals(4, topo.size());
        assertTrue(topo.indexOf(0) < topo.indexOf(3));
    }

    @Test
    public void testDAGShortestPath() {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < 4; i++) adj.add(new ArrayList<>());
        adj.get(0).add(new int[]{1, 1});
        adj.get(0).add(new int[]{2, 4});
        adj.get(1).add(new int[]{2, 2});
        adj.get(2).add(new int[]{3, 3});
        adj.get(1).add(new int[]{3, 7});

        DAGShortestPaths sp = new DAGShortestPaths(adj, 0);
        int[] dist = sp.getDistances();
        assertEquals(0, dist[0]);
        assertEquals(1, dist[1]);
        assertEquals(3, dist[2]);
        assertEquals(6, dist[3]);
    }

    @Test
    public void testDAGLongestPath() {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < 4; i++) adj.add(new ArrayList<>());
        adj.get(0).add(new int[]{1, 2});
        adj.get(1).add(new int[]{2, 3});
        adj.get(2).add(new int[]{3, 4});

        DAGLongestPaths lp = new DAGLongestPaths(adj, 0);
        assertEquals(9, lp.getLongestDistance(), 1e-6);
        assertEquals(Arrays.asList(0, 1, 2, 3), lp.getCriticalPath());
    }
}
