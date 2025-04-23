import java.util.Random;

public class SelectionSortTimeAnalysis {
    public static void main(String[] args) {
        // Array sizes to be used for testing
        int[] nValues = {10000, 20000, 30000, 40000, 50000, 60000};
        
        // Arrays to store the time taken for search and sort for each size
        long[] searchTimes = new long[nValues.length];
        long[] sortTimes = new long[nValues.length];
        
        // Random object to generate random numbers
        Random random = new Random();

        // Loop through each array size
        for (int i = 0; i < nValues.length; i++) {
            int n = nValues[i];

            // Generate a random array of size n
            int[] arr = generateRandomElements(n, random);
            
            // Choose a random key from the array to search for
            int key = arr[random.nextInt(n)];

            // Measure time taken for sequential search in milliseconds
            long startTime = System.currentTimeMillis();
            sequentialSearch(arr, key);
            long endTime = System.currentTimeMillis();
            searchTimes[i] = endTime - startTime;

            // Generate another random array of the same size for sorting
            arr = generateRandomElements(n, random);

            // Measure time taken for selection sort in milliseconds
            startTime = System.currentTimeMillis();
            selectionSort(arr);
            endTime = System.currentTimeMillis();
            sortTimes[i] = endTime - startTime;
        }

        // Print the table header
        System.out.println("n\tSequential Search Time (ms)\tSelection Sort Time (ms)");

        // Print the time results for each array size
        for (int i = 0; i < nValues.length; i++) {
            System.out.println(nValues[i] + "\t\t" + searchTimes[i] + "\t\t\t" + sortTimes[i]);
        }
    }

     // Generates an array of random integers between 1 and 100000.
     
    public static int[] generateRandomElements(int n, Random random) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(100000) + 1;
        }
        return arr;
    }

    // Performs a sequential search for a given key in the array.
    
    public static int sequentialSearch(int[] arr, int key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key) {
                return i;
            }
        }
        return -1;
    }

    // Sorts the array using the selection sort algorithm.
     
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        // Outer loop: move the boundary of the unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            // Find the minimum element in the unsorted portion
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            // Swap the found minimum element with the first unsorted element
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }
}
