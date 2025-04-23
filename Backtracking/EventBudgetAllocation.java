import java.util.Scanner;

public class EventBudgetAllocation {

    // Arrays to hold item names, their costs, and the best selection
    static String[] items;
    static int[] costs;
    static int budget;
    static int maxItemCount = 0;  // Keeps track of the maximum number of items selected within the budget
    static boolean[] bestSelection;  // Stores the best selection of items

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the number of items from the user
        System.out.print("Enter number of event items: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume newline after the number input

        // Initialize arrays based on the number of items
        items = new String[n];
        costs = new int[n];
        bestSelection = new boolean[n];

        // Get the details of each item (name and cost)
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for item " + (i + 1) + ":");
            System.out.print("Item name: ");
            items[i] = scanner.nextLine();

            System.out.print("Cost: ");
            costs[i] = scanner.nextInt();
            scanner.nextLine(); // Consume newline after the cost input
        }

        // Get the budget from the user
        System.out.print("\nEnter total budget: ");
        budget = scanner.nextInt();

        // Temporary array to track which items are selected in each combination
        boolean[] selected = new boolean[n];

        // Start the backtracking process to find the best combination of items
        backtrack(0, 0, 0, selected);

        // Output the best selection of items
        System.out.println("\n Best Event Plan Within Budget (Uses Full Budget):");
        int totalCost = 0;  // Track the total cost of selected items
        for (int i = 0; i < n; i++) {
            if (bestSelection[i]) {
                // Output the selected items
                System.out.println("- " + items[i] + " (Cost: " + costs[i] + ")");
                totalCost += costs[i];  // Add the item's cost to the total
            }
        }

        // Output the total cost and number of selected items
        System.out.println("Total Cost: " + totalCost);
        System.out.println("Total Items Selected: " + maxItemCount);

        // Close the scanner to free up resources
        scanner.close();
    }

    
    // Backtracking function to explore all possible combinations of items
     //and find the one with the maximum number of items within the budget.

    static void backtrack(int index, int currentCost, int currentCount, boolean[] selected) {
        // If the current cost exceeds the budget, stop exploring this combination
        if (currentCost > budget) return;

        // If the current cost equals the budget and the number of selected items
        // is greater than the previous best, update the best selection
        if (currentCost == budget && currentCount > maxItemCount) {
            maxItemCount = currentCount;  // Update the maximum number 
            System.arraycopy(selected, 0, bestSelection, 0, selected.length);
            return; // No need to explore further, we've found an optimal solution
        }

        // If all items have been considered, stop recursion
        if (index >= items.length) return;

        // Include the current item in the selection
        selected[index] = true;
        // Recur with the next item, adding the current item's cost and count
        backtrack(index + 1, currentCost + costs[index], currentCount + 1, selected);

        // Exclude the current item from the selection
        selected[index] = false;
        // Recur with the next item, keeping the current cost and count unchanged
        backtrack(index + 1, currentCost, currentCount, selected);
    }
}
