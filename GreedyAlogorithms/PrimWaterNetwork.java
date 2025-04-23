import java.util.*;

// Class representing the Water Network using an adjacency matrix
class WaterNetwork {
    private int vertices;                    // Number of vertices in the graph
    private int[][] graph;                   // Adjacency matrix to store pipeline costs
    private boolean[] mstSet;                // Boolean array to mark included vertices in MST
    private int[] parent;                    // Stores parent of each vertex in MST
    private int[] key;                       // Used to pick minimum weight edge in cut
    private Map<String, Integer> vertexMap;  // Maps vertex names to indices
    private String[] vertexNames;            // Stores names of vertices for output

    // Constructor to initialize the network
    public WaterNetwork(int vertices) {
        this.vertices = vertices;
        this.graph = new int[vertices][vertices];
        this.mstSet = new boolean[vertices];
        this.parent = new int[vertices];
        this.key = new int[vertices];
        this.vertexMap = new HashMap<>();
        this.vertexNames = new String[vertices];

        // Initialize key values to "infinity"
        Arrays.fill(key, Integer.MAX_VALUE);
    }

    // Add a vertex with a name and assign it an index
    public void addVertex(String name, int index) {
        vertexMap.put(name, index);
        vertexNames[index] = name;
    }

    // Add an undirected edge (pipeline) between two vertices with a given cost
    public void addPipeline(String u, String v, int cost) {
        int i = vertexMap.get(u);
        int j = vertexMap.get(v);
        graph[i][j] = cost;
        graph[j][i] = cost;
    }

    // Utility method to find the vertex with the minimum key value
    // from the set of vertices not yet included in MST
    private int minKey() {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int v = 0; v < vertices; v++) {
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    // Function to construct and print the MST using Prim's algorithm
    public void primMST() {
        key[0] = 0;           // Start from the first vertex
        parent[0] = -1;       // First node is the root of MST
        int totalCost = 0;

        // The MST will have (vertices - 1) edges
        for (int count = 0; count < vertices - 1; count++) {
            int u = minKey();     // Pick the minimum key vertex not yet included
            mstSet[u] = true;     // Add the picked vertex to the MST Set

            // Update the key and parent index of the adjacent vertices
            for (int v = 0; v < vertices; v++) {
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        // Print the MST and calculate total cost
        totalCost = printMST();
        System.out.println("Total Cost of MST: " + totalCost);
    }

    // Helper function to print the MST and calculate its total cost
    private int printMST() {
        int totalCost = 0;
        System.out.println("Edge \tWeight");
        for (int i = 1; i < vertices; i++) {
            System.out.println(vertexNames[parent[i]] + " - " + vertexNames[i] + "\t" + graph[i][parent[i]]);
            totalCost += graph[i][parent[i]];
        }
        return totalCost;
    }
}

// Main class that interacts with the user
public class PrimWaterNetwork {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read number of vertices from user
        System.out.print("Enter number of vertices: ");
        int vertices = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        WaterNetwork network = new WaterNetwork(vertices);

        // Input names of the vertices
        System.out.println("Enter vertex names: ");
        for (int i = 0; i < vertices; i++) {
            String name = scanner.nextLine();
            network.addVertex(name, i);
        }

        // Read number of edges (pipelines)
        System.out.print("Enter number of edges: ");
        int edges = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        // Input edge data in the form of vertex1 vertex2 cost
        System.out.println("Enter edges in the format: vertex1 vertex2 cost");
        for (int i = 0; i < edges; i++) {
            String u = scanner.next();
            String v = scanner.next();
            int cost = scanner.nextInt();
            network.addPipeline(u, v, cost);
        }

        // Close scanner to avoid memory leak
        scanner.close();

        // Generate and print the Minimum Spanning Tree
        network.primMST();
    }
}
