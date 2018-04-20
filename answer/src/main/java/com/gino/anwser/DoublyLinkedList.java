package com.gino.anwser;


import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Doubly linked list
 *
 * @author gino
 * Created on 2018/4/20
 */
public class DoublyLinkedList<E> implements Iterable<E> {

    /**
     * size of list
     */
    private int size;
    /**
     * head node, first one
     */
    private Node<E> head;
    /**
     * tail node, last one
     */
    private Node<E> tail;

    public DoublyLinkedList() {
        this.size = 0;
    }

    /**
     * get size of list, NOT use in this example
     *
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * get head node, first one
     *
     * @return node
     */
    public Node<E> getHead() {
        return head;
    }

    /**
     * get tail node, last one
     *
     * @return node
     */
    public Node<E> getTail() {
        return tail;
    }

    /**
     * add a node at first position, NOT use in this example
     *
     * @param element <E> Object
     */
    public void addFirst(E element) {
        Node<E> node = new Node<>(element, null, this.head);
        if (this.head == null) {
            tail = node;
        } else {
            this.head.prev = node;
        }
        this.head = node;
        size++;
    }

    /**
     * add a node at last position
     *
     * @param element <E> Object
     */
    public void addLast(E element) {
        Node<E> node = new Node<>(element, this.tail, null);
        if (this.tail == null) {
            this.head = node;
        } else {
            this.tail.next = node;
        }
        this.tail = node;
        size++;
    }

    /**
     * reverse list
     */
    public void reverse() {
        Node<E> temp;
        Node<E> current = head;
        tail = head;
        while (current != null) {
            head = current;
            temp = current.next;
            current.next = current.prev;
            current.prev = temp;
            current = temp;
        }
    }

    /**
     * get a node from list by index
     *
     * @param index position in list
     * @return node
     */
    public Node<E> getNode(int index) {
        if (index < 0 || index > size - 1) {
            throw new RuntimeException("index: " + index + " is out of range: [0," + (size - 1) + "]");
        }

        Node<E> temp;

        if (index < (size >> 1)) {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
        }

        return temp;
    }

    /**
     * remove a node, NOT use in this example
     *
     * @param node
     */
    public void remove(Node<E> node) {
        Node<E> next = node.next;
        Node<E> prev = node.prev;

        if (prev == null) {
            head = next;
            head.prev = null;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
            tail.next = null;
        } else {
            next.prev = prev;
        }

        node.prev = node.next = null;
        size--;
    }

    /**
     * remove a node by index, NOT use in this example
     *
     * @param index
     */
    public void remove(int index) {
        Node<E> node = getNode(index);
        remove(node);
    }

    /**
     * iterator is convenient, but NOT use in this example
     **/
    @Override
    public Iterator<E> iterator() {
        return new DoublyLinkedListIterator();
    }

    @Override
    public void forEach(Consumer<? super E> action) {

    }

    @Override
    public Spliterator<E> spliterator() {
        return null;
    }

    private class DoublyLinkedListIterator implements Iterator<E> {
        private Node<E> current;
        private int index;

        DoublyLinkedListIterator() {
            this.current = head;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            Node<E> temp = current;
            current = current.next;
            index++;
            return temp.element;
        }

        @Override
        public void remove() {

        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {

        }
    }
}
