package classes;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A first-in, first-out queue backed by a linked chain of nodes.
 *
 * @param <T> the type of elements stored in this queue
 * 
 * @author Jonathan Ganun (jsganun)
 * @version 11/6/2026
 */
public class Dequeue<T> implements Iterable<T> {
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

        size++;
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
            if (isEmpty()) {
                tail = null;
            }
            
            return removing.data;
        }

    }

    /**
     * Removes every element in this queue.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
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
     * Returns an iterator over the elements in this queue, from front to back.
     *
     * @return an iterator over this queue
     */
    @Override
    public Iterator<T> iterator() {
        return new DequeueIterator<T>(this);
    }

    /**
     * Finds the index of the first element equal to the specified object.
     *
     * @param obj the object to find
     * @return the zero-based index of the first matching element, or {@code -1}
     *         if no matching element is found
     * @throws IllegalArgumentException if {@code obj} is {@code null}
     */
    public int find(T obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }

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
                    index++;
                }
            }
            return -1;
        }
    }

    @Override 
    public String toString() {
        String result = "{";
        if (!isEmpty()) {
            Node<T> curr = head;
            while (curr != null) {
                result = result + curr.data.toString();
                if (curr.next != null) {
                    result = result + ", ";
                }
                curr = curr.next;
            }
        }
        result = result + "}";
        return result;
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
        @SuppressWarnings("unused")
        public Node(E dataPortion, Node<E> nextNode) {
            data = dataPortion;
            next = nextNode;
        }
    }

    /**
     * Iterates over the elements in a linked queue.
     *
     * @param <E> the type of element returned by the iterator
     */
    public static class DequeueIterator<E>
        implements Iterator<E> {

        private Node<E> nextNode;
        private Node<E> previousNode;
        private Node<E> lastReturned;
        private Dequeue<E> dequeue;
        private boolean canRemove;

        /**
         * Creates an iterator over the specified queue.
         *
         * @param queue the queue to iterate over
         */
        public DequeueIterator(Dequeue<E> queue) {
            dequeue = queue;
            nextNode = queue.head;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (lastReturned != null) {
                previousNode = lastReturned;
            }

            lastReturned = nextNode;
            nextNode = nextNode.next;
            canRemove = true;
            return lastReturned.data;
        }

        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException();
            }
            if (dequeue == null) {
                throw new UnsupportedOperationException();
            }

            if (previousNode == null) {
                dequeue.head = nextNode;
            }
            else {
                previousNode.next = nextNode;
            }

            if (nextNode == null) {
                dequeue.tail = previousNode;
            }
            dequeue.size--;
            if (dequeue.isEmpty()) {
                dequeue.tail = null;
            }

            lastReturned = null;
            canRemove = false;
        }
    }
}
