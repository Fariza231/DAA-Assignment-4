package utils;

import java.util.*;

public class GraphUtils {
    public static String prettySCCs(List<List<Integer>> sccs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sccs.size(); i++) {
            sb.append(String.format("SCC %d (size=%d): %s\n", i, sccs.get(i).size(), sccs.get(i).toString()));
        }
        return sb.toString();
    }

    public static String prettyAdjList(List<List<int[]>> adj) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < adj.size(); i++) {
            sb.append(i).append(" -> ");
            for (int[] e : adj.get(i)) sb.append(String.format("(%d,%d)", e[0], e[1]));
            sb.append("\n");
        }
        return sb.toString();
    }
}
