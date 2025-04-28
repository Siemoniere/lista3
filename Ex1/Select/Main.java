//select
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    static int swaps = 0;
    static int comparisons = 0;

    public static void main(String[] args) {
        int pos = Integer.parseInt(args[0]);
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        boolean displayTemporaryArrays = n <= 30;
        if (pos < 1 || pos > n) {
            System.out.println("Invalid position: " + pos);
            scanner.close();
            return;
        }
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < n; i++){
            a.add(scanner.nextInt());
        }
        ArrayList<Integer> aCopy = new ArrayList<>();
        for (int i = 0; i < n; i++){
            aCopy.add(a.get(i));
        }
        System.out.println("------------------");
        System.out.println(pos + ". position: " + randomizedSelect(a, 0, n - 1, pos, n, displayTemporaryArrays));
        printArray(aCopy, "i");
        printArray(a, "f");
        a.sort(null);
        printArray(a, "s");
        System.out.println("Compares: " + comparisons);
        System.out.println("Swaps: " + swaps);
        scanner.close();
    }

    public static int findMedianOfMedians(ArrayList<Integer> a, int p, int q, boolean displayTemporaryArrays) {
        if (q - p < 5) {
            ArrayList<Integer> temp = new ArrayList<>(a.subList(p, q + 1));
            temp.sort(null);
            return temp.get((temp.size() - 1) / 2);
        }
    
        ArrayList<Integer> medians = new ArrayList<>();
        for (int i = p; i <= q; i += 5) {
            int end = Math.min(i + 4, q);
            ArrayList<Integer> group = new ArrayList<>(a.subList(i, end + 1));
            group.sort(null);
            medians.add(group.get((group.size() - 1) / 2));
        }
        return randomizedSelect(medians, 0, medians.size() - 1, (medians.size() + 1) / 2, medians.size(), displayTemporaryArrays);
    }
    
    public static int findMedian(ArrayList<Integer> a) {
        if (a.size() == 0) {
            return -1;
        }
        return a.get(a.size() / 2);
    }
    public static int randomizedSelect(ArrayList<Integer> a, int p, int q, int i, int n, boolean displayTemporaryArrays) {
        if (displayTemporaryArrays) {
            printArray(a, "t");
        }
        if (p == q) {
            return a.get(p);
        }
        int pivotValue = findMedianOfMedians(a, p, q, displayTemporaryArrays);
        int pivotIndex = partitionAroundPivot(a, p, q, pivotValue);
        int k = pivotIndex - p + 1;

        if (i == k) {
            return a.get(pivotIndex);
        } else if (i < k) {
            return randomizedSelect(a, p, pivotIndex - 1, i, n, displayTemporaryArrays);
        } else {
            return randomizedSelect(a, pivotIndex + 1, q, i - k, n, displayTemporaryArrays);
        }
    }
    public static int partitionAroundPivot(ArrayList<Integer> a, int p, int q, int pivotValue) {
        int pivotIndex = -1;
        for (int i = p; i <= q; i++) {
            comparisons++;
            if (a.get(i) == pivotValue) {
                pivotIndex = i;
                break;
            }
        }
        if (pivotIndex == -1) {
            throw new RuntimeException("Pivot value not found");
        }
        swap(a, pivotIndex, q);
        int storeIndex = p;
        for (int i = p; i < q; i++) {
            comparisons++;
            if (a.get(i) < pivotValue) {
                swap(a, storeIndex, i);
                storeIndex++;
            }
        }
        swap(a, storeIndex, q);
        return storeIndex;
    }
    
    private static void swap(ArrayList<Integer> a, int i, int j) {
        swaps++;
        int temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }
    public static void printArray(ArrayList<Integer> a, String message) {
        if (message == "i") {
            System.out.print("Initial array: ");
        } else if (message == "f") {
            System.out.print("Final array: ");
        } else if (message == "s") {
            System.out.print("Sorted array: ");
        } else if (message == "t") {
            System.out.print("Temporary array: ");
        }
        for (int i = 0; i < a.size(); i++) {
            System.out.print(a.get(i) + " ");
        }
        System.out.println();
    }
}