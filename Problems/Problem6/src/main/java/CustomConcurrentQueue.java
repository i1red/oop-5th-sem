import java.util.concurrent.atomic.AtomicReference;

public class CustomConcurrentQueue<T> {
    private final AtomicReference<Node<T>> head;
    private final AtomicReference<Node<T>> tail;

    public CustomConcurrentQueue() {
        var dummy = new Node<T>();
        this.head = new AtomicReference<>(dummy);
        this.tail = new AtomicReference<>(dummy);
    }

    public void push(T data) {
        var newNode = new Node<T>(data);

        while (true) {
            Node<T> currentTailNode = this.tail.get();
            Node<T> currentNextNode = currentTailNode.getNext().get();

            if (currentTailNode == this.tail.get()) {
                if (currentNextNode != null) {
                    this.tail.compareAndSet(currentTailNode, currentNextNode);
                } else if (currentTailNode.getNext().compareAndSet(null, newNode)){
                    this.tail.compareAndSet(currentTailNode, newNode);
                    break;
                }
            }
        }
    }

    public T pop() {
        while (true) {
            Node<T> currentHeadNode = this.head.get();
            Node<T> currentTailNode = this.tail.get();
            Node<T> currentNextNode = currentHeadNode.getNext().get();

            if (currentHeadNode == head.get()) {
                if (currentHeadNode == currentTailNode) {
                    if (currentNextNode == null) {
                        return null;
                    }
                    this.tail.compareAndSet(currentTailNode, currentNextNode);
                } else if (head.compareAndSet(currentHeadNode, currentNextNode)) {
                    return currentNextNode.getData();
                }
            }
        }
    }
}
