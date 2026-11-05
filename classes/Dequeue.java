package classes;

import java.util.NoSuchElementException;

/**
 * A first-in, first-out queue backed by a linked chain of nodes.
 *
 * @param <T> the type of elements stored in this queue
 * 
 * @author Jonathan Ganun (jsganun)
 * @version 11/6/2026
 */
public class Dequeue<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Creates an empty queue.
     */
    public Dequeue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Adds an element to the end of this queue.
     *
     * @param obj the element to add
     * @throws IllegalArgumentException if {@code obj} is {@code null}
     */
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

    /**
     * Removes and returns the element at the front of this queue.
     *
     * @return the element at the front of this queue
     * @throws NoSuchElementException if this queue is empty
     */
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

    /**
     * Returns the element at the front of this queue without removing it.
     *
     * @return the element at the front of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        else {
            return head.data;
        }
    }

    /**
     * Returns the number of elements in this queue.
     *
     * @return the number of elements in this queue
     */
    public int size() {
        return size;
    }

    /**
     * Indicates whether this queue contains no elements.
     *
     * @return {@code true} if this queue is empty; otherwise, {@code false}
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Finds the index of the first element equal to the specified object.
     *
     * @param obj the object to find
     * @return the zero-based index of the first matching element, or {@code -1}
     *         if no matching element is found
     */
    public int find(T obj) {
        if (isEmpty()) {
            return -1;
        }
        else {
            Node<T> curr = head;
            int index = 0;
            while (curr != null) {
                if (obj.equals(curr.data)) {
                    return index;
                }
                else {
                    curr = curr.next;
                }
            }
            return -1;
        }
    }

    /**
     * A node that stores an element and a reference to the next node.
     *
     * @param <E> the type of element stored in the node
     */
    private static class Node<E> {
        private E data;
        private Node<E> next;

        /**
         * Creates a node containing the specified element with no next node.
         *
         * @param dataPortion the element to store
         */
        public Node(E dataPortion) {
            data = dataPortion;
        }

        /**
         * Creates a node containing the specified element and next-node
         * reference.
         *
         * @param dataPortion the element to store
         * @param nextNode the node that follows this one
         */
        public Node(E dataPortion, Node<E> nextNode) {
            data = dataPortion;
            next = nextNode;
        }
    }
}
