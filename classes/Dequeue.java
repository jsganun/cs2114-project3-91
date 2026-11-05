package classes;

import java.util.NoSuchElementException;

public class Dequeue<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public Dequeue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void add(T obj) {
        Node<T> newNode = new Node<T>(obj);
        size++;

        if (obj == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        }
        else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else {
            Node<T> removing = head;
            head = head.next;
            removing.next = null;
            
            size--;
            return removing.data;
        }

    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else {
            return head.data;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
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
