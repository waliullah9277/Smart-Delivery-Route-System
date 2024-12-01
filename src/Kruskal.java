import java.util.*;

public class Kruskal {
    public static class Edge {
        String src, dest;
        int weight;

        public Edge(String src, String dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    public static void kruskal(int vertices, List<Edge> edges) {
        edges.sort(Comparator.comparingInt(e -> e.weight));

        Map<String, String> parent = new HashMap<>();
        for (Edge edge : edges) {
            parent.put(edge.src, edge.src);
            parent.put(edge.dest, edge.dest);
        }

        List<Edge> mst = new ArrayList<>();
        for (Edge edge : edges) {
            String root1 = find(edge.src, parent);
            String root2 = find(edge.dest, parent);

            if (!root1.equals(root2)) {
                mst.add(edge);
                parent.put(root1, root2);
            }
        }

        System.out.println("Minimum Spanning Tree:");
        for (Edge edge : mst) {
            System.out.println(edge.src + " - " + edge.dest + " : " + edge.weight + " km");
        }
    }

    private static String find(String node, Map<String, String> parent) {
        if (!parent.get(node).equals(node)) {
            parent.put(node, find(parent.get(node), parent));
        }
        return parent.get(node);
    }
}
