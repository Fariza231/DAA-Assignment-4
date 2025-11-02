package graph.scc;
import java.util.*;

public class TarjanSCC {
    private int n, time;
    private List<List<Integer>> adj;
    private int[] ids, low;
    private boolean[] onStack;
    private Stack<Integer> stack;
    private List<List<Integer>> sccs;

    public TarjanSCC(List<List<Integer>> adj) {
        this.adj = adj;
        n = adj.size();
        ids = new int[n];
        low = new int[n];
        onStack = new boolean[n];
        Arrays.fill(ids, -1);
        stack = new Stack<>();
        sccs = new ArrayList<>();
        for (int i = 0; i < n; i++) if (ids[i] == -1) dfs(i);
    }

    private void dfs(int at) {
        stack.push(at);
        onStack[at] = true;
        ids[at] = low[at] = ++time;

        for (int to : adj.get(at)) {
            if (ids[to] == -1) dfs(to);
            if (onStack[to]) low[at] = Math.min(low[at], low[to]);
        }

        if (ids[at] == low[at]) {
            List<Integer> component = new ArrayList<>();
            while (true) {
                int node = stack.pop();
                onStack[node] = false;
                component.add(node);
                if (node == at) break;
            }
            sccs.add(component);
        }
    }

    public List<List<Integer>> getSCCs() { return sccs; }
}
