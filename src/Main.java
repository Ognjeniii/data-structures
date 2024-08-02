public class Main {
    public static void main(String[] args) {
        Integer[] arr = { 13, 4, 3, 2, 6, 1, 5 };
        BinaryHeap<Integer> bh = new BinaryHeap<Integer>(arr);
        bh.print();
        System.out.println();

        bh.poll();
        bh.print();
        System.out.println();
    }
}