package graph.scc;
import java.util.*;

public class CondensationBuilder {

    public static List<List<Integer>> buildCondensation(
            List<List<Integer>> adj,
            List<List<Integer>> sccs) {

        Map<Integer, Integer> compMap = new HashMap<>();
        for (int i = 0; i < sccs.size(); i++)
            for (int node : sccs.get(i))
                compMap.put(node, i);

        List<Set<Integer>> dagSet = new ArrayList<>();
        for (int i = 0; i < sccs.size(); i++) dagSet.add(new HashSet<>());

        for (int u = 0; u < adj.size(); u++) {
            for (int v : adj.get(u)) {
                int cu = compMap.get(u);
                int cv = compMap.get(v);
                if (cu != cv) dagSet.get(cu).add(cv);
            }
        }

        List<List<Integer>> dag = new ArrayList<>();
        for (Set<Integer> s : dagSet)
            dag.add(new ArrayList<>(s));

        return dag;
    }
}
