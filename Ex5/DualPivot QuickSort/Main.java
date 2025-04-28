import java.util.ArrayList;
import java.util.Scanner;

public class Main{

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < n; i++){
            a.add(scanner.nextInt());
        }
        ArrayList<Integer> aCopy = new ArrayList<>(a);
        DualPivot qs = new DualPivot(n, a);
        ArrayList<Integer> old = new ArrayList<>();
        qs.saveOld(old);
        ArrayList<Integer> sorted = qs.sort(aCopy);
        qs.setSorted(sorted);
        if (n < 40){
            System.out.println("Oto nieposortowana tablica: ");
            qs.display(old);
            System.out.println("Posortowana tablica: ");
            qs.display(a); 
        }

        System.out.printf("Swaps: %d\n", qs.getSwap());
        System.out.printf("Compares: %d\n", qs.getCmp());

        if (qs.check()) {
            System.out.println("Poprawnie");
        } else {
            System.out.println("Error");
        }
        scanner.close();
    }
}