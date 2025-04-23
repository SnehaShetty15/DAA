import java.util.*;

// Class to represent a road (edge) with destination city (to) and travel time
class Road {
    int to, time;

    Road(int to, int time) {
        this.to = to;
        this.time = time;
    }
}

public class OptimalRoadRoutes {

    // Dijkstra's algorithm to find shortest paths from a source city to all others
    private static int[] dijkstra(List<List<Road>> graph, int source) {
        int n = graph.size(); // number of cities
        int[] dist = new int[n]; // distance array
        Arrays.fill(dist, Integer.MAX_VALUE); // initially set all distances to infinity
        dist[source] = 0; // distance from source to itself is 0

        // Priority queue to select the city with the minimum distance at each step
        PriorityQueue<Road> pq = new PriorityQueue<>(Comparator.comparingInt(r -> r.time));
        pq.add(new Road(source, 0)); // start with source city

        // Main loop of Dijkstra's algorithm
        while (!pq.isEmpty()) {
            Road current = pq.poll(); // get city with shortest distance
            int u = current.to;

            // Traverse all neighboring cities (adjacent roads)
            for (Road neighbor : graph.get(u)) {
                int v = neighbor.to;
                int weight = neighbor.time;

                // Relaxation step: update distance if shorter path is found
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.add(new Road(v, dist[v])); // push updated distance to the queue
                }
            }
        }

        return dist; // return array of shortest distances
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: number of cities
        System.out.print("Enter number of cities: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline

        Map<String, Integer> cityMap = new HashMap<>(); // maps city name to index
        String[] cityNames = new String[n]; // array to retrieve names from index

        // Input: city names
        System.out.println("Enter city names:");
        for (int i = 0; i < n; i++) {
            String city = scanner.nextLine();
            cityMap.put(city, i); // assign each city a unique index
            cityNames[i] = city;
        }

        // Initialize graph as an adjacency list
        List<List<Road>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Input: number of roads
        System.out.print("Enter number of roads: ");
        int m = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline

        // Input: road data (city1, city2, travel time)
        System.out.println("Enter roads in format: city1 city2 travelTime");
        for (int i = 0; i < m; i++) {
            String[] parts = scanner.nextLine().split(" ");
            String city1 = parts[0];
            String city2 = parts[1];
            int travelTime = Integer.parseInt(parts[2]);

            int u = cityMap.get(city1); // get index of city1
            int v = cityMap.get(city2); // get index of city2

            // Since the roads are bidirectional, add edge in both directions
            graph.get(u).add(new Road(v, travelTime));
            graph.get(v).add(new Road(u, travelTime));
        }

        // Input: source city from which to calculate shortest paths
        System.out.print("Enter source city: ");
        String sourceCity = scanner.nextLine();
        int sourceIndex = cityMap.get(sourceCity); // get index of source city

        // Compute shortest distances from source city
        int[] distances = dijkstra(graph, sourceIndex);

        // Output: shortest travel time from source city to all other cities
        System.out.println("\nShortest travel times from " + sourceCity + ":");
        for (int i = 0; i < n; i++) {
            if (i == sourceIndex) continue; // skip showing distance from source to itself
            String timeDisplay = (distances[i] == Integer.MAX_VALUE) ? "Unreachable" : distances[i] + " hours";
            System.out.println(cityNames[i] + " : " + timeDisplay);
        }

        scanner.close();
    }
}
