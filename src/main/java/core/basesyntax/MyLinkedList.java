package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);

        if (this.tail == null) {
            this.head = newNode;
        } else {
            this.tail.next = newNode;
            newNode.prev = this.tail;
        }

        this.tail = newNode;
        this.size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        if (this.head == null || index == this.size) {
            this.add(value);
        } else {
            Node<T> currentNode = this.head;

            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }

            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);

            if (currentNode.prev != null) {
                currentNode.prev.next = newNode;
            } else {
                this.head = newNode;
            }

            currentNode.prev = newNode;
            this.size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T e: list) {
            this.add(e);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        Node<T> currentNode = this.head;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode.getValue();
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        Node<T> currentNode = this.head;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        T prevValue = currentNode.getValue();
        currentNode.setValue(value);

        return prevValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        Node<T> currentNode = this.head;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next == null ? null : currentNode.next;
        } else {
            this.head = currentNode.next == null ? null : currentNode.next;
        }

        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev == null ? null : currentNode.prev;
        } else {
            this.tail = currentNode.prev == null ? null : currentNode.prev;
        }

        this.size--;
        return currentNode.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = this.head;

        while (currentNode != null) {
            if (object == null
                    ? object == currentNode.getValue()
                    : object.equals(currentNode.getValue())) {
                if (currentNode.prev != null) {
                    currentNode.prev.next = currentNode.next == null ? null : currentNode.next;
                } else {
                    this.head = currentNode.next == null ? null : currentNode.next;
                }

                if (currentNode.next != null) {
                    currentNode.next.prev = currentNode.prev == null ? null : currentNode.prev;
                } else {
                    this.tail = currentNode.prev == null ? null : currentNode.prev;
                }

                this.size--;

                return true;
            }

            currentNode = currentNode.next;
        }

        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public T getValue() {
            return this.value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
