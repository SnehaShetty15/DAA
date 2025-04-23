import java.util.Random;

public class QuickSort {
    
    // The main quicksort function
    public static void quicksort(int[] arr, int low, int high) {
        if (low < high) {
            // Partitioning index
            int pi = partition(arr, low, high);
            
            // Recursively sort the left and right sub-arrays
            quicksort(arr, low, pi - 1);
            quicksort(arr, pi + 1, high);
        }
    }

    // The partition function that places the pivot element at its correct position
    // and places smaller elements on the left and larger elements on the right
    public static int partition(int[] arr, int low, int high) {
        // Choose the pivot element (here, choosing the last element)
        int pivot = arr[high];
        int i = low - 1;  // Index of smaller element

        // Loop through the array and compare each element to the pivot
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;  // Increment the index of smaller element
                // Swap arr[i] and arr[j]
                swap(arr, i, j);
            }
        }

        // Swap the pivot element with the element at i+1 so that the pivot is in its correct place
        swap(arr, i + 1, high);
        
        return i + 1;  // Return the partition index
    }

    // Utility function to swap two elements in the array
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Function to generate a random array of given size
    public static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        
        // Fill the array with random numbers between 0 and 9999
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(10000);  // Random number between 0 and 9999
        }
        
        return arr;
    }

    // Main method to run the sorting and time the process
    public static void main(String[] args) {
        // List of array sizes to test
        int[] sizes = {1000,2000,3000,4000,5000,6000}; // Change these sizes as needed
        
        for (int size : sizes) {
            int[] arr = generateRandomArray(size);
            
            // Start timing
            long startTime = System.nanoTime();
            quicksort(arr, 0, arr.length - 1);  // Perform quicksort
            long endTime = System.nanoTime();
            
            long duration = endTime - startTime; // Time taken in nanoseconds
            System.out.println("Sorted " + size + " elements in : " + duration + " ns");
        }
    }
}
