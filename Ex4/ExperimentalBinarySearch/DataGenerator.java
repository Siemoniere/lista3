import java.util.Random;
import java.util.Arrays;

public class DataGenerator{
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java DataGenerator [l|r|m] n");
            return;
        }
        String mode = args[0];
        Random rand = new Random();
        int n = Integer.parseInt(args[1]);
        int[] a = new int[n];

        for (int i = 0; i < n; i++){
            a[i] = rand.nextInt(2 * n);
        }

        switch (mode){
            case "r":
                Arrays.sort(a);
                break;
            case "m":
                Arrays.sort(a);
                for (int i = 0; i < n / 2; i++){
                    int temp = a[i];
                    a[i] = a[n - i - 1];
                    a[n - i - 1] = temp;
                }
                break;
            case "l":
                 break;
            default:
                System.out.println("Wrong mode: " + mode);
                return;
        }
        System.out.println(n);
        for (int i = 0; i < n; i++) {
            System.out.println(a[i]);
        }
        
    }
}