package graph.io;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GraphGenerator {

    private static final Random rand = new Random();

    public static void main(String[] args) throws IOException {
        generateAll();
    }

    public static void generateAll() throws IOException {
        generate("data/small_1.json", 6, 9, true);
        generate("data/small_2.json", 8, 10, false);
        generate("data/small_3.json", 10, 15, true);

        generate("data/medium_1.json", 12, 25, true);
        generate("data/medium_2.json", 15, 30, false);
        generate("data/medium_3.json", 18, 35, true);

        generate("data/large_1.json", 25, 60, true);
        generate("data/large_2.json", 30, 90, false);
        generate("data/large_3.json", 40, 120, true);

        System.out.println("9 graph datasets generated successfully in /data/");
    }

    public static void generate(String filename, int n, int edges, boolean allowCycles) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("directed", true);
        obj.put("n", n);
        obj.put("weight_model", "edge");

        JSONArray arr = new JSONArray();
        Set<String> used = new HashSet<>();

        int attempts = 0;
        while (arr.length() < edges && attempts < edges * 5) {
            int u = rand.nextInt(n);
            int v = rand.nextInt(n);
            if (u == v) { attempts++; continue; }
            if (!allowCycles && v <= u) { attempts++; continue; }

            String key = u + "_" + v;
            if (used.contains(key)) { attempts++; continue; }

            used.add(key);
            int w = rand.nextInt(9) + 1;
            arr.put(new JSONObject().put("u", u).put("v", v).put("w", w));
        }

        obj.put("edges", arr);
        obj.put("source", rand.nextInt(n));
        obj.put("description", String.format("Auto-generated graph (n=%d, m=%d, cycles=%b)", n, arr.length(), allowCycles));

        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(obj.toString(2));
        }
        System.out.println("Generated: " + filename + " (n=" + n + ", m=" + arr.length() + ", cycles=" + allowCycles + ")");
    }
}
