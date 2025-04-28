//randomized select
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main{
    static int swaps = 0;
    static int comparisons = 0;
    public static void main(String[] args) {
        int pos = Integer.parseInt(args[0]);
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
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
        System.out.println(pos + ". position: " + randomizedSelect(a, 0, n - 1, pos, n));
        printArray(aCopy, "i");
        printArray(a, "f");
        a.sort(null);
        printArray(a, "s");
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Swaps: " + swaps);
        scanner.close();
    }
    public static int randomizedSelect(ArrayList<Integer> a, int p, int q, int i, int n) {
        if (n <= 30) {
            printArray(a, "t");
        }
        if (p == q) {
            return a.get(p);
        }
        int r = randPartition(a, p, q);
        int k = r - p + 1;
        if (i == k) {
            return a.get(r);
        } else if (i < k) {
            return randomizedSelect(a, p, r - 1, i, n);
        } else {
            return randomizedSelect(a, r + 1, q, i - k, n);
        }
    }
    public static int randPartition(ArrayList<Integer> a, int p, int q) {
        Random random = new Random();
        int i = random.nextInt(q - p + 1) + p;
        swap(a, i, q);
        return partition(a, p, q);
    }
    private static void swap(ArrayList<Integer> a, int i, int j) {
        swaps++;
        int temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }
    private static int partition(ArrayList<Integer> a, int p, int q) {
        int pivot = a.get(q);
        int i = p - 1;
        for (int j = p; j < q; j++) {
            comparisons++;
            if (a.get(j) <= pivot) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, q);
        return i + 1;
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