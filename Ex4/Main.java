import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int cmp = 0;
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int x = Integer.parseInt(args[0]);
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < n; i++){
            a.add(scanner.nextInt());
        }
        for (int i = 0; i < n; i++){
            System.out.print(a.get(i) + " ");
        }
        System.out.println();
        System.out.println(binarySearch(a, x, 0, n - 1) ? "1" : "0");
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
        System.out.println("Comparisons: " + cmp);
        scanner.close();
    }
    public static boolean binarySearch(ArrayList<Integer> a, int x, int low, int high) {
        if (low > high) {
            return false;
        }
        int mid = (low + high) / 2;
        cmp++;
        if (a.get(mid) == x) {
            return true;
        }
        cmp++;
        if (a.get(mid) < x) {
            return binarySearch(a, x, mid + 1, high);
        } else {
            return binarySearch(a, x, low, mid - 1);
        }
    }
}
