package lw04.unguided;
 
import java.util.Scanner;
 
public class SortBotWarehouse {
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(SortBotWarehouse.class.getResourceAsStream("parcels.txt"));
 
        int n = sc.nextInt();
        int[] shelf = new int[n];
        for (int i = 0; i < n; i++) {
            shelf[i] = sc.nextInt();
        }
 
        int m = sc.nextInt();
        int[] targets = new int[m];
        for (int i = 0; i < m; i++) {
            targets[i] = sc.nextInt();
        }
        sc.close();
 
        int[] afterTwoPasses = new int[n];
 
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (shelf[j] < shelf[j + 1]) {
                    int temp = shelf[j];
                    shelf[j] = shelf[j + 1];
                    shelf[j + 1] = temp;
                }
            }
            if (i == 1) {
                for (int k = 0; k < n; k++) {
                    afterTwoPasses[k] = shelf[k];
                }
            }
        }
 
        System.out.println("==== After 2 Passes ====");
        printArray(afterTwoPasses);
        System.out.println();
        System.out.println("==== Fully Sorted ====");
        printArray(shelf);
        System.out.println();
        System.out.println("==== Search Results ====");
 
        for (int t = 0; t < m; t++) {
            int target = targets[t];
            int low = 0;
            int high = n - 1;
            int steps = 0;
            int index = -1;
 
            while (low <= high) {
                int mid = (low + high) / 2;
                steps++;
                if (shelf[mid] == target) {
                    index = mid;
                    break;
                } else if (shelf[mid] > target) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
 
            if (index != -1) {
                System.out.println(target + ": FOUND at index " + index + " (steps: " + steps + ")");
            } else {
                System.out.println(target + ": NOT FOUND (steps: " + steps + ")");
            }
        }
    }
 
    static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(arr[i]);
        }
        System.out.println();
    }
}
 