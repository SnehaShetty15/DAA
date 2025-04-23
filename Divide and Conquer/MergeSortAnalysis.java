import java.util.*;

public class MergeSortAnalysis {
    
    //Performs merge sort on the array between indices left and right.
     
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            // Find the middle point
            int mid = left + (right - left) / 2;

            // Recursively sort first and second halves
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }

    /**
     * Merges two subarrays of arr[].
     * First subarray is arr[left..mid]
     * Second subarray is arr[mid+1..right]
     * arr- The original array with two sorted halves.
     * left- The starting index of the first subarray.
     * mid- The ending index of the first subarray.
     * right- The ending index of the second subarray.
     */
    private static void merge(int[] arr, int left, int mid, int right) {
        // Sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temporary arrays
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // Copy data to temporary arrays
        System.arraycopy(arr, left, leftArray, 0, n1);
        System.arraycopy(arr, mid + 1, rightArray, 0, n2);

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
        int k = left; // Initial index of merged subarray

        // Merge the temporary arrays back into the original array
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k++] = leftArray[i++];
            } else {
                arr[k++] = rightArray[j++];
            }
        }

        // Copy any remaining elements of leftArray[]
        while (i < n1) {
            arr[k++] = leftArray[i++];
        }

        // Copy any remaining elements of rightArray[]
        while (j < n2) {
            arr[k++] = rightArray[j++];
        }
    }

    //Generates an array filled with random integers between 0 and 9999.
     
    private static int[] generateRandomArray(int n) {
        int[] arr = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(10000);  // Random numbers from 0 to 9999
        }
        return arr;
    }

    public static void main(String[] args) {
        // Array of sizes to analyze merge sort performance
        int[] sizes = {100, 500, 1000, 5000, 10000, 20000};

        // Loop through each size, sort, and time the sort
        for (int n : sizes) {
            int[] arr = generateRandomArray(n);  // Create a random array of size n

            // Record start time in nanoseconds
            long startTime = System.nanoTime();
            mergeSort(arr, 0, arr.length - 1);  // Sort the array using merge sort
            long endTime = System.nanoTime();  // Record end time

            // Calculate total time taken in nanoseconds
            long timeTaken = endTime - startTime;

            // Output the result
            System.out.println("Sorted " + n + " elements in " + timeTaken + " ns");
        }
    }
}
