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

        for(int i = Math.max(0, (heapSize / 2) - 1); i >= 0; i++)
            sink(i);
    }

    // top down node sink, O(log(n))
    private void sink(int k) {
        int heapSize = size();
        while(true) {
            int left = 2 * k + 1;
            int right = 2 * k + 2;
            int smallest = left; // assume left is the smallest node of two children

            if(right < heapSize && less(right, left))
                smallest = right;

            if(left >= heapSize || less(k, smallest))
                break;

            swap(smallest, k);
            k = smallest;
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

    private boolean less(int i, int j) {
        T node1 = heap.get(i);
        T node2 = heap.get(j);
        return node1.compareTo(node2) <= 0;
    }

    private int size() {
        return heap.size();
    }
}
