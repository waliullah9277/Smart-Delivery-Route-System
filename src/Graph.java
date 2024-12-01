import java.util.*;

public class Graph {
    private Map<String, List<Edge>> adjList = new HashMap<>();
    private Map<String, Integer> distances = new HashMap<>();
    private Map<String, Boolean> visited = new HashMap<>();
    private Map<String, String> parents = new HashMap<>();

    public Graph(List<String> cities) {
        for (String city : cities) {
            adjList.put(city, new ArrayList<>());
            distances.put(city, Integer.MAX_VALUE);
            visited.put(city, false);
            parents.put(city, null);
        }
    }

    public void addEdge(String u, String v, int w) {
        if (!adjList.containsKey(u) || !adjList.containsKey(v)) {
            System.out.println("Error: One or both cities are not in the graph.");
            return;
        }
        adjList.get(u).add(new Edge(v, w));
    }

    public void dijkstra(String start) {
        if (!adjList.containsKey(start)) {
            System.out.println("Error: Starting city does not exist.");
            return;
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> a.weight - b.weight);
        distances.put(start, 0);
        pq.add(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            String currentNode = current.destination;

            if (visited.get(currentNode)) continue;
            visited.put(currentNode, true);

            for (Edge neighbor : adjList.get(currentNode)) {
                String neighborNode = neighbor.destination;
                int newDist = distances.get(currentNode) + neighbor.weight;

                if (newDist < distances.get(neighborNode)) {
                    distances.put(neighborNode, newDist);
                    pq.add(new Edge(neighborNode, newDist));
                    parents.put(neighborNode, currentNode);
                }
            }
        }
    }

    public void printPath(String end) {
        if (!visited.get(end)) {
            System.out.println("No path found.");
            return;
        }

        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = parents.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        System.out.println("Path: " + String.join(" -> ", path));
        System.out.println("Distance: " + distances.get(end) + " km");
    }

    public void printAllRoutes(String start, String end) {
        // if (!adjList.containsKey(start) || !adjList.containsKey(end)) {
        //     System.out.println("Error: One or both cities do not exist.");
        //     return;
        // }

        if (!adjList.containsKey(start)) {
            System.out.println("Error: start cities do not exist.");
            return;
        }
        else if(!adjList.containsKey(end)){
            System.out.println("Error: end cities do not exist.");
            return;
        }
        else{
            System.out.println("Error: One or both cities do not exist.");
        }


        System.out.println("All possible routes from " + start + " to " + end + ":");
        Set<String> visitedCities = new HashSet<>();
        List<String> currentPath = new ArrayList<>();
        currentPath.add(start);
        printAllRoutesUtil(start, end, visitedCities, currentPath, 0);
    }

    private void printAllRoutesUtil(String current, String end, Set<String> visitedCities, List<String> path, int totalDistance) {
        if (current.equals(end)) {
            System.out.print("Path: ");
            for (int i = 0; i < path.size() - 1; i++) {
                String from = path.get(i);
                String to = path.get(i + 1);
                System.out.print(path.get(i));
                if (i < path.size() - 2) System.out.print(" -> ");
                int distance = getEdgeWeight(from, to);
                totalDistance += distance;
            }
            System.out.print(" -> " + path.get(path.size() - 1));
            System.out.println(" | Total Distance: " + totalDistance + " km");
            return;
        }

        visitedCities.add(current);

        for (Edge neighbor : adjList.get(current)) {
            if (!visitedCities.contains(neighbor.destination)) {
                path.add(neighbor.destination);
                printAllRoutesUtil(neighbor.destination, end, visitedCities, path, totalDistance);
                path.remove(path.size() - 1);
            }
        }

        visitedCities.remove(current);
    }

    private int getEdgeWeight(String from, String to) {
        if (!adjList.containsKey(from)) return 0; // Safety check for missing cities
        for (Edge edge : adjList.get(from)) {
            if (edge.destination.equals(to)) {
                return edge.weight;
            }
        }
        return 0;
    }

    public List<Kruskal.Edge> getAllEdges() {
        List<Kruskal.Edge> edges = new ArrayList<>();
        for (String from : adjList.keySet()) {
            for (Edge edge : adjList.get(from)) {
                edges.add(new Kruskal.Edge(from, edge.destination, edge.weight));
            }
        }
        return edges;
    }

    public Map<String, Integer> getDistances() {
        return distances;
    }

    public List<String> getCities() {
        return new ArrayList<>(adjList.keySet());
    }
}
