import java.util.*;

// Union-Find (Disjoint Set Union - DSU) data structure for cycle detection
class UnionFind {
    int[] parent;

    // Initialize each node as its own parent
    UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    // Find the root parent of a node with path compression
    int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    // Union two sets (connect their roots)
    void union(int x, int y) {
        int rootX = find(x), rootY = find(y);
        if (rootX != rootY) parent[rootY] = rootX;
    }
}

// Edge class to represent a road between two towns
class Edge {
    int u, v, weight;

    Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }
}

// Main class for building the road network using Kruskal's algorithm
public class RoadNetwork {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of towns
        System.out.print("Enter number of towns: ");
        int n = scanner.nextInt(); 
        scanner.nextLine(); // Consume the newline

        // Map to store town names and assign them indices
        Map<String, Integer> townMap = new HashMap<>();

        System.out.println("Enter towns:");
        for (int i = 0; i < n; i++) {
            String town = scanner.nextLine();
            townMap.put(town, i);
        }

        // Input number of roads (edges)
        System.out.print("Enter number of roads: ");
        int m = scanner.nextInt(); 
        scanner.nextLine();

        // List to store all edges (roads)
        List<Edge> edges = new ArrayList<>();

        System.out.println("Enter roads (town1 town2 weight):");
        for (int i = 0; i < m; i++) {
            String[] input = scanner.nextLine().split(" ");
            String town1 = input[0];
            String town2 = input[1];
            int weight = Integer.parseInt(input[2]);

            // Add an edge to the list using town indices
            edges.add(new Edge(townMap.get(town1), townMap.get(town2), weight));
        }

        // Sort edges by weight (ascending) for Kruskal's algorithm
        edges.sort(Comparator.comparingInt(e -> e.weight));

        // Initialize Union-Find structure
        UnionFind uf = new UnionFind(n);
        int totalWeight = 0;
        List<Edge> mstEdges = new ArrayList<>(); // Edges in MST

        // Process each edge in order of increasing weight
        for (Edge edge : edges) {
            int u = edge.u, v = edge.v;

            // If adding this edge doesn't form a cycle
            if (uf.find(u) != uf.find(v)) {
                uf.union(u, v); // Connect the sets
                mstEdges.add(edge); // Add edge to MST
                totalWeight += edge.weight; // Accumulate total weight
            }
        }

        // Print the edges included in the MST
        System.out.println("Edges in MST:");
        for (Edge e : mstEdges) {
            String town1 = getKey(townMap, e.u);
            String town2 = getKey(townMap, e.v);
            System.out.println(town1 + " - " + town2 + ": " + e.weight);
        }

        // Print total cost (total length of roads in MST)
        System.out.println("Total road length: " + totalWeight);

        scanner.close(); // Close the scanner to avoid resource leak
    }

    /**
     * Helper method to get the town name from its index.
     * (Reverse lookup in the townMap)
     */
    private static String getKey(Map<String, Integer> map, int value) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == value) return entry.getKey();
        }
        return null;
    }
}
