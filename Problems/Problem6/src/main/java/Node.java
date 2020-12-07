import java.util.concurrent.atomic.AtomicReference;

class Node<T> {
    private T data;
    private AtomicReference<Node<T>> next = new AtomicReference<>();

    Node() {
        this(null);
    }

    Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public AtomicReference<Node<T>> getNext() {
        return next;
    }

    public void setNext(AtomicReference<Node<T>> next) {
        this.next = next;
    }
}

