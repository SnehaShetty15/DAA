import java.util.Scanner;

public class SimpleSubwaySystemMatrix {
    final static int INF = 99999; // A large number representing no direct path

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Ask user for the number of stations
        System.out.print("Enter the number of stations: ");
        int n = sc.nextInt();
        sc.nextLine(); // Consume newline left-over

        // Step 2: Read station names from the user
        String[] stations = new String[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name of station " + (i + 1) + ": ");
            stations[i] = sc.nextLine();
        }

        //  Step 3: Create and input the travel time matrix
        int[][] time = new int[n][n];
        System.out.println("\nEnter travel times between stations (enter 99999 if no direct path):");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    time[i][j] = 0; // Travel time from a station to itself is 0
                } else {
                    System.out.print("From " + stations[i] + " to " + stations[j] + ": ");
                    time[i][j] = sc.nextInt(); // User inputs time or 99999 for no path
                }
            }
        }

        //  Step 4: Apply Floyd-Warshall Algorithm
        // This will update the time[][] matrix with the shortest travel time between all pairs of stations
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (time[i][k] + time[k][j] < time[i][j]) {
                        time[i][j] = time[i][k] + time[k][j]; // Update with shorter path
                    }
                }
            }
        }

        //  Step 5: Display the shortest travel time matrix
        System.out.println("\nShortest Travel Time Matrix (in minutes):");

        // Print header row
        System.out.printf("%15s", "");
        for (int i = 0; i < n; i++) {
            System.out.printf("%15s", stations[i]);
        }
        System.out.println();

        // Print matrix with row headers
        for (int i = 0; i < n; i++) {
            System.out.printf("%15s", stations[i]);
            for (int j = 0; j < n; j++) {
                if (time[i][j] == INF) {
                    System.out.printf("%15s", "INF"); // Show INF if no path
                } else {
                    System.out.printf("%15d", time[i][j]); // Show shortest travel time
                }
            }
            System.out.println();
        }

        sc.close(); 
    }
}
