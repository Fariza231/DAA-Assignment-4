package graph.io;
import org.json.*;
import java.nio.file.*;
import graph.core.Graph;

public class GraphLoader {
    public static Graph loadFromJson(String path) throws Exception {
        String json = Files.readString(Paths.get(path));
        JSONObject obj = new JSONObject(json);
        int n = obj.getInt("n");
        boolean directed = obj.getBoolean("directed");
        Graph g = new Graph(n, directed);
        JSONArray edges = obj.getJSONArray("edges");
        for (int i = 0; i < edges.length(); i++) {
            JSONObject e = edges.getJSONObject(i);
            g.addEdge(e.getInt("u"), e.getInt("v"), e.getInt("w"));
        }
        return g;
    }
}
