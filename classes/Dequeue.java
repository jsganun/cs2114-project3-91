package classes;

public class Dequeue<T> {
    private Node<T> head;
    private Node<T> tail;

    public Dequeue() {
    }

    public void add(T obj) {
        throw new UnsupportedOperationException("TODO");
    }

    public T remove() {
        throw new UnsupportedOperationException("TODO");
    }

    public T peek() {
        throw new UnsupportedOperationException("TODO");
    }

    public int size() {
        throw new UnsupportedOperationException("TODO");
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("TODO");
    }

    private static class Node<E> {
        private E data;
        private Node<E> next;

        public Node(E dataPortion) {
            data = dataPortion;
        }

        public Node(E dataPortion, Node<E> nextNode) {
            data = dataPortion;
            next = nextNode;
        }
    }
}
