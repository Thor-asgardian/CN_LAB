import java.util.Scanner;

class Graph {
    int v;
    int[][] vertices;

    public Graph(int v) {
        this.v = v;
        this.vertices = new int[v][3];
    }

    public void addEdge(int s, int d, int w) {
        this.vertices[s][0] = s;
        this.vertices[s][1] = d;
        this.vertices[s][2] = w;
    }

    public void printDistance(int[] dist) {
        System.out.println("Vertex\t\tDistance");
        for (int i = 0; i < v; i++) {
            System.out.println(i + "\t\t" + dist[i]);
        }
    }

    public void bellmanFord(int src) {
        int[] dist = new int[v];
        for (int i = 0; i < v; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[src] = 0;

        for (int k = 0; k < v - 1; k++) {
            for (int[] edge : vertices) {
                int s = edge[0];
                int d = edge[1];
                int w = edge[2];

                if (dist[s] != Integer.MAX_VALUE && dist[d] > dist[s] + w) {
                    dist[d] = dist[s] + w;
                }
            }
        }

        for (int[] edge : vertices) {
            int s = edge[0];
            int d = edge[1];
            int w = edge[2];

            if (dist[s] != Integer.MAX_VALUE && dist[d] > dist[s] + w) {
                System.out.println("Graph has a negative cycle");
                return;
            }
        }

        printDistance(dist);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter no. of vertices: ");
        int nv = scanner.nextInt();

        System.out.print("Enter no. of edges: ");
        int ne = scanner.nextInt();

        Graph g = new Graph(nv);

        System.out.println("Enter source, destination, and weights of edges");
        for (int i = 0; i < ne; i++) {
            int s = scanner.nextInt();
            int d = scanner.nextInt();
            int w = scanner.nextInt();
            g.addEdge(s, d, w);
        }

        System.out.print("Enter source node: ");
        int src = scanner.nextInt();

        g.bellmanFord(src);
    }
}
/* output sample:
Enter no. of vertices: 5
Enter no. of edges: 8
Enter source, destination, and weights of edges
0 1 6
0 2 7
1 3 5
1 4 -4
1 2 8
2 3 -3
2 4 9
3 4 7
Enter source node: 0
Vertex		Distance
0		0
1		1
2		7
3		4
4		-2
*/
