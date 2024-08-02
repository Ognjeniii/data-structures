import java.util.*;

public class BinaryHeap <T extends Comparable<T>> {
    private List<T> heap = null;

    public BinaryHeap(){

    }

    public BinaryHeap(int sz) {
        heap = new ArrayList<>(sz);
    }

    public BinaryHeap(T[] elems) {
        int heapSize = elems.length;
        heap = new ArrayList<>(heapSize);

        for(int i = 0; i < heapSize; i++)
            heap.add(elems[i]);

        for(int i = Math.max(0, (heapSize / 2) - 1); i >= 0; i--)
            sink(i);
    }

    // top down node sink, O(log(n))
    private void sink(int k) {
        int heapSize = size();
        while(true) { // k je roditelj
            int left = 2 * k + 1; // levo dete
            int right = 2 * k + 2; // desno dete
            int smallest = left; // pretpostavimo da je levo dete manje od desnog deteta

            // less metoda proverava da li je element na poziciji right manji ili jednak elemntu na poziciji left
            if(right < heapSize && less(right, left))
                smallest = right; // ako jeste, right je sada najmanji

            // ako je left veći od heapSoze ili ako je roditelj manji ili jednak najmanjem (od dvoje dece)
            if(left >= heapSize || less(k, smallest))
                break;

            swap(smallest, k); // menjamo mesto roditelja i deteta
            k = smallest; // dete postaje roditelj

            // objasnjeno na http://www.cs.umd.edu/~meesh/351/mount/lectures/lect14-heapsort-analysis-part.pdf
        }
    }

    private void swim(int k) {
        int parent = (k - 1) / 2;

        while(k > 0 && less(k, parent)) {
            swap(parent, k);
            k = parent;
        }
    }

    private void swap(int i, int j) {
        T elem_i = heap.get(i);
        T elem_j = heap.get(j);

        heap.set(i, elem_j);
        heap.set(j, elem_i);
    }

    // da li je element na poziciji i manji ili jednak elementu na poziciji j
    private boolean less(int i, int j) {
        T node1 = heap.get(i);
        T node2 = heap.get(j);
        return node1.compareTo(node2) <= 0;
    }

    public void print() {
        for(var i : heap)
            System.out.print(i + " ");
    }

    public int size() {
        return heap.size();
    }

    public void clear() {
        heap.clear();
    }

    public T peek() throws Exception {
        if(heap.isEmpty())
            throw new Exception("Heap je prazan.");
        return heap.get(0);
    }

    public T poll() {
        return removeAt(0);
    }

    // Brisanje elementa na određenom indexu - O(log(n))
    private T removeAt(int i) {
        if(heap.isEmpty())
            return null;

        int lastElemIndex = size() - 1; // index poslednjeg elementa - najdesniji
        T removed_data = heap.get(i);
        swap(i, lastElemIndex); // menjamo mesta prvog i poslednjeg elementa

        heap.remove(lastElemIndex); // brišemo poslednji element posle menjanja mesta

        if(i == lastElemIndex) // ako je preostao samo jedan element
            return removed_data;

        T elem = heap.get(i);

        sink(i);

        if (heap.get(i).equals(elem))
            swim(i);

        return removed_data;
    }

    // rekurzivno proverava da li je hip min hip
    // poziva se sa k = 0
    public boolean isMinHeap(int k) {
        int heapSize = size();
        if(k >= heapSize)
            return true;

        int left = 2 * k + 1;
        int right = 2 * k + 2;

        if (left < heapSize && !less(k, left))
            return false;

        if(right < heapSize && !less(k, right))
            return false;

        return isMinHeap(left) && isMinHeap(right);
    }
}
