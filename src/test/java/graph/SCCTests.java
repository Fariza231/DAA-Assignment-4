package graph;

import graph.scc.TarjanSCC;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class SCCTests {

    @Test
    public void testSimpleCycle() {
        // 0 → 1 → 2 → 0  forms one SCC
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 3; i++) adj.add(new ArrayList<>());
        adj.get(0).add(1);
        adj.get(1).add(2);
        adj.get(2).add(0);

        TarjanSCC t = new TarjanSCC(adj);
        List<List<Integer>> sccs = t.getSCCs();

        assertEquals(1, sccs.size());
        assertEquals(3, sccs.get(0).size());
    }

    @Test
    public void testAcyclic() {
        // 0 → 1 → 2, no cycles, so 3 SCCs
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 3; i++) adj.add(new ArrayList<>());
        adj.get(0).add(1);
        adj.get(1).add(2);

        TarjanSCC t = new TarjanSCC(adj);
        List<List<Integer>> sccs = t.getSCCs();

        assertEquals(3, sccs.size());
    }
}
