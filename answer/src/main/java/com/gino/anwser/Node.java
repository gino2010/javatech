package com.gino.anwser;

/**
 * node for doubly linked list
 *
 * @author gino
 * Created on 2018/4/20
 */
public class Node<E> {
    public E element;
    public Node<E> next;
    public Node<E> prev;

    Node(E element, Node<E> prev, Node<E> next) {
        this.element = element;
        this.next = next;
        this.prev = prev;
    }

}
