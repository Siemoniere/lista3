import java.util.ArrayList;

public class QuickSort {

    private int n;
    private ArrayList<Integer> a;
    private int swap = 0, cmp = 0;

    public QuickSort(int n, ArrayList<Integer> a){
        this.a = a;
        this.n = n;
    }

    public ArrayList<Integer> sort(ArrayList<Integer> a){
        if (a.size() <= 1) return new ArrayList<>(a);

        int pivot = randomizedSelect(a, 0, a.size() - 1, (a.size() + 1) / 2, n, false);
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        if (n < 40){
            System.out.print("Podział: ");
            for (int val : a) {
                System.out.printf("%d ", val);
            }
            System.out.printf(" | Pivot: %d\n", pivot);
        }
        boolean pivotUsed = false;
        for (int i = 0; i < a.size(); i++) {
            cmp++;
            if (a.get(i) == pivot && !pivotUsed) {
                pivotUsed = true;
                continue;
            }
            if (a.get(i) < pivot) {
                left.add(a.get(i));
                swap++;
            } else {
                right.add(a.get(i));
            }
        }

        ArrayList<Integer> sorted = new ArrayList<>();
        sorted.addAll(sort(left));
        sorted.add(pivot);
        sorted.addAll(sort(right));
        if (n < 40){
            System.out.print("Scalanie: ");
            for (int val : sorted){
                System.out.printf("%d ", val);
            }
            System.out.println();
        }
        return sorted;
    }

    public void display(ArrayList<Integer> a) {
        for (int val : a) {
            System.out.printf("%d ", val);
        }
        System.out.println();
    }
    
    public void saveOld(ArrayList<Integer> old){
        old.clear();
        old.addAll(this.a);
    }

    public int getSwap(){
        return swap;
    }
    public int getCmp(){
        return cmp;
    }
    public void setSorted(ArrayList<Integer> sorted){
        this.a.clear();
        this.a.addAll(sorted);
    }
    public boolean check(){
        for (int i = 1; i < a.size(); i++){
            if(a.get(i - 1) > a.get(i)){
                return false;
            }
        } return true;
    }
    public  int findMedianOfMedians(ArrayList<Integer> a, int p, int q, boolean displayTemporaryArrays) {
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
    
    public  int findMedian(ArrayList<Integer> a) {
        if (a.size() == 0) {
            return -1;
        }
        return a.get(a.size() / 2);
    }
    public  int randomizedSelect(ArrayList<Integer> a, int p, int q, int i, int n, boolean displayTemporaryArrays) {
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
    public  int partitionAroundPivot(ArrayList<Integer> a, int p, int q, int pivotValue) {
        int pivotIndex = -1;
        for (int i = p; i <= q; i++) {
            cmp++;
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
            cmp++;
            if (a.get(i) < pivotValue) {
                swap(a, storeIndex, i);
                storeIndex++;
            }
        }
        swap(a, storeIndex, q);
        return storeIndex;
    }
    
    private  void swap(ArrayList<Integer> a, int i, int j) {
        swap++;
        int temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }
    public  void printArray(ArrayList<Integer> a, String message) {
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