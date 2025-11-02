package app;

import graph.scc.*;
import graph.topo.*;
import graph.dagsp.*;
import org.json.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Unified Main class for Assignment 4:
 * - If run with no arguments → analyzes all JSON in /data and saves results.csv
 * - If run with one file path argument → runs detailed analysis for that file
 */
public class Main {

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                runBatchMode(); // analyze all files in /data
            } else {
                runSingleFile(args[0]); // analyze one specific file
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------- Single file detailed analysis --------------------
    private static void runSingleFile(String filePath) throws Exception {
        System.out.println("Loading graph from: " + filePath);

        String json = Files.readString(Paths.get(filePath));
        JSONObject obj = new JSONObject(json);
        int n = obj.getInt("n");
        JSONArray edges = obj.getJSONArray("edges");

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < edges.length(); i++) {
            JSONObject e = edges.getJSONObject(i);
            adj.get(e.getInt("u")).add(e.getInt("v"));
        }

        TarjanSCC tarjan = new TarjanSCC(adj);
        List<List<Integer>> sccs = tarjan.getSCCs();
        System.out.println("\nStrongly Connected Components:");
        for (int i = 0; i < sccs.size(); i++) {
            System.out.println("  Component " + i + ": " + sccs.get(i));
        }

        Map<Integer, Integer> compMap = new HashMap<>();
        for (int i = 0; i < sccs.size(); i++)
            for (int node : sccs.get(i)) compMap.put(node, i);

        List<List<int[]>> weightedDAG = new ArrayList<>();
        for (int i = 0; i < sccs.size(); i++) weightedDAG.add(new ArrayList<>());
        for (int i = 0; i < edges.length(); i++) {
            JSONObject e = edges.getJSONObject(i);
            int u = compMap.get(e.getInt("u"));
            int v = compMap.get(e.getInt("v"));
            int w = e.getInt("w");
            if (u != v) weightedDAG.get(u).add(new int[]{v, w});
        }

        List<List<Integer>> dag = new ArrayList<>();
        for (int i = 0; i < sccs.size(); i++) dag.add(new ArrayList<>());
        for (int u = 0; u < n; u++) {
            for (int v : adj.get(u)) {
                int cu = compMap.get(u);
                int cv = compMap.get(v);
                if (cu != cv && !dag.get(cu).contains(cv)) dag.get(cu).add(cv);
            }
        }

        System.out.println("\nCondensation DAG: " + dag);

        List<Integer> topoOrder = TopologicalSort.sort(dag);
        System.out.println("Topological Order: " + topoOrder);

        int source = obj.getInt("source");
        int sourceComp = compMap.get(source);

        DAGShortestPaths sp = new DAGShortestPaths(weightedDAG, sourceComp);
        System.out.println("\nShortest distances: " + Arrays.toString(sp.getDistances()));

        DAGLongestPaths lp = new DAGLongestPaths(weightedDAG, sourceComp);
        System.out.println("Critical path: " + lp.getCriticalPath());
        System.out.println("Longest distance: " + lp.getLongestDistance());
    }

    // -------------------- Batch mode for all /data JSONs --------------------
    private static void runBatchMode() throws Exception {
        File folder = new File("data");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null || files.length == 0) {
            System.out.println("No dataset files found in /data/");
            return;
        }

        try (FileWriter csv = new FileWriter("results.csv")) {
            csv.write("File,Vertices,Edges,SCCs,TopoOrder,Shortest(ms),Longest(ms)\n");

            System.out.println("\nStarting batch analysis for " + files.length + " graph files...\n");
            for (File file : files) {
                System.out.println("Analyzing: " + file.getName());

                String json = Files.readString(Paths.get(file.getPath()));
                JSONObject obj = new JSONObject(json);
                int n = obj.getInt("n");
                JSONArray edges = obj.getJSONArray("edges");

                List<List<Integer>> adj = new ArrayList<>();
                for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
                for (int i = 0; i < edges.length(); i++) {
                    JSONObject e = edges.getJSONObject(i);
                    adj.get(e.getInt("u")).add(e.getInt("v"));
                }

                long start = System.nanoTime();
                TarjanSCC tarjan = new TarjanSCC(adj);
                List<List<Integer>> sccs = tarjan.getSCCs();
                long sccTime = System.nanoTime() - start;

                start = System.nanoTime();
                List<Integer> topo = TopologicalSort.sort(adj);
                long topoTime = System.nanoTime() - start;

                List<List<int[]>> weightedAdj = new ArrayList<>();
                for (int i = 0; i < n; i++) weightedAdj.add(new ArrayList<>());
                for (int i = 0; i < edges.length(); i++) {
                    JSONObject e = edges.getJSONObject(i);
                    int u = e.getInt("u");
                    int v = e.getInt("v");
                    int w = e.getInt("w");
                    weightedAdj.get(u).add(new int[]{v, w});
                }

                double spTime = 0, lpTime = 0;
                try {
                    start = System.nanoTime();
                    new DAGShortestPaths(weightedAdj, 0);
                    spTime = (System.nanoTime() - start) / 1e6;

                    start = System.nanoTime();
                    new DAGLongestPaths(weightedAdj, 0);
                    lpTime = (System.nanoTime() - start) / 1e6;
                } catch (Exception ignored) {}

                csv.write(String.format("%s,%d,%d,%d,%d,%.3f,%.3f\n",
                        file.getName(), n, edges.length(), sccs.size(),
                        topo != null ? topo.size() : 0, spTime, lpTime));

                System.out.printf("%-15s | n=%d | m=%d | SCCs=%d | SP=%.3f | LP=%.3f%n",
                        file.getName(), n, edges.length(), sccs.size(), spTime, lpTime);
            }
            System.out.println("\nAll results saved to results.csv");
        }
    }
}
