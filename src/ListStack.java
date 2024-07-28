import java.util.*;

public class ListStack<T> {
    private java.util.LinkedList<T> list = new LinkedList<>();

    public ListStack() {

    }

    public ListStack(T firstElem) {
        push(firstElem);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    private void push(T elem) {
        list.add(elem);
    }

    public T pop() {
        if (isEmpty())
            throw new EmptyStackException();
        return list.removeLast();
    }

    public T peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return list.peekLast();
    }

    public int search(T elem) {
        return list.lastIndexOf(elem);
    }
}
