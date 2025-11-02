package graph.dagsp;
import java.util.*;

public class DAGLongestPaths {
    private int n;
    private List<List<int[]>> adj;
    private int[] dist, parent;

    public DAGLongestPaths(List<List<int[]>> adj, int source) {
        this.adj = adj;
        this.n = adj.size();
        dist = new int[n];
        parent = new int[n];
        Arrays.fill(dist, Integer.MIN_VALUE);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        List<Integer> topo = topologicalOrder();
        for (int u : topo) {
            if (dist[u] != Integer.MIN_VALUE) {
                for (int[] e : adj.get(u)) {
                    int v = e[0], w = e[1];
                    if (dist[u] + w > dist[v]) {
                        dist[v] = dist[u] + w;
                        parent[v] = u;
                    }
                }
            }
        }
    }

    private List<Integer> topologicalOrder() {
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++)
            for (int[] e : adj.get(u)) indeg[e[0]]++;
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) q.add(i);

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int[] e : adj.get(u)) if (--indeg[e[0]] == 0) q.add(e[0]);
        }
        return order;
    }

    public int[] getDistances() { return dist; }

    public List<Integer> getCriticalPath() {
        int maxDist = Integer.MIN_VALUE, end = -1;
        for (int i = 0; i < n; i++) {
            if (dist[i] > maxDist) {
                maxDist = dist[i];
                end = i;
            }
        }
        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = parent[at]) path.add(at);
        Collections.reverse(path);
        return path;
    }

    public int getLongestDistance() {
        int max = Integer.MIN_VALUE;
        for (int d : dist) max = Math.max(max, d);
        return max;
    }
}
