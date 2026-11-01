import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RidTimeHop {

    static void insertionSort(int[] arr, int passes) {
        for (int i = 1; i <= passes; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] < key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("branches.txt"));

        int n = sc.nextInt();
        int[] branches = new int[n];
        for (int i = 0; i < n; i++) {
            branches[i] = sc.nextInt();
        }

        int m = sc.nextInt();
        int[] targets = new int[m];
        for (int i = 0; i < m; i++) {
            targets[i] = sc.nextInt();
        }
        sc.close();

        insertionSort(branches, 3);

        for (int i = 0; i < n; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(branches[i]);
        }
        System.out.println();

        for (int i = 0; i < m; i++) {
            int index = linearSearch(branches, targets[i]);
            if (index != -1) {
                System.out.println(targets[i] + ": FOUND at index " + index);
            } else {
                System.out.println(targets[i] + ": NOT FOUND");
            }
        }
    }
}
