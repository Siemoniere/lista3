import java.util.ArrayList;

public class DualPivot {

    private int n;
    private ArrayList<Integer> a;
    private int swap = 0, cmp = 0;

    public DualPivot(int n, ArrayList<Integer> a){
        this.a = a;
        this.n = n;
    }

    public ArrayList<Integer> sort(ArrayList<Integer> a) {
        if (a.size() <= 1) return new ArrayList<>(a);
    
        if (a.size() == 2) {
            ArrayList<Integer> res = new ArrayList<>();
            if (a.get(0) <= a.get(1)) {
                res.add(a.get(0));
                res.add(a.get(1));
            } else {
                res.add(a.get(1));
                res.add(a.get(0));
                swap++;
                cmp++;
            }
            return res;
        }
    
        int p = randomizedSelect(a, 0, a.size() - 1, (a.size() + 1) / 3, n, false);
        int q = randomizedSelect(a, 0, a.size() - 1, (2 * a.size() + 1) / 3, n, false);
    
        if (p > q) {
            int temp = p;
            p = q;
            q = temp;
        }
    
        if (p == q) {
            ArrayList<Integer> res = new ArrayList<>(a);
            res.sort(null);  // lub insertionSort
            return res;
        }
    
        ArrayList<Integer> s = new ArrayList<>();
        ArrayList<Integer> l = new ArrayList<>();
        ArrayList<Integer> m = new ArrayList<>();
    
        for (int i = 0; i < a.size(); i++) {
            int value = a.get(i);
            if (value < p) {
                s.add(value);
                swap++;
            } else if (value > q) {
                l.add(value);
                swap++;
            } else {
                m.add(value);
                swap++;
            }
        }
    
        if (s.size() == a.size() || m.size() == a.size() || l.size() == a.size()) {
            ArrayList<Integer> res = new ArrayList<>(a);
            insertionSort(res, 0, res.size() - 1);  // sortuj in-place
            return res;
        }
    
        ArrayList<Integer> sorted = new ArrayList<>();
        sorted.addAll(sort(s));
        sorted.addAll(sort(m));
        sorted.addAll(sort(l));
    
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
    public int findMedianOfMedians(ArrayList<Integer> a, int p, int q, boolean displayTemporaryArrays) {
        if (q - p < 5) {
            insertionSort(a, p, q);  // sortuj in-place
            return a.get(p + (q - p) / 2);
        }
    
        int numMedians = 0;
        for (int i = p; i <= q; i += 5) {
            int end = Math.min(i + 4, q);
            insertionSort(a, i, end);
            int medianIndex = i + (end - i) / 2;
            swap(a, p + numMedians, medianIndex);
            numMedians++;
        }
        return randomizedSelect(a, p, p + numMedians - 1, (numMedians + 1) / 2, numMedians, displayTemporaryArrays);
    }
    
    
    public int findMedian(ArrayList<Integer> a) {
        if (a.size() == 0) {
            return -1;
        }
        return a.get(a.size() / 2);
    }
    public int randomizedSelect(ArrayList<Integer> a, int p, int q, int i, int n, boolean displayTemporaryArrays) {
        if (displayTemporaryArrays) {
            printArray(a, "t");
        }
        if (p == q) {
            return a.get(p);
        }
        if (q - p + 1 <= 5) {
            insertionSort(a, p, q); // sortuj fragment in-place
            return a.get(p + i - 1);
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
    
    public int partitionAroundPivot(ArrayList<Integer> a, int p, int q, int pivotValue) {
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
    
    private void swap(ArrayList<Integer> a, int i, int j) {
        swap++;
        int temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }
    public void printArray(ArrayList<Integer> a, String message) {
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
    public void insertionSort(ArrayList<Integer> a, int p, int q) {
        for (int i = p + 1; i <= q; i++) {
            int key = a.get(i);
            int j = i - 1;
            while (j >= p && a.get(j) > key) {
                cmp++;
                a.set(j + 1, a.get(j));
                swap++;
                j--;
            }
            if (j >= p) cmp++; // ostatnie porównanie, które powoduje przerwanie
            a.set(j + 1, key);
            swap++;
        }
    }
    
}