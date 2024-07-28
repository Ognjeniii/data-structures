import java.util.LinkedList;

public class ListQueue<T> {
    private java.util.LinkedList<T> list = new LinkedList<>();
    public ListQueue() {}
    public ListQueue(T firstElem) {
        enqueue(firstElem);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    // vraća element sa početka queue-a
    public T peek() {
        if(isEmpty())
            throw new RuntimeException("Queue is empty!");
        return list.peekFirst();
    }

    // vraća i briše element sa početka queue-a (dequeue)
    public T poll() {
        if(isEmpty())
            throw new RuntimeException("Queue is empty!");
        return list.removeFirst();
    }

    private void enqueue(T elem) {
        list.addLast(elem);
    }
}
