import java.util.*;

class Edge {
    int source, destination, weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

class BellmanFord {
    int V, E;
    List<Edge> edges;
    int[] distance;

    public BellmanFord(int V, int E) {
        this.V = V;
        this.E = E;
        edges = new ArrayList<>();
        distance = new int[V];
    }

    void addEdge(int source, int destination, int weight) {
        edges.add(new Edge(source, destination, weight));
    }

    void bellmanFord(int source) {
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        for (int i = 1; i <= V - 1; i++) {
            for (Edge edge : edges) {
                if (distance[edge.source] != Integer.MAX_VALUE && distance[edge.source] + edge.weight < distance[edge.destination]) {
                    distance[edge.destination] = distance[edge.source] + edge.weight;
                }
            }
        }

        // Check for negative-weight cycles
        for (Edge edge : edges) {
            if (distance[edge.source] != Integer.MAX_VALUE && distance[edge.source] + edge.weight < distance[edge.destination]) {
                System.out.println("Negative-weight cycle detected!");
                return;
            }
        }

        // Print shortest distances from the source vertex
        for (int i = 0; i < V; i++) {
            System.out.println("Vertex " + i + ": Distance = " + distance[i]);
        }
    }

    public static void main(String[] args) {
        int V = 5; // Number of vertices
        int E = 8; // Number of edges
        BellmanFord graph = new BellmanFord(V, E);

        // Add edges to the graph
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 3, 3);
        graph.addEdge(2, 1, 6);
        graph.addEdge(3, 2, 2);
        graph.addEdge(4, 3, 2);
        graph.addEdge(4, 1, 1);

        int source = 0; // Source vertex

        graph.bellmanFord(source);
    }
}
